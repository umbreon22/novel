package novel.internal.testutil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Compares the values between to {@link Number} instances.
 */
public class NumberValueComparator implements Comparator<Number> {

	protected static final int CANNOT_COMPARE = -69_69_69_69;

	@Override
	public int compare(Number a, Number b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		int compare = compareWhole(a, b);
		if(compare != CANNOT_COMPARE) {
			return compare;
		}
		compare = compareDecimal(a, b);
		if(compare != CANNOT_COMPARE) {
			return compare;
		}
		return compareBigDecimal(a, b);
	}

	protected int compareWhole(Number a, Number b) {
		if(isWholeBoxed(a) && isWholeBoxed(b)) {
			return Long.compare(a.longValue(), b.longValue());
		} else return CANNOT_COMPARE;
	}

	protected int compareDecimal(Number a, Number b) {
		if(isPrimitiveBoxed(a) && isPrimitiveBoxed(b)) {//can compare decimal box to whole box
			return Double.compare(a.doubleValue(), b.doubleValue());
		} else return CANNOT_COMPARE;
	}

	protected int compareBigDecimal(Number a, Number b) {
		BigDecimal bigA = toBigDecimal(a);
		BigDecimal bigB = toBigDecimal(b);
		return bigA.compareTo(bigB);
	}

	protected static boolean isPrimitiveBoxed(Number num) {
		return isDecimalBoxed(num) || isWholeBoxed(num);
	}

	protected static boolean isWholeBoxed(Number number) {
		return isInteger(number)
				|| isLong(number)
				|| isShort(number)
				|| isByte(number);
	}

	protected static boolean isByte(Number number) {
		return number instanceof Byte;
	}

	protected static boolean isShort(Number number) {
		return number instanceof Short;
	}

	protected static boolean isLong(Number number) {
		return number instanceof Long || number instanceof AtomicLong;
	}

	protected static boolean isInteger(Number number) {
		return number instanceof Integer || number instanceof AtomicInteger;
	}

	protected static boolean isFloat(Number number) {
		return number instanceof Float;
	}

	protected static boolean isDouble(Number number) {
		return number instanceof Double;
	}

	protected static boolean isDecimalBoxed(Number number) {
		return isDouble(number)
			|| isFloat(number);
	}

	protected static BigDecimal toBigDecimal(Number number) {
		if(number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else if(number instanceof BigInteger) {
			return new BigDecimal((BigInteger) number);
		} else if(isWholeBoxed(number)) {
			return new BigDecimal(number.longValue());
		} else if(isDecimalBoxed(number)) {
			return BigDecimal.valueOf(number.doubleValue());
		}
		try {
			return new BigDecimal(number.toString());
		} catch(final NumberFormatException e) {
			throw new RuntimeException(number.getClass() + " is not supported!");
		}
	}

}
