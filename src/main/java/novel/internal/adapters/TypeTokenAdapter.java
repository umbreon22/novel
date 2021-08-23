package novel.internal.adapters;

import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.read.validators.ByteValidator;
import novel.api.types.token.TypeToken;
import novel.api.types.write.pens.DataPen;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeTokenAdapter implements ObjectDataAdapter<TypeToken> {

	private final ClassAdapter classAdapter = new ClassAdapter();
	private final ObjectDataAdapter<Type[]> typeArrayAdapter = new ObjectDataAdapter<>() {
	private final ByteValidator sizeValidator = size -> size >= 0;

		@Override
		public Type[] read(DataPaper paper) throws DataPaperReadException {
			byte length = sizeValidator.validate(paper.bytes());
			Type[] types = new Type[length];
			for(int i = 0; i < types.length; i++) {
				types[i] = classAdapter.read(paper);
			}
			return types;
		}

		@Override
		public void write(DataPen pen, Type[] vars) {
			pen.bytes((byte) vars.length);
			for(Type type : vars) {
				String name = type.getTypeName();
				pen.strings(name);
			}
		}
	};

	@Override
	public TypeToken read(DataPaper paper) throws DataPaperReadException {
		var clazz = classAdapter.read(paper);
		Type[] params = typeArrayAdapter.read(paper);
		return params.length > 0
				? TypeToken.getParameterized(clazz, params)
				: TypeToken.get(clazz);
	}

	@Override
	public void write(DataPen pen, TypeToken object) {
		var type = object.getRawType();
		classAdapter.write(pen, type);
		if(object.getType() instanceof ParameterizedType pt) {
			typeArrayAdapter.write(pen, pt.getActualTypeArguments());
		} else {
			pen.bytes((byte)0);
		}
	}
}
