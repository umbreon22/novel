package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.token.TypeToken;
import novel.api.types.write.pens.DataPen;
import novel.internal.reflective.constructor.ReflectiveConstructor;
import novel.internal.reflective.constructor.UnsafeInstanceConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

public class RecordAdapterFactory extends AdapterFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecordAdapterFactory.class);

	@Override
	public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
		if(!Record.class.isAssignableFrom(typeToken.getRawType()) && typeToken.getRawType() != Record.class) {
			return null;
		}
		//noinspection rawtypes
		return new RecordAdapter(novel, typeToken);
	}

	private static final class RecordAdapter<R extends Record> implements ObjectDataAdapter<R> {

		private final Novel novel;
		private final ComponentData[] componentData;
		private final ReflectiveConstructor<R> recordConstructor;

		private static record ComponentData(Method accessor, TypeToken token){}

		private RecordAdapter(Novel novel, TypeToken<R> typeToken) {
			this.novel = novel;
			var rawType = typeToken.getRawType();
			var components = rawType.getRecordComponents();
			this.componentData = Arrays.stream(components)
					.map(component-> new ComponentData(component.getAccessor(), TypeToken.get(component.getType())))
					.toArray(ComponentData[]::new);
			this.recordConstructor = createConstructor(rawType, components);
		}

		private ReflectiveConstructor<R> createConstructor(Class<? super R> rawType, RecordComponent[] components) {
			Class<?>[] recordClasses = Arrays.stream(components)
					.map(RecordComponent::getType)
					.toArray(Class<?>[]::new);
			//noinspection rawtypes
			return new UnsafeInstanceConstructor(rawType, rawType, recordClasses);
		}

		@Override
		public R read(DataPaper paper) throws DataPaperReadException {
			Object[] params = new Object[componentData.length];
			for(int i = 0; i < params.length; i++) {
				params[i] = novel.read(paper, componentData[i].token);
			}
			return recordConstructor.construct(params);
		}

		@Override
		public void write(DataPen pen, R object) {
			for(var component : componentData) {
				try {
					Object componentValue = component.accessor().invoke(object);
					novel.write(pen, componentValue, component.token);
				} catch (IllegalAccessException | InvocationTargetException e) {
					//todo, possibly add some exception handler?
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

}
