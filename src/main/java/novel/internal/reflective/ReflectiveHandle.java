package novel.internal.reflective;

/**
 * This class 'handles' getting and setting member variables reflectively
 */
interface ReflectiveHandle {
    void set(Object instance, Object memberValue) throws IllegalAccessException;
    Object get(Object instance) throws IllegalAccessException;
}