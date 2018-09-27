package tubes.error;

public class MismatchedSize extends RuntimeException {
    public MismatchedSize(String message) {
        super(message);
    }
}