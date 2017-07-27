import com.authicate.Openchain.Record;
import com.authicate.exception.CustomException;
import com.google.protobuf.ByteString;

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


    public TransactionBuilder(APIClient apiClient) throws CustomException {
        if (apiClient.getNameSpace() == null) {
            throw new CustomException("The API client has not been initialized");
        }
        this.apiClient = apiClient;
        this.metaData = ByteString.EMPTY;
        this.mutationSigners = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public TransactionBuilder addRecord(ByteString key, Object value, ByteString version) {
        ByteString recordValue = null;

        if (value.getClass().isInstance(ByteString.class)) {
            recordValue = (ByteString) value;
        } else {
            //TODO Deserialize JSON object and encode it to UTF8 Bytes
            // C# equivalent code  recordValue = new ByteString(Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(value)));
            //recordValue = ByteString.copyFrom( value);
        }
        //Record newRecord =


        return null;
    }
}
