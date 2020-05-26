package tr.example.serde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import tr.example.data.Form;
import tr.example.data.User;

import java.util.HashMap;
import java.util.Map;

public class StreamsSerdes extends Serdes {

    public static final class UserSerdes extends Serdes.WrapperSerde<User> {
        public UserSerdes() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }
    public static Serde<User> User() {
        UserSerdes serde = new UserSerdes();
        Map<String, Object> serdesConfigs = new HashMap<>();
        serdesConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, User.class);
        serde.configure(serdesConfigs, false);
        return serde;
    }

    public static final class FormSerde extends Serdes.WrapperSerde<Form> {
        public FormSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }
    public static Serde<Form> Form() {
        FormSerde serde = new FormSerde();
        Map<String, Object> serdesConfigs = new HashMap<>();
        serdesConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, Form.class);
        serde.configure(serdesConfigs, false);
        return serde;
    }
}
