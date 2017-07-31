import com.authicate.utils.Util;
import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;
import org.junit.Test;

import static com.authicate.utils.Util.displayBytes;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class MutationSignerTest {

    @Test
    public void getPublicKey() throws Exception {
        ECKey ecKey = new ECKey();
        MutationSigner m = new MutationSigner(ecKey);
        for(byte b: m.getPublicKey().toByteArray()){
            System.out.print(b);
        }
        assertThat(m.getPublicKey().toByteArray(), is(ecKey.getPubKey()));

    }

    @Test
    public void testSign(){
        ECKey ecKey = new ECKey();
        MutationSigner m = new MutationSigner(ecKey);
        ByteString mutation = ByteString.copyFrom("314ca6753771fd180be2ea70b121f8a37cfea6a574fc66bf7697e8c9a06ab6fa".getBytes());

        m.sign(mutation).toByteArray();

    }

}
