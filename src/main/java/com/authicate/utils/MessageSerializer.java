package com.authicate.utils;

import com.authicate.Openchain.Mutation;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bitcoinj.core.Sha256Hash;

import java.util.Date;


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
     * @param mutation
     * @return
     */
    public byte[] SerializeMutation(Mutation mutation) {

        Mutation.Builder builder = Mutation.newBuilder();
        ByteString namespace = mutation.getNamespace();
        return null;
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
