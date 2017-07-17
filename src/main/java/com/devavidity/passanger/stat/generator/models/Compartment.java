package com.devavidity.passanger.stat.generator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Compartment {
    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonProperty
    private int vehicleId;

    @JsonProperty
    private int compartmentId;

    @JsonProperty
    private int in;

    @JsonProperty
    private int out;

    @JsonProperty
    private int capacity;

    public Compartment(int vehicleId, int compartmentId, int in, int out, int capacity) {
        this.vehicleId = vehicleId;
        this.compartmentId = compartmentId;
        this.in = in;
        this.out = out;
        this.capacity = capacity;
    }

    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @JsonIgnore
    public String toJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
