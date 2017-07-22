package com.devavidity.passanger.stat.generator.utils;

import com.devavidity.passanger.stat.generator.Application;
import org.apache.commons.cli.*;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.FileInputStream;
import java.util.Properties;

public abstract class ToolRunner {

    public static final String EMPTY_VALUE = "EMPTY";
    private final String MANDATORY_PROP_ARGUMENT = "P";
    private final String BOOT_STRAP_ARGUMENT = "B";
    private final String VEHICLE_ID = "V";
    private final String NUMBER_OF_COMPARTMENT = "NC";
    private final String COMPARTMENT_CAPACITY = "CC";
    private final String EVENT_GENERATION_INTERVAL_MS = "I";


    public static Properties properties = new Properties();

    public void execute(String[] args) {
        try {
            Options runTimeOptions = new Options();
            runTimeOptions.addRequiredOption(MANDATORY_PROP_ARGUMENT, "PropertyFile", true, "Location of the property file");
            runTimeOptions.addOption(BOOT_STRAP_ARGUMENT, true, "Boot Strap Server");
            runTimeOptions.addOption(VEHICLE_ID, true, "Vehicle Id");
            runTimeOptions.addOption(NUMBER_OF_COMPARTMENT, true, "Number Of compartment ");
            runTimeOptions.addOption(COMPARTMENT_CAPACITY, true, "Compartment capacity");
            runTimeOptions.addOption(EVENT_GENERATION_INTERVAL_MS, true, "Event generation interval");
            CommandLineParser cliParser = new DefaultParser();
            CommandLine parse = cliParser.parse(runTimeOptions, args);
            Option[] options = parse.getOptions();
            for (Option option : options) {
                if (option.getOpt().contentEquals(MANDATORY_PROP_ARGUMENT)) {
                    String propertyFileLocation = option.getValue(MANDATORY_PROP_ARGUMENT);
                    FileInputStream fileInputStream = new FileInputStream(propertyFileLocation);
                    properties.load(fileInputStream);
                } else if (!option.getValue().equals(EMPTY_VALUE)) {
                    switch (option.getOpt()) {
                        case BOOT_STRAP_ARGUMENT:
                            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, option.getValue());
                            break;
                        case VEHICLE_ID:
                            properties.put(Application.VEHICLE_ID, option.getValue());
                            break;
                        case NUMBER_OF_COMPARTMENT:
                            properties.put(Application.NUMBER_OF_COMPARTMENT, option.getValue());
                            break;
                        case COMPARTMENT_CAPACITY:
                            properties.put(Application.COMPARTMENT_CAPACITY, option.getValue());
                            break;
                        case EVENT_GENERATION_INTERVAL_MS:
                            properties.put(Application.EVENT_GENERATION_INTERVAL_MS, option.getValue());
                            break;
                    }
                }
            }
            run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    protected Properties getProperties() {
        return properties;
    }

    public abstract void run();

}

