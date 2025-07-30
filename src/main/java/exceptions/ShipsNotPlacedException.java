package exceptions;
/**
 * Custom checked exception thrown when ships have not been placed before starting the game.
 * This exception extends {@link Exception} and provides a default message indicating the issue.
 */
public class ShipsNotPlacedException extends Exception {
    public ShipsNotPlacedException() {
        super("No se han colocado barcos.");
    }
}