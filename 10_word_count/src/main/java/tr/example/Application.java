package tr.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static Properties setUp(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }

    public static void main(String[] args) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kTable = streamsBuilder.stream(AppConfigs.TOPIC_NAME);
        kTable.print(Printed.<String, String>toSysOut().withLabel("TOPIC-IN"));

        KStream<String, String> wordList = kTable
                .mapValues(s -> s.toLowerCase()).
                flatMapValues((s, s2) -> Arrays.asList(s2.split(" ")));
        wordList.print(Printed.<String, String>toSysOut().withLabel("WORD-LIST"));

        KGroupedStream<String, String> groupWord = wordList.groupBy((s, s2) -> s2);

        KTable<String, Long> countTable = groupWord.count();
        countTable.toStream().print(Printed.<String, Long>toSysOut().withLabel("COUNT-TABLE"));


        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
