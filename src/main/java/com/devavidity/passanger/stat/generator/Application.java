package com.devavidity.passanger.stat.generator;

import com.devavidity.passanger.stat.generator.models.Compartment;
import com.devavidity.passanger.stat.generator.utils.ToolRunner;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by ankit on 17.07.17.
 */
public class Application extends ToolRunner {

    public static final String EVENT_GENERATION_INTERVAL_MS = "event.generation.interval.ms";
    public static final String VEHICLE_ID = "vehicle.id";
    public static final String NUMBER_OF_COMPARTMENT = "number.of.compartment";
    public static final String COMPARTMENT_CAPACITY = "compartment.capacity";
    public static final String KAFKA_TOPIC_OUTPUT = "kafka.out.topic";

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
        String kafkaTopic = properties.getProperty(KAFKA_TOPIC_OUTPUT);

        Producer<String, String> producer = new KafkaProducer<>(properties);
        Random random = new Random();

        while (true) {
            for (int i = 1; i <= numberOfCompartment; i++) {
                int in = random.nextInt(compartmentCapacity);
                int out = random.nextInt(compartmentCapacity - in);
                Compartment compartment = new Compartment(vehicleId, i, in, out, compartmentCapacity);
                String key = vehicleId + "_" + i;
                String value = compartment.toJson();
                System.out.println("Sending Record : " + value);
                ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, key, value);
                producer.send(record);
                System.out.println("Sent successfully !!!");
            }
            try {
                Thread.sleep(eventGenerationInterval);
            } catch (InterruptedException e) {
                System.err.println("Woopsss !!!! Bye Bye. " + e.getMessage());
                producer.close();
                return;
            }
        }
    }
}
