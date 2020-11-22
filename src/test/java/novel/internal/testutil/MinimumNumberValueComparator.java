package novel.internal.testutil;

import java.util.function.Function;

/**
 * Compares the values between to {@link Number} instances.
 * This time, we're comparing the values at the smallest sized type.
 * i.e. {@link Long} compared to {@link Short} will be compared as two shorts.
 *
 * May not work with types of size greater than {@link Long},
 * I don't need to implement that at this time.
 */
public class MinimumNumberValueComparator extends NumberValueComparator {

	@Override
	protected int compareWhole(Number a, Number b) {
		return convertAndCompare(a, b, findSmallestWholeConverter(a,b));
	}

	@Override
	protected int compareDecimal(Number a, Number b) {
		return convertAndCompare(a, b, findSmallestDecimalConverter(a, b));
	}

	private Function<Number, Comparable<?>> findSmallestWholeConverter(Number a, Number b) {
		if(isByte(a) || isByte(b)) {
			return Number::byteValue;
		} else if(isShort(a) || isShort(b)) {
			return Number::shortValue;
		} else if(isInteger(a) || isInteger(b)) {
			return Number::intValue;
		} else if(isLong(a) || isLong(b)) {
			return Number::longValue;
		}
		return null;
	}

	private Function<Number, Comparable<?>> findSmallestDecimalConverter(Number a, Number b) {
		if(isFloat(a) || isFloat(b)) {
			return Number::floatValue;
		} else if(isDouble(a) || isDouble(b)) {
			return Number::doubleValue;
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private int convertAndCompare(Number a, Number b, Function<Number, Comparable<?>> converter) {
		if(converter == null) {
			return CANNOT_COMPARE;
		} else {
			Comparable comparableA = converter.apply(a);
			Comparable comparableB = converter.apply(b);
			return comparableA.compareTo(comparableB);
		}
	}

}
