package com.authicate.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class SigningKey {

    @JsonProperty("pub_key")
    public String pub_key;

    @JsonProperty("signature")
    public String signature;

    public SigningKey(String pub_key, String signature) {
        this.pub_key = pub_key;
        this.signature = signature;
    }

    public String getPublicKey() {
        return pub_key;
    }

    public void setPublicKey(String pub_key) {
        this.pub_key = pub_key;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
