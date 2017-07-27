import com.authicate.exception.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by revanthpobala on 7/23/17.
 */
public class APIClientTest {

    public static APIClient apiClient = new APIClient("http://localhost:8080/");

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        apiClient = null;
    }

    @Test
    public void getNameSpace() throws Exception {
    }

    @Test
    public void getInfo() throws Exception {
        assertThat(apiClient.getInfo().getNameSpace(), is("9cee56894928639e"));
    }

    @Test
    public void testParse() throws CustomException {
        System.out.println(apiClient.parse("9cee56894928639e"));
    }

    @Test
    public void getTransactionData() throws Exception {
        String mutation = "c3c1620b4a1f1ac5c7880de7f8e0abe03f80efba07b753d91dcfe454d40aaa54";
        apiClient.getTransactionData(mutation);
    }

    @Test
    public void testHexToBytes() {
        byte[] res = {-100, -18, 86, -119, 73, 40, 99, -98};
        assertThat(APIClient.bytesToHex(res), is("9CEE56894928639E"));
    }

    @Test
    public void submitTransactionData() throws Exception {
    }

}