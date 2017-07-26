import com.authicate.utils.MessageSerializer;
import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class MutationSigner {

    public ByteString publicKey;
    public ByteString privateKey;

    private MessageSerializer messageSerializer = new MessageSerializer();

    public MutationSigner(){}

    public ByteString getPublicKey() {
        return publicKey;
    }

    private ECKey ecKey;

    /**
     *
     * @param ecKey
     */
    public MutationSigner(ECKey ecKey){
        this.publicKey = ByteString.copyFrom(ecKey.getPubKey());
        this.privateKey = ByteString.copyFrom(ecKey.getPrivKeyBytes());
        this.ecKey = ecKey;
    }

    /**
     *
     * @param mutation
     * @return
     */
    public ByteString sign(ByteString mutation){
        byte[] hash = messageSerializer.computeHash(mutation.toByteArray());
        byte[] signatureBytes = ecKey.sign(Sha256Hash.create(hash)).encodeToDER();
        ByteString signatureBuffer =  ByteString.copyFrom(signatureBytes);

        return signatureBuffer;
    }


}
