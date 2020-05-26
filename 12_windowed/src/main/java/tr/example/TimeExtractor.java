package tr.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import tr.example.data.Form;

public class TimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        if(consumerRecord.value() instanceof Form)
            return ((Form) consumerRecord.value()).getCreatedTime().getTime();
        return l;
    }
}
