package com.revanth.openchain.utils;

import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;

import java.security.SignatureException;


/**
 * Created by revanthpobala on 7/31/17.
 */
public class SignatureEvidence {

    public SignatureEvidence() {
    }

    public SignatureEvidence(ByteString pub_key, ByteString signature) {
        this.pub_key = pub_key;
        this.signature = signature;
    }

    public ByteString getPub_key() {
        return pub_key;
    }

    public void setPub_key(ByteString pub_key) {
        this.pub_key = pub_key;
    }

    public ByteString getSignature() {
        return signature;
    }

    public void setSignature(ByteString signature) {
        this.signature = signature;
    }

    public ByteString pub_key;
    public ByteString signature;


    public boolean verifySignature(byte[] mutationHash) throws SignatureException {

//        return new ECKey().signedMessageToKey(mutationHash.toString(), getSignature().toString())
//                .verify(getSignature().toByteArray(), getPub_key().toByteArray());
        return false;
    }
}
