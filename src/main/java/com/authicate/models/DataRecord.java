package com.authicate.models;

import com.authicate.Openchain.Record;
import com.google.protobuf.ByteString;

/**
 * Created by revanthpobala on 7/21/17.
 */

/**
 * Datastructure for Record.
 */
public class DataRecord extends Record {


    public String Data;

    public DataRecord(ByteString key, ByteString value, ByteString version, String data) {
        super();
        Data = data;
    }

    public String getData() {
        return Data;
    }
}
