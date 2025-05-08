package Exceptions;

public class FXMLLoadingException extends Exception {
    /**
     * Exception raised when an error occurs while loading the FXML file.
     */
    public FXMLLoadingException() {
        super("Error loading the FXML file.");
    }
}
