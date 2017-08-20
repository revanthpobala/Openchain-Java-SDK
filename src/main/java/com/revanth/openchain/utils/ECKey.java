package com.revanth.openchain.utils;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECCurve;

/**
 * Created by revanthpobala on 7/31/17.
 */
public class ECKey {


    public static X9ECParameters getSecp256k1() {
        return ECNamedCurveTable.getByName("secp256k1");
    }


    public static ECDomainParameters domainParameters;

}
