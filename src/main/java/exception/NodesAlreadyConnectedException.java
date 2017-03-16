package exception;

/**
 * Created by Denis Magdenkov on 15.03.2017.
 */
public class NodesAlreadyConnectedException extends RuntimeException {
    public NodesAlreadyConnectedException(String s) {
        super(s);
    }
}
