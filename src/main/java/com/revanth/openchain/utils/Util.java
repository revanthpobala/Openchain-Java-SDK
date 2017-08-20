package com.revanth.openchain.utils;

import com.revanth.openchain.client.APIClient;
import com.revanth.openchain.exception.CustomException;
import com.revanth.openchain.models.Mutation;
import com.google.protobuf.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by revanthpobala on 7/25/17.
 */
public class Util {

    private MessageSerializer messageSerializer = new MessageSerializer();
    private SignatureEvidence signatureEvidence = new SignatureEvidence();
    private APIClient apiClient = new APIClient();
    public static String displayBytes(byte[] value) {
        String result = "";
        for (byte b : value) {
            result += b;
        }
        System.out.println(result);
        return result;
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

    public JSONObject post(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            ByteString parsedMutation;
            if (jsonObject.get("mutation").getClass().isInstance(JSONObject.class)
                    && jsonObject.get("signatures").getClass().isInstance(JSONArray.class)) {
                System.out.println("Not a valid json array");
            }
            parsedMutation = parse(jsonObject.getString("mutation"));
            List<SignatureEvidence> authentication = new ArrayList<>();
            JSONObject evidence = new JSONObject(jsonObject.get("signatures").toString());
//            if (!(evidence != null && evidence.get("pub_key").getClass().isInstance(JSONObject.class)
//                    && evidence.get("signature").getClass().isInstance(JSONObject.class))) {
//                System.out.println("Not a valid json");
//            }

            authentication.add(new SignatureEvidence(
                    parse(evidence.get("pub_key").toString()), parse(evidence.get("signature").toString())));
            Mutation mutation = messageSerializer.deserializeMutation(parsedMutation);
            apiClient.bytesToHex(mutation.getNameSpace().toByteArray());
            mutation.getRecords().get(0).getValue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
