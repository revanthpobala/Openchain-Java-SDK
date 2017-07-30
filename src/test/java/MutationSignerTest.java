import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by revanthpobala on 7/21/17.
 */
public class MutationSignerTest {
    private MutationSigner mutationSigner = new MutationSigner();
    @Before
    public void setUp() throws Exception {
        mutationSigner = new MutationSigner();
    }

    @After
    public void tearDown() throws Exception {
        mutationSigner = null;
    }

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(mutationSigner.getPublicKey().toString());

    }

}
