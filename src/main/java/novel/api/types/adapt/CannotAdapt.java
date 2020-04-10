package novel.api.types.adapt;
import novel.api.types.read.CannotRead;
import novel.api.types.token.TypeToken;
import novel.api.types.write.CannotWrite;

/**
 * This class repesents a token's adapter that cannot be adapted.
 * @param <T>
 */
public final class CannotAdapt<T> implements ObjectDataAdapter<T>, CannotRead<T>, CannotWrite<T> {
    private final TypeToken<T> token;
    public CannotAdapt(TypeToken<T> token) {this.token = token;}
    @Override
    public TypeToken<?> token() {
        return token;
    }
}