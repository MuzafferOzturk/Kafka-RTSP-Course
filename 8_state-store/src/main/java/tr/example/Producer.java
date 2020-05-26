package tr.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.Form;
import tr.example.data.User;
import tr.example.serde.JsonSerializer;

import java.util.Collections;
import java.util.Properties;

public class Producer {
    public static final Logger logger = LogManager.getLogger(Producer.class);
    public static void main(String[] args) {
        logger.info("Start Producer");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "state-producer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        try(KafkaProducer<String, Form> kafkaProducer = new KafkaProducer<>(properties)){
            User user = new User("M1", "N1");
            kafkaProducer.send(
                    new ProducerRecord<>(AppConfigs.TOPIC_NAME, "1",
                            new Form(user, "ANKARA", "S", "HOME", Collections.singletonList("+900"))));
            logger.info("Send Form data");
        }
        logger.info("Finish Producer");
    }
}
