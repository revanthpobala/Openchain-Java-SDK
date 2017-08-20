package com.revanth.openchain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by revanthpobala on 7/21/17.
 */

/**
 * Data structure for Transanction Data
 */
public class TransactionData {

    @JsonProperty("transaction")
    public Transaction transaction;

    @JsonProperty("mutation")
    public Mutation mutation;

    @JsonProperty("transaction_hash")
    public String transactionHash;

    @JsonProperty("mutation_hash")
    public String mutationHash;

    public TransactionData(Transaction transaction, Mutation mutation, String transactionHash, String mutationHash) {
        this.transaction = transaction;
        this.mutation = mutation;
        this.transactionHash = transactionHash;
        this.mutationHash = mutationHash;
    }


    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getMutationHash() {
        return mutationHash;
    }

    public void setMutationHash(String mutationHash) {
        this.mutationHash = mutationHash;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


}
