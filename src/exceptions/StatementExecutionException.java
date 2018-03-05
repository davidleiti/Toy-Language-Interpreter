package exceptions;

public class StatementExecutionException extends RuntimeException {
    public StatementExecutionException(String message) {
        super(message);
    }
}
