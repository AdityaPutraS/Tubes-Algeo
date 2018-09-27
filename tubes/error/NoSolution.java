package tubes.error;

public class NoSolution extends  RuntimeException {
    public NoSolution(String message) {
        super(message);
    }
}
