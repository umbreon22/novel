package novel.internal.adapters;

import java.util.UUID;

public class UUIDAdapter extends ObjectToStringAdapter<UUID> {
    UUIDAdapter(){
        super(UUID::fromString);
    }
}
