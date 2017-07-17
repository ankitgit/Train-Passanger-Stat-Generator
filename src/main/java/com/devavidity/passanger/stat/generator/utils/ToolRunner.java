package com.devavidity.passanger.stat.generator.utils;

import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ToolRunner {

    public final String MANDATORY_ARGUMENT = "P";

    public static Properties properties = new Properties();

    public void execute(String[] args) {

        try {

            Options runTimeOptions = new Options();
            runTimeOptions.addRequiredOption(MANDATORY_ARGUMENT, "PropertyFile", true, "Location of the property file");
            CommandLineParser cliParser = new DefaultParser();
            CommandLine parse = cliParser.parse(runTimeOptions, args);

            String propertyFileLocation = parse.getOptionValue(MANDATORY_ARGUMENT);

            FileInputStream fileInputStream = new FileInputStream(propertyFileLocation);

            properties.load(fileInputStream);

            run();

        } catch (ParseException | IOException e) {
            System.err.println(e.getMessage());
        }

    }

    protected Properties getProperties() {
        return properties;
    }

    public abstract void run();

}

