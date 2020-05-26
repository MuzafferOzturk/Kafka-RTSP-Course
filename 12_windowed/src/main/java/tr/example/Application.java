package tr.example;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.Form;
import tr.example.serde.StreamsSerdes;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);
    public static final String HOME_ADDRESS = "HOME";
    public static final String PHONE_TYPE = "PHONE";

    public static Properties setUp(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StreamsSerdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StreamsSerdes.Form().getClass());
        return properties;
    }

    public static void main(String[] args) {
        logger.info("Start Application");
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, Form> kStream = streamsBuilder.stream(AppConfigs.TOPIC_NAME
                , Consumed.with(StreamsSerdes.String(), StreamsSerdes.Form())
                        .withTimestampExtractor(new TimeExtractor()));
        kStream.print(Printed.<String, Form>toSysOut().withLabel("WINDOW"));
        KTable<Windowed<String>, Long> kTable = kStream.groupByKey(Grouped.with(StreamsSerdes.String(), StreamsSerdes.Form()))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)).grace(Duration.ofMinutes(2)))
                .count();
                //window kapanana kadar bekle...
//                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()));

        kTable.toStream().foreach((formWindowed, aLong) -> logger
                .info("\nUser ID: " + formWindowed.key() + " Window ID: " + formWindowed.window().hashCode()
                        + "\nStart: " + new Date(formWindowed.window().start()).toString()
                        + " \nEnd:" + new Date(formWindowed.window().end()).toString()
                        + " \nCount: " + aLong));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
