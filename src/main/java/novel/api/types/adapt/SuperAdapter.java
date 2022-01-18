package novel.api.types.adapt;

import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.write.pens.DataPen;

public final class SuperAdapter<T> implements ObjectDataAdapter<T> {

	private final ObjectDataAdapter<T> adapter;
	public SuperAdapter(ObjectDataAdapter<T> adapter) {
		this.adapter = adapter;
	}

	@Override
	public T read(DataPaper paper) throws DataPaperReadException {
		return adapter.read(paper);
	}

	@Override
	public void write(DataPen pen, T object) {
		adapter.write(pen, object);
	}
}
