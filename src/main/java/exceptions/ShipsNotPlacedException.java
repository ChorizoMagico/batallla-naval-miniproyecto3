package exceptions;

public class ShipsNotPlacedException extends Exception {
    public ShipsNotPlacedException() {
        super("No se han colocado barcos.");
    }
}