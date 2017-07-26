import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class SigningKey {

    @JsonProperty("pub_key")
    public String publicKey;

    @JsonProperty("signature")
    public String signature;


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
