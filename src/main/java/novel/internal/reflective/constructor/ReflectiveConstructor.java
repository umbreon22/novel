package novel.internal.reflective.constructor;

public interface ReflectiveConstructor<T> {
	T construct(Object... params);
}
