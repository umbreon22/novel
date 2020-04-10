package novel.internal.adapters;

import java.math.BigInteger;

public class BigIntegerAdapter extends ObjectToStringDataAdapter<BigInteger> {
    BigIntegerAdapter() {
        super(BigInteger::new);
    }
}
