package Exceptions;

public class CardNotFoundException extends Exception {
    /**
     * Exception raised when a card is not found in the JSON data.
     */
    public CardNotFoundException() {
        super("Card not found in the JSON data");
    }
}
