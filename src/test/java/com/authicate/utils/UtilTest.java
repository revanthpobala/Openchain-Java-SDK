package com.authicate.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by revanthpobala on 7/31/17.
 */
public class UtilTest {
    private Util util = new Util();

    @Test
    public void post() throws Exception {
//        String data  = "{\"mutation\":\"0a089cee56894928639e122412220a207b2276616c696455746638223a747275652c22656d707479223a66616c73657d\"," +
//                "\"signatures\":{\"signature\":\"3045022100db3ef1077978d66f846a162afeec1fa5134fdfa0209da541027d9d2f476878280220229c88fe642e5bdec3965d7711d554a0870ffd591a58cc77b1359bf173dc2f6d\"," +
//                "\"pub_key\":\"02bf31e8b30ddf36a636fb36133b0d6b73e5946bd3939e45efd1d24a9a9cfdaf86\"}}";

        String data  = "{\"mutation\":\"0a082f0d828a60f1572712160a036b657912060a04646174611a0776657273696f6e1a086d65746164617461\"," +
                "\"signatures\":{\"signature\":\"304502210083a2eb1d9ae027fe47c8ec88e6d9e6a3903f5d0e5602fa232a0fe221dae256b102206f5aedd1458a17f78996f0a4c9399230681e7de996fa4b3f66cda0aa0e88614e\"," +
                "\"pub_key\":\"02ca6257f38ff9d437b1bbfdb9016a81531592e8d38ea68f247c14fa777056a4aa\"}}";

        util.post(data);

    }

}