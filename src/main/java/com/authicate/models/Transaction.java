package com.authicate.models;

import com.google.protobuf.ByteString;

import java.time.Instant;

/**
 * Created by revanthpobala on 7/29/17.
 */
public class Transaction {

    public ByteString mutation;
    public long timeStamp;
    public ByteString transactionMetadata;

    public Transaction(ByteString mutation, long timeStamp, ByteString transactionMetadata) {
        this.mutation = mutation;
        this.timeStamp = timeStamp;
        this.transactionMetadata = transactionMetadata;
    }

    public ByteString getMutation() {
        return mutation;
    }

    public void setMutation(ByteString mutation) {
        this.mutation = mutation;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ByteString getTransactionMetadata() {
        return transactionMetadata;
    }

    public void setTransactionMetadata(ByteString transactionMetadata) {
        this.transactionMetadata = transactionMetadata;
    }


}
