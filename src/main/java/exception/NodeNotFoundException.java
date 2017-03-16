package exception;

/**
 * Created by Denis Magdenkov on 15.03.2017.
 */
public class NodeNotFoundException extends RuntimeException {

    public NodeNotFoundException(String message) {
        super(message);
    }
}
