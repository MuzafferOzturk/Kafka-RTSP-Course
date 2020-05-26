package tr.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.KeyValuePair;

import java.util.Arrays;
import java.util.Properties;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static Properties setUp(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.STORE_LOCATION);
        return properties;
    }

    public static void main(String[] args) {
        logger.info("Start Application");
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, Integer> kStream = streamsBuilder.stream(AppConfigs.TOPIC_NAME);
        kStream.print(Printed.<Integer, Integer>toSysOut().withLabel("TOPIC-IN"));

        KGroupedStream<Integer, Integer> groupWord = kStream.groupByKey();

        KTable<Integer, Integer> eventTableReduce = groupWord.reduce(Integer::sum);
        eventTableReduce.toStream().print(Printed.<Integer, Integer>toSysOut().withLabel("EVENT-COUNT-REDUCE"));

        KTable<Integer, Integer> eventTableAgg = groupWord.aggregate(() -> 0, (key, value, aggValue) -> aggValue + value);
        eventTableAgg.toStream().print(Printed.<Integer, Integer>toSysOut().withLabel("EVENT-COUNT-AGGREGATE"));

        //KTable COUNT
        KTable<Integer, Integer> kTable = streamsBuilder.table(AppConfigs.TOPIC_NAME2);
        kTable.toStream().print(Printed.<Integer, Integer>toSysOut().withLabel("KTable"));
        kTable
                .groupBy((key, value) -> KeyValue.pair(value, 1))
                .count()
                .toStream()
                .print(Printed.<Integer, Long>toSysOut().withLabel("KTable-Count"));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
