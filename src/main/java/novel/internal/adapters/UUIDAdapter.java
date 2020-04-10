package novel.internal.adapters;

import java.util.UUID;

public class UUIDAdapter extends ObjectToStringDataAdapter<UUID> {
    UUIDAdapter(){
        super(UUID::fromString);
    }
}
