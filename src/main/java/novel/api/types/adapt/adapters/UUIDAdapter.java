package novel.api.types.adapt.adapters;

import java.util.UUID;

public class UUIDAdapter extends ObjectToStringAdapter<UUID> {
    public UUIDAdapter(){
        super(UUID::fromString);
    }
}
