package com.revanth.openchain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class AclSubject {

    @JsonProperty("addresses")
    public List<String> addresses;

    @JsonProperty("required")
    public int required;

    public AclSubject(){
        addresses = new ArrayList<String>();
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }


}
