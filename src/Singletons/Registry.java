package Singletons;

public class Registry {

    private static Registry registry;

    private Registry() {
        // private constructor
    }

    public static Registry GetRegistry() {
        if (registry == null) {
            registry = new Registry();
        }
        return registry;
    }

    public static synchronized <T> T getSingleton(Class<T> singletonClass) throws SingletonException {
        return switch (singletonClass.getSimpleName()) {
            case "FileHandler" -> (T) FileHandler.GetFileHandler();
            case "Validator" -> (T) Validator.GetValidator();
            case "OrderHandler" -> (T) OrderHandler.GetOrderHandler();
            default -> throw new SingletonException(String.format("Unknown Singleton: \"%s\" not in registry", singletonClass));
        };
    }
}
