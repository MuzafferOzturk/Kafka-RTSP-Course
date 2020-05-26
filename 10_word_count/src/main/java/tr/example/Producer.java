package tr.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Producer {
    public static final Logger logger = LogManager.getLogger(Producer.class);
    public static void main(String[] args) {
        logger.info("Start tr.example.Producer");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try(KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)){
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, "X", "New Message add"));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, "X", "Add Message"));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, "X", "Message X"));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, "X", "Message X"));
        }
        logger.info("Finish tr.example.Producer");
    }
}
