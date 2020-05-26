package tr.example;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import tr.example.data.Form;

public class ProcessorCity implements Processor<String, Form> {
    private KeyValueStore<String, Double> store;
    @Override
    public void init(ProcessorContext processorContext) {
        store = (KeyValueStore<String, Double>) processorContext.getStateStore(AppConfigs.STORE_NAME);
        printAllData();
    }

    private void printAllData(){
        KeyValueIterator<String, Double> iterator = store.all();
        while (iterator.hasNext()){
            KeyValue keyValue = iterator.next();
            System.out.println(keyValue.key + "  " + keyValue.value);
        }
    }

    @Override
    public void process(String s, Form form) {
        Double value = store.get(form.getCity());
        if(value == null)
            store.put(form.getCity(), 1.0);
        else
            store.put(form.getCity(), value + 1);
        printAllData();
    }

    @Override
    public void close() {

    }
}
