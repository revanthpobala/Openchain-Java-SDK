import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by revanthpobala on 7/23/17.
 */
public class APIClientTest {
    private APIClient apiClient;

    @Before
    public void setUp() throws Exception {
        APIClient apiClient = new APIClient("http://localhost:8080/");
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
        APIClient apiClient = new APIClient("http://localhost:8080/");
        assertThat(apiClient.getInfo().getNameSpace(), is("9cee56894928639e"));
    }

    @Test
    public void getTransactionData() throws Exception {
    }

    @Test
    public void submitTransactionData() throws Exception {
    }

}