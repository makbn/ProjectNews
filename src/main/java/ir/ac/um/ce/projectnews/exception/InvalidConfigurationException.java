package ir.ac.um.ce.projectnews.exception;

public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException() {
        super("invalid flags configuration");
    }

    public InvalidConfigurationException(String message) {
        super(message);
    }
}
