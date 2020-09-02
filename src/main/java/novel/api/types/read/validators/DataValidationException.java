package novel.api.types.read.validators;

/**
 * Created on 8/27/2020.
 */
public class DataValidationException extends IllegalArgumentException {

    public DataValidationException(String category, Object data) {
        super(String.format("Invalid %s: %s", category, data));
    }
}
