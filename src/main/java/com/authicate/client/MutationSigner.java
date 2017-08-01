package com.authicate.client;

import com.authicate.utils.MessageSerializer;
import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

import static com.authicate.utils.Util.displayBytes;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class MutationSigner {


    private MessageSerializer messageSerializer = new MessageSerializer();
    private ECKey ecKey;
    public ByteString pub_key;
    public ByteString privateKey;


    /**
     * @param ecKey
     */
    public MutationSigner(ECKey ecKey) {
        this.pub_key = ByteString.copyFrom(ecKey.getPubKey());
        this.privateKey = ByteString.copyFrom(ecKey.getPrivKeyBytes());
        this.ecKey = ecKey;
    }


    public ByteString getPublicKey() {
        return pub_key;
    }

    /**
     * @param mutation
     * @return
     */
    public ByteString sign(ByteString mutation) {
        Sha256Hash hash = messageSerializer.computeHash(mutation.toByteArray());
        byte[] signatureBytes = ecKey.sign(hash).encodeToDER();
        System.out.println("verify bytes "+ecKey.verify(hash.getBytes(), signatureBytes));
        System.out.println("verify "+ecKey.verify(hash.getBytes(), signatureBytes));
        ByteString signatureBuffer = ByteString.copyFrom(signatureBytes);
        return signatureBuffer;
    }

}
