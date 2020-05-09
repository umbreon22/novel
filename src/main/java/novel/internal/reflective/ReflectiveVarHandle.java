package novel.internal.reflective;

import java.lang.invoke.VarHandle;

public class ReflectiveVarHandle implements ReflectiveHandle {

    private final VarHandle varHandle;

    public ReflectiveVarHandle(VarHandle varHandle) {
        this.varHandle = varHandle;
    }

    @Override
    public void set(Object instance, Object memberValue) {
        varHandle.set(instance, memberValue);
    }

    @Override
    public Object get(Object instance) {
        return varHandle.get(instance);
    }
}
