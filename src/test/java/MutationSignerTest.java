import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by revanthpobala on 7/21/17.
 */
@RunWith(Arquillian.class)
public class MutationSignerTest {
    private MutationSigner mutationSigner;
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


    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MutationSigner.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
