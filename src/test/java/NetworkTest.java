import exception.NodeNotFoundException;
import exception.NodesAlreadyConnectedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Denis Magdenkov on 15.03.2017.
 */
public class NetworkTest {

    private Network network;

    @Before
    public void init() {
        network = new Network(8);
        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(2, 4);
        network.connect(6, 2);
        network.connect(5, 8);
    }

    @Test(expected = NodesAlreadyConnectedException.class)
    public void testNodesAlreadyConnected() throws Exception {
        network.connect(1, 6);
    }

    @Test(expected = NodesAlreadyConnectedException.class)
    public void testSameNodeConnectionWillFail() throws Exception {
        network.connect(1, 1);
    }

    @Test
    public void checkIndirectConnection() throws Exception {
       assertTrue("Assert indirect connection between 1 and 4. ", network.query(1, 4));
       assertTrue("Assert indirect connection between 6 and 4. ", network.query(6, 4));
    }

    @Test
    public void checkIndirectConnectionReversed() throws Exception {
        assertTrue("Assert reversed indirect connection between 4 and 1.  ", network.query(4, 1));
        assertTrue("Assert reversed indirect connection between 4 and 6.  ", network.query(4, 6));
    }

    @Test
    public void checkDirectConnections() throws Exception {
        assertTrue("Assert direct connection between 1 and 2.  ", network.query(1, 2));
        assertTrue("Assert direct connection between 2 and 4.  ", network.query(2, 4));
    }

    @Test
    public void checkNotConnected() throws Exception {
        assertFalse("Assert nodes with at least one connection 1 and 8 not connected", network.query(1 ,8));
        assertFalse("Assert nodes without any connections not connected  ", network.query(3, 7));
    }

    @Test(expected = NodeNotFoundException.class)
    public void checkAssertNodeIsPresentById() throws Exception {
        network.assertNodeIsPresentById(100);
    }

}