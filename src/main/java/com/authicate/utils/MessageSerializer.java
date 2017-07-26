package com.authicate.utils;

import com.authicate.Openchain.Mutation;
import com.google.protobuf.ByteString;
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

    @org.jetbrains.annotations.Nullable
    public static byte[] SerializeMutation(Mutation mutation){

        Mutation.Builder builder = Mutation.newBuilder();
        ByteString namespace = mutation.getNamespace();
        return null;
    }

    public static byte[] computeHash(byte [] data){
       return Sha256Hash.createDouble(data).getBytes();
    }




}
