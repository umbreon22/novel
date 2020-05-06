package novel.internal.adapters;

import java.math.BigInteger;

public class BigIntegerAdapter extends ObjectToStringAdapter<BigInteger> {
    BigIntegerAdapter() {
        super(BigInteger::new);
    }
}
