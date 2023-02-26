package mgr.sims.alerting.notification.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import mgr.sims.alerting.notification.model.NotificationChannelType;

import java.io.IOException;

public class NotificationChannelTypeSerializer extends StdSerializer<NotificationChannelType> {

    public NotificationChannelTypeSerializer() {
        this(null);
    }

    public NotificationChannelTypeSerializer(Class<NotificationChannelType> t) {
        super(t);
    }

    @Override
    public void serialize(NotificationChannelType notificationChannelType, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeRawValue("\"" + notificationChannelType.getType() + "\"");
    }
}