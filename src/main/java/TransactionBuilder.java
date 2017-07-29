import com.authicate.Openchain.Record;
import com.authicate.exception.CustomException;
import com.authicate.models.Mutation;
import com.authicate.models.TransactionData;
import com.authicate.utils.MessageSerializer;
import com.google.protobuf.ByteString;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by revanthpobala on 7/27/17.
 */
public class TransactionBuilder {
    private APIClient apiClient = new APIClient();
    private List<Record> records;
    private List<MutationSigner> mutationSigners;
    private ByteString metaData;
    ObjectMapper mapper = new ObjectMapper();
    private MessageSerializer messageSerializer = new MessageSerializer();

    /**
     *
     * @param apiClient
     * @throws CustomException
     */
    public TransactionBuilder(APIClient apiClient) throws CustomException {
        if (apiClient.getNameSpace() == null) {
            throw new CustomException("The API client has not been initialized");
        }
        this.apiClient = apiClient;
        this.metaData = ByteString.EMPTY;
        this.mutationSigners = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    /**
     *
     * @param key
     * @param value
     * @param version
     * @return
     * @throws IOException
     */
    public TransactionBuilder addRecord(ByteString key, Object value, ByteString version) throws IOException {
        ByteString recordValue = null;

        if (value.getClass().isInstance(ByteString.class)) {
            recordValue = (ByteString) value;
        } else {
            // C# equivalent code  recordValue = new ByteString(Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(value)));
            recordValue = ByteString.copyFrom(Charset.forName("UTF-8").encode(mapper.writeValueAsString(value)));
        }
        Record newRecord = new Record(key, recordValue, version);
        records.add(newRecord);
        return this;
    }

    /**
     *
     * @param data
     * @return
     * @throws IOException
     */
    public TransactionBuilder setMetaData(Object data) throws IOException {
        metaData = ByteString.copyFrom(Charset.forName("UTF-8").encode(mapper.writeValueAsString(data)));
        return this;
    }

    /**
     *
     * @return
     */
    public TransactionBuilder addAccountRecord() {
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public TransactionBuilder addSigningKey(MutationSigner key) {
        mutationSigners.add(key);
        return this;
    }

    /**
     *
     * @return
     * @throws CustomException
     */
    public ByteString build() throws CustomException {
        Mutation mutation = new com.authicate.models.Mutation(apiClient.getNameSpace(), records, metaData);
        return ByteString.copyFrom(messageSerializer.SerializeMutation(mutation));
    }

    /**
     *
     * @return
     * @throws CustomException
     */
    public TransactionData submit() throws CustomException {
        ByteString mutation = build();
        List<SigningKey> signatures = new ArrayList<>();
        for (MutationSigner ms : mutationSigners) {
            signatures.add(new SigningKey(ms.getPublicKey().toString(), ms.sign(mutation).toString()));
        }
        return apiClient.submitTransactionData(mutation, signatures);
    }

}
