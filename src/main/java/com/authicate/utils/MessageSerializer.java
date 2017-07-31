package com.authicate.utils;

import com.authicate.Openchain;
import com.authicate.Openchain.Mutation;
import com.authicate.Openchain.RecordValue;
import com.authicate.Openchain.Transaction;
import com.authicate.exception.CustomException;
import com.authicate.models.Record;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.Networks;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
     * @param localMutation
     * @return
     */
    public byte[] SerializeMutation(com.authicate.models.Mutation localMutation) throws CustomException {
        Mutation.Builder builder = Mutation.newBuilder();
        ByteString namespace = localMutation.getNameSpace();
        builder.setNamespace(namespace);
        ByteString metadata = localMutation.getMetaData();
        builder.setMetadata(metadata);
        List<Record> records = localMutation.getRecords();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getValue() != null) {
                Openchain.Record.Builder recordBuilder = Openchain.Record.newBuilder();
                ByteString key = records.get(i).getKey();
                ByteString version = records.get(i).getVersion();
                recordBuilder.setVersion(version);
                recordBuilder.setValue(RecordValue.newBuilder().setData(records.get(i).getValue()));
                //ByteString r = records.get(i).getValue();
                builder.addRecords(recordBuilder);
            }
        }
        return builder.build().toByteArray();
    }

    /**
     * @param data
     * @return
     * @throws InvalidProtocolBufferException
     */
    public com.authicate.models.Mutation deserializeMutation(ByteString data) throws InvalidProtocolBufferException, CustomException {
        Mutation.Builder mutation = Mutation.newBuilder();
        mutation.mergeFrom(data);
        List<Openchain.Record> records = mutation.getRecordsList();
        List<Record> collectionRecords = new ArrayList<>();
        ByteString nameSpace = ByteString.copyFrom(mutation.getNamespace().toByteArray());
        ByteString metaData = ByteString.copyFrom(mutation.getMetadata().toByteArray());
        for(Openchain.Record record: records){
            collectionRecords.add(new Record(nameSpace,record.getValue()!=null ?
                    ByteString.copyFrom(record.getValue().getData().toByteArray()): null, metaData));
        }
        return new com.authicate.models.Mutation(nameSpace, collectionRecords, metaData);
    }

    /**
     * @param transaction
     * @return
     */
    public static byte[] serializeTransaction(com.authicate.models.Transaction transaction) {
        Transaction.Builder transactions = Transaction.newBuilder();
        transactions.setMutation(transaction.getMutation());
        // Check the timestamp here.
        //https://github.com/openchain/openchain/blob/3422c96258d0251b7221ef2be1c9f79a7d26ebdb/src/Openchain.Abstractions/MessageSerializer.cs#L92
        transactions.setTimestamp(transaction.getTimeStamp() - Instant.now().toEpochMilli());
        transactions.setTransactionMetadata(transaction.getTransactionMetadata());
        return transactions.build().toByteArray();
    }

    public static com.authicate.models.Transaction deSerializeTransaction(ByteString data) throws InvalidProtocolBufferException {
        Transaction.Builder transactions = Transaction.newBuilder();
        transactions.mergeFrom(data);
        return new com.authicate.models.Transaction(ByteString.copyFrom(transactions.getMutation().toByteArray()),
                transactions.getTimestamp(), ByteString.copyFrom(transactions.getTransactionMetadata().toByteArray()));
    }

    /**
     * Compute the hash of hash of the data.
     *
     * @param data
     * @return
     */
//    public static byte[] computeHash(byte[] data) {
//        return Sha256Hash.createDouble(data).getBytes();
//    }

    public static Sha256Hash computeHash(byte[] data) {
        return Sha256Hash.createDouble(data);
    }
}
