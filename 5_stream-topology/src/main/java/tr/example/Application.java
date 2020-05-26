package tr.example;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.example.data.Address;
import tr.example.data.Contact;
import tr.example.data.Form;
import tr.example.serde.StreamsSerdes;

import java.util.ArrayList;
import java.util.Collections;
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
        return properties;
    }

    public static Address getAddress(Form form){
        Address address = new Address();
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setType(HOME_ADDRESS);
        address.setUserId(form.getUser().getId());
        return address;
    }

    public static List<Contact> getContactList(Form form){
        List<Contact> contactList = new ArrayList<>();
        form.getPhoneList().forEach(phone -> contactList.add(new Contact(PHONE_TYPE, phone)));
        return contactList;
    }

    public static void main(String[] args) {
        logger.info("Start Topology");
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, Form> streamForm = streamsBuilder.stream(AppConfigs.TOPIC_NAME
                , Consumed.with(StreamsSerdes.String(), StreamsSerdes.Form()));

        streamForm.foreach((s, form) -> logger.info(s + " " + form.toString()));

        streamForm.filter((s, form) -> form.getCity().equals("ANKARA"))
                .to(AppConfigs.OUTPUT_TOPIC_ANKARA, Produced.with(StreamsSerdes.String(), StreamsSerdes.Form()));

        streamForm.filter((s, form) -> form.getAddressType().equalsIgnoreCase(HOME_ADDRESS))
                .mapValues((s, form) -> getAddress(form))
                .to(AppConfigs.OUTPUT_TOPIC_ADDRESS, Produced.with(StreamsSerdes.String(), StreamsSerdes.Address()));

        streamForm.flatMapValues((s, form) -> getContactList(form))
                .to(AppConfigs.OUTPUT_TOPIC_CONTACT, Produced.with(StreamsSerdes.String(), StreamsSerdes.Contact()));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), setUp());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
