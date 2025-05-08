package Exceptions;

public class AudioFileNotFoundException extends Exception {
    /**
     * Exception raised when the audio file is not found.
     */
    public AudioFileNotFoundException() {
        super("Error: Audio file not found. Check the file path.");
    }
}


