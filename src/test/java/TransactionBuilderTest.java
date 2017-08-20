import com.revanth.openchain.client.APIClient;
import com.revanth.openchain.client.MutationSigner;
import com.revanth.openchain.client.TransactionBuilder;
import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;
import org.junit.Test;

/**
 * Created by revanthpobala on 7/30/17.
 */
public class TransactionBuilderTest {
    @Test
    public void addRecord() throws Exception {
    }

    @Test
    public void setMetaData() throws Exception {
    }

    @Test
    public void addAccountRecord() throws Exception {
    }

    @Test
    public void addSigningKey() throws Exception {
    }

    @Test
    public void build() throws Exception {
    }

    @Test
    public void submit() throws Exception {
        APIClient apiClient = new APIClient("http://localhost:8080/");
        apiClient.initialize();
        TransactionBuilder transactionBuilder = new TransactionBuilder(apiClient);
        //transactionBuilder.setMetaData("");
        ECKey ecKey = new ECKey();
        //ecKey.toAddress();
        MutationSigner m = new MutationSigner(ecKey);
        transactionBuilder.addSigningKey(m);
        String keyV = "/asset/p2pkh/XsHA3oWrNqz5kH3fPHRyu3Ko4iUpX6cHXv/:DATA:asdef";
        ByteString key = ByteString.copyFrom(keyV.getBytes());
        ByteString value = ByteString.copyFrom("{\"name\":\"o\"}".getBytes());
        ByteString version = ByteString.EMPTY;//ByteString.copyFrom("0".getBytes());
        transactionBuilder.addRecord(key, value, version);
        transactionBuilder.submit();

    }

}