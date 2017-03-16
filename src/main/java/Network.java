import exception.NodeNotFoundException;
import exception.NodesAlreadyConnectedException;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Denis Magdenkov on 15.03.2017.
 */
public class Network {


    private static final int MINIMUM_NODES_AMOUNT = 1;

    private Map<Integer, Node> nodesMap;

    private Network () {}

    public Network(int nodesAmount) {
        if (nodesAmount < MINIMUM_NODES_AMOUNT) {
            throw new IllegalArgumentException("Nodes amount should be positive and bigger then " + MINIMUM_NODES_AMOUNT);
        }

        nodesMap = new HashMap<>();

        IntStream.range(1, nodesAmount + 1)
                .forEach(id -> nodesMap.put(id, new Node(id)));

    }

    public void connect (Integer nodeId1, Integer nodeId2) {
        assertNodesExists(nodeId1, nodeId2);
        assertNodesNotAlreadyConnected(nodeId1, nodeId2);

        nodesMap.get(nodeId1).getConnections().add(nodesMap.get(nodeId2));
        nodesMap.get(nodeId2).getConnections().add(nodesMap.get(nodeId1));
    }


    public boolean query (Integer nodeId1, Integer nodeId2) {
        assertNodesExists(nodeId1, nodeId2);

        Node node1 = nodesMap.get(nodeId1);
        Node node2 = nodesMap.get(nodeId2);

        if (node1.equals(node2)) {
            return true;
        }

        if (node1.getConnections().isEmpty() || node2.getConnections().isEmpty()) {
            return false;
        }

        if (node1.getConnections().contains(node2)) {
            // direct connection
            return true;
        }

        Set<Node> visitedNodes = new HashSet<>();
        Node foundNode = recursiveSearch(node1, node2, visitedNodes);

        return foundNode != null && foundNode.equals(node2);
    }

    private Node recursiveSearch(Node root, Node target, Set<Node> visitedNodes) {
        if (root.equals(target)) {
            return root;
        }
        if (visitedNodes.contains(root)) {
            return null;
        }

        visitedNodes.add(root);

        List<Node> childs = new ArrayList<>(root.getConnections());

        Node tmp;
        if (!childs.isEmpty()) {
            for (Node child : childs) {
                tmp = recursiveSearch(child, target, visitedNodes);
                if (tmp != null) {
                    return tmp;
                }
            }
        }
        return null;
    }

    private void assertNodesNotAlreadyConnected(Integer node1, Integer node2) {
        if (node1.equals(node2) // assume that same node is like already connected
                || nodesMap.get(node1).getConnections().contains(nodesMap.get(node2))) {
            throw new NodesAlreadyConnectedException(MessageFormat.format("Nodes {0} and {1} are already connected!", node1.toString(), node2.toString()));
        }
    }

    private void assertNodesExists(Integer node1, Integer node2) {
        assertNodeIsPresentById(node1);
        assertNodeIsPresentById(node2);
    }


    protected void assertNodeIsPresentById(Integer id) {
        if (!nodesMap.containsKey(id)) {
            throw new NodeNotFoundException(MessageFormat.format("Node w/ id {0} was not found.", id.toString()) );
        }
    }



}
