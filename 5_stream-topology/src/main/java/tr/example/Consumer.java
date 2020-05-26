package tr.example;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.Contact;
import tr.example.serde.StreamsSerdes;

import java.util.Properties;

public class Consumer {
    public static final Logger logger = LogManager.getLogger(Consumer.class);
    public static void main(String[] args) {
        logger.info("Start stream");
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "consumer");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StreamsSerdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StreamsSerdes.Contact().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, Contact> kStream = streamsBuilder.stream(AppConfigs.OUTPUT_TOPIC_CONTACT
                , Consumed.with(StreamsSerdes.String(), StreamsSerdes.Contact()));
        kStream.foreach((key, value) -> logger.info(key + " " + value.toString()));
        Topology topology = streamsBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology, properties);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
