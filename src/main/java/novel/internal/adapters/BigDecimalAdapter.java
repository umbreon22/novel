package novel.internal.adapters;

import java.math.BigDecimal;

public class BigDecimalAdapter extends ObjectToStringAdapter<BigDecimal> {
    BigDecimalAdapter() {
        super(BigDecimal::new);
    }
}
