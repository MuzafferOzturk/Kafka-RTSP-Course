package tr.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.User;
import tr.example.serde.JsonDeserializer;
import tr.example.serde.JsonSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);
    public static void main(String[] args) {
        logger.info("Start Application");
        new Application().runConsumer();
        new Application().runProducer();
    }

    public void runConsumer(){
        logger.info("Start Consumer");
        Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, AppConfigs.GROUP_ID);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, User.class);

        KafkaConsumer<Integer, User> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(AppConfigs.TOPIC_NAME));
        logger.info("Consumer subscribe");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true){
                ConsumerRecords<Integer, User> consumerRecord = consumer.poll(Duration.ofMillis(100));
                consumerRecord.forEach(record -> {
                    logger.info(record.key() + " -- " + record.value());
                });
            }
        });

    }

    public void runProducer(){
        logger.info("Start producer");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);
        producerStart(kafkaProducer);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::close));
    }

    public void producerStart(KafkaProducer producer){
        int index;
        logger.info("Producer send messages");
        for(index = 0; index < AppConfigs.EVENT_COUNT; index++)
            producer.send(new ProducerRecord(AppConfigs.TOPIC_NAME, index, new User("M", "M", "+9" + index)));
    }


}
