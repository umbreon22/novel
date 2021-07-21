package novel.api.types.adapt.adapters;

import java.math.BigDecimal;

public class BigDecimalAdapter extends ObjectToStringAdapter<BigDecimal> {
    public BigDecimalAdapter() {
        super(BigDecimal::new);
    }
}
