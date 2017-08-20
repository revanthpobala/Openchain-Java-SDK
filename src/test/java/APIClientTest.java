import com.revanth.openchain.client.APIClient;
import com.revanth.openchain.exception.CustomException;
import com.revanth.openchain.models.LedgerInfo;
import com.revanth.openchain.models.TransactionData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by revanthpobala on 7/23/17.
 */
public class APIClientTest {

    //TODO: Use mocks to remove external dependencies.

    public static APIClient apiClient = new APIClient("http://localhost:8080/");

    @Mock
    private LedgerInfo mockLedgerInfo;

    @Mock
    private TransactionData mockTransactionData;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

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
        assertEquals(apiClient.parse("9cee56894928639e").toByteArray().length, 8);
    }

    @Test
    public void getTransactionData() throws Exception {
        APIClient apiClient = new APIClient("http://localhost:8080/");
        String mutation = "c5ea05088f4bbe09960d8ae5491c688b7ff03bc196ff220876b7cca2cc8f0aa9";
        //byte[] res = {-100, -18, 86, -119, 73, 40, 99, -98};
        //assertThat(apiClient.getTransactionData(mutation).getMutation().getNameSpace(), is(ByteString.copyFrom(res)));
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