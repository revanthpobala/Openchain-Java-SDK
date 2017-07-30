import com.authicate.exception.CustomException;
import com.authicate.models.LedgerInfo;
import com.authicate.models.TransactionData;
import com.google.protobuf.ByteString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        String mutation = "c3c1620b4a1f1ac5c7880de7f8e0abe03f80efba07b753d91dcfe454d40aaa54";
        byte[] res = {-100, -18, 86, -119, 73, 40, 99, -98};
        assertThat(apiClient.getTransactionData(mutation).getMutation().getNameSpace(), is(ByteString.copyFrom(res)));
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