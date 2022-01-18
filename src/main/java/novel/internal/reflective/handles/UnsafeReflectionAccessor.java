package novel.internal.reflective.handles;

import sun.misc.Unsafe;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An implementation of {@link ReflectionAccessor} based on {@link Unsafe}.
 */
final class UnsafeReflectionAccessor extends ReflectionAccessor {
	private static Class unsafeClass;
	private final Object theUnsafe = getUnsafeInstance();
	private final Field overrideField = getOverrideField();

	// Visible for testing only
	boolean makeAccessibleWithUnsafe(AccessibleObject ao) {
		if (theUnsafe != null && overrideField != null) {
			try {
				Method method = unsafeClass.getMethod("objectFieldOffset", Field.class);
				long overrideOffset = (Long) method.invoke(theUnsafe, overrideField);  // long overrideOffset = theUnsafe.objectFieldOffset(overrideField);
				Method putBooleanMethod = unsafeClass.getMethod("putBoolean",  Object.class, long.class, boolean.class);
				putBooleanMethod.invoke(theUnsafe, ao, overrideOffset, true); // theUnsafe.putBoolean(ao, overrideOffset, true);
				return true;
			} catch (Exception ignored) { // do nothing
			}
		}
		return false;
	}

	private static Object getUnsafeInstance() {
		try {
			unsafeClass = Class.forName("sun.misc.Unsafe");
			Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			return unsafeField.get(null);
		} catch (Exception e) {
			return null;
		}
	}

	private static Field getOverrideField() {
		try {
			return AccessibleObject.class.getDeclaredField("override");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void makeAccessible(Field field) {
		try {
			if(!makeAccessibleWithUnsafe(field)) {
				field.setAccessible(true);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
