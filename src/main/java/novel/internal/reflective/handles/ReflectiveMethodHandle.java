package novel.internal.reflective.handles;

import java.lang.invoke.MethodHandle;

public class ReflectiveMethodHandle implements ReflectiveHandle {

    private final MethodHandle getter;
    private final MethodHandle setter;

    public ReflectiveMethodHandle(MethodHandle getter, MethodHandle setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public void set(Object instance, Object memberValue) throws Throwable {
        setter.invoke(instance, memberValue);
    }

    @Override
    public Object get(Object instance) throws Throwable {
        return getter.invoke(instance);
    }
}
