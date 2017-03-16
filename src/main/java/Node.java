import java.util.HashSet;
import java.util.Set;

/**
 * Created by Denis Magdenkov on 15.03.2017.
 */
public class Node {

    private Integer id;

    private Set<Node> connections =  new HashSet<>();

    private Node() {}

    public Node(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Node> getConnections() {
        return connections;
    }

    public void setConnections(Set<Node> connections) {
        this.connections = connections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return getId().equals(node.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}
