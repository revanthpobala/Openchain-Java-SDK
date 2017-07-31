package com.authicate.utils;

/**
 * Created by revanthpobala on 7/25/17.
 */
public class Util {

    public static String displayBytes(byte[] value){
        String result = "";
        for(byte b: value){
            result += b;
        }
        System.out.println(result);
        return result;
    }

}
