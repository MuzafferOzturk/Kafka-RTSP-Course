package tr.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
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
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());

        try(KafkaProducer<Integer, Integer> kafkaProducer = new KafkaProducer<>(properties)){
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 15, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 16, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 17, 2));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 15, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 18, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 16, 2));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, 15, 1));

            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 1, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 2, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 3, 2));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 4, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 5, 1));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 6, 2));
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, 7, 1));
        }
        logger.info("Finish tr.example.Producer");
    }
}
