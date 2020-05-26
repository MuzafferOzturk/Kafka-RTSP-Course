package tr.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);
    public static void main(String[] args) {
        //Consumer properties isolation.level=read.committed
        logger.info("Start Producer");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, AppConfigs.TRANSACTION_ID);

        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);
        kafkaProducer.initTransactions();

        new Application().commitTransaction(kafkaProducer);
        new Application().abortTransaction(kafkaProducer);
        kafkaProducer.close();
    }

    public void  commitTransaction(KafkaProducer kafkaProducer){
        logger.info("Commit Transaction begin");
        try {
            kafkaProducer.beginTransaction();
            int index;
            for(index = 0; index < AppConfigs.EVENT_COUNT; index++){
                kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, "Transaction 1 for topic 1 " + index));
                kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, "Transaction 1 for topic 2 " + index));
            }
            kafkaProducer.commitTransaction();
        }
        catch (Exception ex){
            logger.error(ex.toString());
            kafkaProducer.abortTransaction();
            kafkaProducer.close();
        }
        logger.info("Commit Transaction end");
    }

    public void abortTransaction(KafkaProducer kafkaProducer){
        logger.info("Abort Transaction begin");
        try {
            kafkaProducer.beginTransaction();
            int index;
            for(index = 0; index < AppConfigs.EVENT_COUNT; index++){
                kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME, index, "Transaction 2 for topic 1 " + index));
                kafkaProducer.send(new ProducerRecord<>(AppConfigs.TOPIC_NAME2, index, "Transaction 2 for topic 2 " + index));
            }
            logger.info("Abort Transaction end");
            throw new RuntimeException("Test Transaction");
        }
        catch (Exception ex){
            logger.error("Fail", ex);
            kafkaProducer.abortTransaction();
            kafkaProducer.close();
        }
    }
}
