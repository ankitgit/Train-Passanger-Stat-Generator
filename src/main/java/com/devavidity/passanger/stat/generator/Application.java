package com.devavidity.passanger.stat.generator;

import com.devavidity.passanger.stat.generator.models.Compartment;
import com.devavidity.passanger.stat.generator.utils.ToolRunner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

/**
 * Created by ankit on 17.07.17.
 */
public class Application extends ToolRunner {

    private final String EVENT_GENERATION_INTERVAL_MS = "event.generation.interval.ms";
    private final String VEHICLE_ID = "vehicle.id";
    private final String NUMBER_OF_COMPARTMENT = "number.of.compartment";
    private final String COMPARTMENT_CAPACITY = "compartment.capacity";
    private final String KAFKA_TOPIC = "kafka.topic";

    public static void main(String[] args) {
        Application application = new Application();
        application.execute(args);
    }

    @Override
    public void run() {
        Properties properties = getProperties();
        long eventGenerationInterval = Long.parseLong(properties.getProperty(EVENT_GENERATION_INTERVAL_MS));
        int vehicleId = Integer.parseInt(properties.getProperty(VEHICLE_ID));
        int numberOfCompartment = Integer.parseInt(properties.getProperty(NUMBER_OF_COMPARTMENT));
        int compartmentCapacity = Integer.parseInt(properties.getProperty(COMPARTMENT_CAPACITY));
        String kafkaTopic = properties.getProperty(KAFKA_TOPIC);

        Producer producer = new KafkaProducer<String, String>(properties);

        while (true) {
            for (int i = 1; i <= numberOfCompartment; i++) {
                Random random = new Random();
                int in = random.nextInt(compartmentCapacity);
                int out = random.nextInt(compartmentCapacity - in);
                Compartment compartment = new Compartment(vehicleId, i, in, out, compartmentCapacity);
                String key = vehicleId + "_" + i;
                String value = compartment.toJson();
                ProducerRecord record = new ProducerRecord(kafkaTopic, key, value);
                producer.send(record);
            }
            try {
                Thread.sleep(eventGenerationInterval);
            } catch (InterruptedException e) {
                System.err.println("Woopsss !!!! Bye Bye. " + e.getMessage());
                return;
            }
        }
    }
}
