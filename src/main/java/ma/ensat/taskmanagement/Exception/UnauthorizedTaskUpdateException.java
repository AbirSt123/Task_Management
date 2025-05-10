package ma.ensat.taskmanagement.Exception;

public class UnauthorizedTaskUpdateException extends RuntimeException {
    public UnauthorizedTaskUpdateException(String message) {
        super(message);
    }
}
