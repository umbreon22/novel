package novel.internal.testutil;

public class FieldsWithModifiers {

    private FieldsWithModifiers(){throw new UnsupportedOperationException();}

    private Object privateObject = null;
    private transient Object privateTransientObject = null;
    private final Object privateFinalObject = null;
    private static Object privateStaticObject = null;
    private static final Object privateStaticFinalObject = null;
    private static final transient Object privateStaticFinalTransientObject = null;

    Object packagePrivateObject = null;
    transient Object packagePrivateTransientObject = null;
    final Object packagePrivateFinalObject = null;
    final transient Object packagePrivateFinalTransientObject = null;

    static Object packagePrivateStaticObject = null;
    static transient Object packagePrivateStaticTransientObject = null;
    static final Object packagePrivateStaticFinalObject = null;
    static final transient Object packagePrivateStaticFinalTransientObject = null;

    protected Object protectedObject = null;
    protected transient Object protectedTransientObject = null;
    protected final Object protectedFinalObject = null;
    protected final transient Object protectedFinalTransientObject = null;
    protected static Object protectedStaticObject = null;
    protected static final Object protectedStaticFinalObject = null;
    protected static final transient Object protectedStaticFinalTransientObject = null;

    public Object publicObject = null;
    public transient Object publicTransientObject = null;
    public final Object publicFinalObject = null;
    public final transient Object publicFinalTransientObject = null;
    public static Object publicStaticObject = null;
    public static final Object publicStaticFinalObject = null;
    public static final transient Object publicStaticFinalTransientObject = null;

}
