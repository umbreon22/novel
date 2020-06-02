package novel.internal.reflective.handles;

/**
 * This class 'handles' getting and setting member variables reflectively
 */
interface ReflectiveHandle {
    void set(Object instance, Object memberValue) throws Throwable;
    Object get(Object instance) throws Throwable;
}