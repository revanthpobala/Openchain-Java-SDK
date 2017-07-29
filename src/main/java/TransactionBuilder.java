import com.authicate.Openchain.Record;
import com.authicate.exception.CustomException;
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


    public TransactionBuilder(APIClient apiClient) throws CustomException {
        if (apiClient.getNameSpace() == null) {
            throw new CustomException("The API client has not been initialized");
        }
        this.apiClient = apiClient;
        this.metaData = ByteString.EMPTY;
        this.mutationSigners = new ArrayList<>();
        this.records = new ArrayList<>();
    }

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


    public TransactionBuilder setMetaData(Object data) throws IOException {
        metaData = ByteString.copyFrom(Charset.forName("UTF-8").encode(mapper.writeValueAsString(data)));
        return this;
    }

    public TransactionBuilder addAccountRecord() {
        return null;
    }


    public TransactionBuilder addSigningKey(MutationSigner key) {
        mutationSigners.add(key);
        return this;
    }

    public ByteString build() throws CustomException {
        Object mutation = new com.authicate.models.Mutation(apiClient.getNameSpace(), records, metaData);
        // TODO Figure out a way to get the mutation object.
        //return ByteString.copyFrom(messageSerializer.SerializeMutation(mutation));
        return null;
    }

}
