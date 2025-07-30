package exceptions;
/**
 * Custom unchecked exception thrown when an image resource cannot be found.
 * This exception extends {@link RuntimeException} and is intended to signal
 * missing image files during runtime, such as when loading resources via {@code getResourceAsStream}.
 */
public class ImageNotFoundException extends RuntimeException {
  public ImageNotFoundException(String message) {
    super(message);
  }
}