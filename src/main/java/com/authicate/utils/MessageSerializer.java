package com.authicate.utils;

import com.authicate.Openchain;
import com.authicate.Openchain.Mutation;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bitcoinj.core.Sha256Hash;

import java.util.Date;
import java.util.List;

import static com.authicate.Openchain.*;


/**
 * Created by revanthpobala on 7/21/17.
 */

/**
 * Utility class for message serializer.
 */

public class MessageSerializer {

    private static long date = 0L;

    private static Date epoch = new Date(date);

    /**
     * S
     * @param localMutation
     * @return
     */
    public byte[] SerializeMutation(com.authicate.models.Mutation localMutation) {
        Mutation mutation = null;
        Mutation.Builder builder = Mutation.newBuilder();
        ByteString namespace = localMutation.getNameSpace();
        ByteString metadata = localMutation.getMetaData();
        List<Record> records = localMutation.getRecords();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getValue() != null) {
                ByteString key = records.get(i).getKey();
                ByteString version = records.get(i).getVersion();
                RecordValue r = records.get(i).getValue();
                builder.addRecords(new Record(key, ByteString.copyFrom(r.toByteArray()), version));
            }
        }
        return builder.build().toByteArray();
    }

    /**
     * @param data
     * @return
     * @throws InvalidProtocolBufferException
     */
    public Mutation deserializeMutation(ByteString data) throws InvalidProtocolBufferException {
        Mutation.Builder mutation = Mutation.newBuilder();
        mutation.mergeFrom(data);
        //mutation.set
        return null;

    }

    /**
     * Compute the hash of hash of the data.
     *
     * @param data
     * @return
     */
    public static byte[] computeHash(byte[] data) {
        return Sha256Hash.createDouble(data).getBytes();
    }


}
