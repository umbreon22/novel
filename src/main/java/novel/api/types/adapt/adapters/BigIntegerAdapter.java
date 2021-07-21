package novel.api.types.adapt.adapters;

import java.math.BigInteger;

public class BigIntegerAdapter extends ObjectToStringAdapter<BigInteger> {
    public BigIntegerAdapter() {
        super(BigInteger::new);
    }
}
