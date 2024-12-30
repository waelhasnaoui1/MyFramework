import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MyContainer {

    private Map<Class<?>,Object> componentMap = new HashMap<>();

    public MyContainer(Class<?>... componentClasses) {
        // Initialize components and their dependencies
        try {
            for (Class<?> clazz : componentClasses) {
                if (clazz.isAnnotationPresent(MyComponent.class)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    componentMap.put(clazz, instance);
                }
            }

            // Inject dependencies
            for (Object component : componentMap.values()) {
                injectDependencies(component);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize container", e);
        }
    }

    // Method to inject dependencies
    private void injectDependencies(Object component) throws IllegalAccessException {
        for (Field field : component.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = componentMap.get(fieldType);
                if (dependency == null) {
                    throw new RuntimeException("No component found for type: " + fieldType.getName());
                }
                field.setAccessible(true);
                field.set(component, dependency);
            }
        }
    }

    // Method to get a component by its type
    public <T> T getComponent(Class<T> clazz) {
        return (T) componentMap.get(clazz);
    }

}
