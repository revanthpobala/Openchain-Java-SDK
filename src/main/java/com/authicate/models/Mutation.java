package com.authicate.models;

import com.authicate.Openchain.*;
import com.authicate.exception.CustomException;
import com.google.protobuf.ByteString;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by revanthpobala on 7/21/17.
 * <p>
 * Please refer: https://github.com/openchain/openchain/blob/master/src/Openchain.Abstractions/Mutation.cs
 */
public class Mutation {


    private ByteString nameSpace;
    private List<Record> records = new ArrayList<>();
    private ByteString metaData;

    public ByteString getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(ByteString nameSpace) {
        this.nameSpace = nameSpace;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public ByteString getMetaData() {
        return metaData;
    }

    public void setMetaData(ByteString metaData) {
        this.metaData = metaData;
    }

    /**
     * Constructor
     *
     * @param nameSpace
     * @param records
     * @param metaData
     * @throws CustomException
     */
    public Mutation(ByteString nameSpace, List<Record> records, ByteString metaData) throws CustomException {
        if (nameSpace == null) {
            throw new CustomException("Something is wrong with the name space");
        }
        if (records == null) {
            throw new CustomException("Something is wrong with the records");
        }
        if (metaData == null) {
            throw new CustomException("Something is wrong with the meta data");
        }
        this.nameSpace = nameSpace;
        this.records = records;
        this.metaData = metaData;

        for (Record record : records) {
            if (record.getSerializedSize() == 0) {
                throw new CustomException("Something is wrong with the records");
            }
        }
        HashSet<ByteString> keys = new HashSet<ByteString>();
        for (Record record : records) {
            if (keys.contains(record.getKey())) {
                throw new CustomException("Duplicate Records");
            }
            keys.add(record.getKey());
        }
    }

}
