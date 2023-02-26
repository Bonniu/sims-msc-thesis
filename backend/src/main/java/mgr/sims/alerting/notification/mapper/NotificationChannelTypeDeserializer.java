package mgr.sims.alerting.notification.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import mgr.sims.alerting.notification.model.NotificationChannelType;

import java.io.IOException;
import java.util.Arrays;

public class NotificationChannelTypeDeserializer extends StdDeserializer<NotificationChannelType> {

    protected NotificationChannelTypeDeserializer() {
        super(NotificationChannelType.class);
    }

    @Override
    public NotificationChannelType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String name = p.readValueAs(String.class);
        return Arrays.stream(NotificationChannelType.values())
                .filter(c -> c.getType().equals(name))
                .findFirst().orElseThrow();
    }
}