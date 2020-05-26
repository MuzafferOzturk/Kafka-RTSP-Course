package tr.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
/**
 * Queryable server kurulması durumunda store kullanılabilir daha sonra eklenicek
 *
 * @see <a href="https://kafka.apache.org/21/javadoc/org/apache/kafka/streams/kstream/Materialized.html">Materialized</a>
 * @see <a href="https://www.confluent.io/blog/unifying-stream-processing-and-interactive-queries-in-apache-kafka/">Interactive Queries</a>
 */
public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static Properties setUp(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }

    public static void main(String[] args) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KTable<Integer, String> kTable = streamsBuilder.table(AppConfigs.TOPIC_NAME);
        kTable.toStream().print(Printed.<Integer, String>toSysOut().withLabel("KTable"));


        kTable.filter((key, value) -> value.equalsIgnoreCase("1"), Materialized.as(AppConfigs.STORE_NAME));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
