import com.authicate.exception.CustomException;
import com.authicate.models.LedgerInfo;
import com.authicate.models.TransactionData;
import com.google.protobuf.ByteString;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class APIClient {

    /**
     * TODO: Many things.
     */

    public ByteString nameSpace;
    private Unirest _httpClient;
    private static String urlString = null;
    private static String MEDIA_TYPE = "application/json";

    /**
     *
     * @param baseUrl
     */
    public APIClient(String baseUrl) {
        this.urlString = baseUrl;
    }

    /**
     *
     * @return
     */
    public ByteString getNameSpace() {
        return nameSpace;
    }

    /**
     *
     * @param nameSpace
     */
    private void setNameSpace(ByteString nameSpace) {
        this.nameSpace = nameSpace;
    }

    /**
     *
     * @param hexValue
     * @return
     * @throws CustomException
     */
    public ByteString parse(String hexValue) throws CustomException {
        if (hexValue == null) {
            throw new CustomException("The hexValue parameter must not be null.");
        }
        if (hexValue.length() % 2 == 1)
            throw new CustomException("The hexValue parameter must have an even number of digits.");
        byte[] result = new byte[hexValue.length() >> 1];
        for (int i = 0; i < hexValue.length() >> 1; ++i) {
            char[] hexChar = hexValue.toCharArray();
            result[i] = (byte) ((getHexValue(hexChar[i << 1]) << 4) + (getHexValue(hexChar[(i << 1) + 1])));
        }
        return ByteString.copyFrom(result);
    }

    /**
     *
     * @param hex
     * @return
     * @throws CustomException
     */

    private static int getHexValue(char hex) throws CustomException {
        int value = "0123456789ABCDEF".indexOf(Character.toUpperCase(hex));
        if (value < 0)
            throw new CustomException(String.format("The character '{0}' is not a hexadecimal digit.", hex));
        else
            return value;
    }

    /**
     *
     * @return
     * @throws CustomException
     */

    public Object initialize() throws CustomException {
        LedgerInfo info = getInfo();
        ByteString namespace = parse(info.getNameSpace());
        return this;
    }

    /**
     * Map the response to the {@link LedgerInfo} class.
     *
     * @return LedgerInfo class else null.
     * @throws CustomException
     */
    public LedgerInfo getInfo() throws CustomException {
        GetRequest request = Unirest.get(urlString + "info/");
        request.header("Content-Type", MEDIA_TYPE);
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse<JsonNode> response = request.asJson();
            if (response.getStatus() == 200) {
                LedgerInfo ledgerInfo = mapper.readValue(response.getBody().toString(), LedgerInfo.class);
                return ledgerInfo;
            }
        } catch (UnirestException e) {
            throw new CustomException(e.getMessage());
        } catch (JsonParseException e) {
            throw new CustomException(e.getMessage());
        } catch (JsonMappingException e) {
            throw new CustomException(e.getMessage());
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param mutationHashObj
     * @return
     * @throws CustomException
     */
    public TransactionData getTransactionData(Object mutationHashObj) throws CustomException {
        ByteString mutationHash;
        Class bs = ByteString.class;
        if (bs.isInstance(mutationHashObj)) {
            mutationHash = (ByteString) mutationHashObj;
        } else if (String.class.isInstance(mutationHashObj)) {
            mutationHash = parse((String) mutationHashObj);
        } else {
            throw new CustomException("Invalid Mutation Hash");
        }
        GetRequest request = Unirest.get(urlString + "query/transaction").routeParam("format", "raw")
                .routeParam("mutation_hash", mutationHash.toString()).header("Content-Type", MEDIA_TYPE);
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse<JsonNode> response = request.asJson();
            if (response.getStatus() == 200) {
                // TODO: Implement logic here.
            }

        } catch (UnirestException e) {
            throw new CustomException("Unable to get transaction");
        }

        return null;
    }

    /**
     *
     * @param mutation
     * @param signatures
     * @return
     * @throws CustomException
     */
    public boolean submitTransactionData(ByteString mutation, ArrayList<SigningKey> signatures) throws CustomException {
        Map<String, Object> data = new HashMap<>();
        data.put("mutation", mutation.toString());
        data.put("signatures", signatures);
        try {
            String body = new ObjectMapper().writeValueAsString(data);
            RequestBodyEntity request = Unirest.post(urlString+"submit/")
                    .header("Content-Type", MEDIA_TYPE).body(body);
        } catch (IOException e) {
            throw new CustomException("Bad Input" + e);
        }

        return false;
    }


}
