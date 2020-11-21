package novel.internal.testutil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

/**
 * Compares the values between to {@link Number} instances.
 */
public class NumberValueComparator implements Comparator<Number> {

	private static final int CANNOT_COMPARE = -69_69_69_69;

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

	private static int compareWhole(Number a, Number b) {
		if(isWholeBoxed(a) && isWholeBoxed(b)) {
			return Long.compare(a.longValue(), b.longValue());
		} else return CANNOT_COMPARE;
	}

	private static int compareDecimal(Number a, Number b) {
		if(isPrimitiveBoxed(a) && isPrimitiveBoxed(b)) {//can compare decimal box to whole box
			return Double.compare(a.doubleValue(), b.doubleValue());
		} else return CANNOT_COMPARE;
	}

	private static int compareBigDecimal(Number a, Number b) {
		return toBigDecimal(a).compareTo(toBigDecimal(b));
	}

	private static boolean isPrimitiveBoxed(Number num) {
		return isDecimalBoxed(num) || isWholeBoxed(num);
	}

	private static boolean isWholeBoxed(Number number) {
		return number instanceof Integer
				|| number instanceof Long
				|| number instanceof Short
				|| number instanceof Byte;
	}

	private static boolean isDecimalBoxed(Number number) {
		return number instanceof Double
			|| number instanceof Float;
	}

	private static BigDecimal toBigDecimal(Number number) {
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
