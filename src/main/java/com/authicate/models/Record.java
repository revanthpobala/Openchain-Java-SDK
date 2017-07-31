package com.authicate.models;

import com.authicate.exception.CustomException;
import com.google.protobuf.ByteString;

/**
 * Created by revanthpobala on 7/30/17.
 */
public class Record {

    private ByteString key;
    private ByteString value;
    private ByteString version;

    public Record(ByteString key, ByteString value, ByteString version) throws CustomException {

        if (key == null) {
            throw new CustomException("Something is wrong with the key");
        }
        if (version == null) {
            throw new CustomException("Something is wrong with the version");
        }
        this.key = key;
        this.value = value;
        this.version = version;
    }

    public ByteString getKey() {
        return key;
    }

    public void setKey(ByteString key) {
        this.key = key;
    }

    public ByteString getValue() {
        return value;
    }

    public void setValue(ByteString value) {
        this.value = value;
    }

    public ByteString getVersion() {
        return version;
    }

    public void setVersion(ByteString version) {
        this.version = version;
    }


}
