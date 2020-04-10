package novel.internal.adapters;

import java.math.BigDecimal;

public class BigDecimalAdapter extends ObjectToStringDataAdapter<BigDecimal> {
    BigDecimalAdapter() {
        super(BigDecimal::new);
    }
}
