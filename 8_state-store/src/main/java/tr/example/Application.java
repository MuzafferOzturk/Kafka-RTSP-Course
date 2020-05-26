package tr.example;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.Address;
import tr.example.data.Contact;
import tr.example.data.Form;
import tr.example.serde.StreamsSerdes;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static Properties setUp(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        return properties;
    }

    public static void main(String[] args) {
        logger.info("Start Topology");
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        StoreBuilder<KeyValueStore<String, Double>> storeBuilder = Stores.keyValueStoreBuilder(
                Stores.inMemoryKeyValueStore(AppConfigs.STORE_NAME),
                StreamsSerdes.String()
                , StreamsSerdes.Double());
        streamsBuilder.addStateStore(storeBuilder);

        KStream<String, Form> streamForm = streamsBuilder.stream(AppConfigs.TOPIC_NAME
                , Consumed.with(StreamsSerdes.String(), StreamsSerdes.Form()));
        streamForm.process((ProcessorCity::new), AppConfigs.STORE_NAME);

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
