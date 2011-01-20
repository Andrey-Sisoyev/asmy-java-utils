package home.lang;

public interface IEnumWithDefault<T extends Enum<T>> {
    public T getDefaultLiteral();
}
