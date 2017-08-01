package com.authicate.client;

import com.authicate.exception.CustomException;
import com.authicate.models.LedgerInfo;
import com.authicate.models.Mutation;
import com.authicate.models.Transaction;
import com.authicate.models.TransactionData;
import com.authicate.utils.MessageSerializer;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class APIClient {
    public APIClient() {
    }

    public ByteString nameSpace;
    private static String urlString = null;
    private static String MEDIA_TYPE = "application/json";
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private MessageSerializer messageSerializer = new MessageSerializer();

    /**
     * @param baseUrl
     */
    public APIClient(String baseUrl) {
        urlString = baseUrl;
    }

    /**
     * @return
     */
    public ByteString getNameSpace() {
        return nameSpace;
    }

    /**
     * @param nameSpace
     */
    private void setNameSpace(ByteString nameSpace) {
        this.nameSpace = nameSpace;
    }

    /**
     * Return a {@link ByteString} when a hex value is passed
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
     * @return
     * @throws CustomException
     */

    public APIClient initialize() throws CustomException {
        LedgerInfo info = getInfo();
        ByteString namespace = parse(info.getNameSpace());
        this.setNameSpace(namespace);
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
     * Get the related transaction information when a mutation hash is passed.
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
        GetRequest request = Unirest.get(urlString + "query/transaction?format=raw&mutation_hash=" + mutationHashObj.toString())
                .header("Content-Type", MEDIA_TYPE);
        try {
            HttpResponse<JsonNode> response = request.asJson();
            if (response.getStatus() == 200) {
                String result = response.getBody().getObject().get("raw").toString();
                ByteString buffer = parse(result);
                Transaction transaction = messageSerializer.deSerializeTransaction(buffer);
                Mutation mutation = messageSerializer.deserializeMutation(transaction.getMutation());
                byte[] transactionBuffer = buffer.toByteArray();
                byte[] transactionHash = messageSerializer.computeHash(transactionBuffer).getBytes();
                TransactionData data = new TransactionData(transaction, mutation, transaction.getMutation().toString(),
                        ByteString.copyFrom(transactionHash).toString());
                return data;
            }

        } catch (UnirestException e) {
            throw new CustomException("Unable to get transaction");
        } catch (InvalidProtocolBufferException e) {
            throw new CustomException("Unable to get transaction");
        }

        return null;
    }

    /**
     * @param mutation
     * @param signatures
     * @return
     * @throws CustomException
     */
    public TransactionData submitTransactionData(ByteString mutation, List<SigningKey> signatures) throws CustomException {
        Map<String, Object> data = new HashMap<>();
        data.put("mutation", bytesToHex(mutation.toByteArray()));
        data.put("signatures", signatures);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mutation", bytesToHex(mutation.toByteArray()));
        JSONObject childJsonObject = new JSONObject();
        childJsonObject.put("signature",signatures.get(0).getSignature());
        childJsonObject.put("pub_key", signatures.get(0).getPublicKey());
        jsonObject.put("signatures", childJsonObject);
        System.out.println(jsonObject.toString());
        try {
            String body = new ObjectMapper().writeValueAsString(data);
            RequestBodyEntity request = Unirest.post(urlString + "submit/")
                    .header("Content-Type", MEDIA_TYPE).body(jsonObject);
            System.out.println(request.getBody());
            System.out.println(request.asJson().getBody());
        } catch (IOException e) {
            throw new CustomException("Bad Input" + e);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

}
