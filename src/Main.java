public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // Create an instance of the container and pass the classes to be managed
        MyContainer container = new MyContainer(ServiceA.class, ServiceB.class);

        // Retrieve the ServiceB instance from the container and execute its method
        ServiceB serviceB = container.getComponent(ServiceB.class);
        serviceB.execute();  // Output: "ServiceB execution, calling Se
    }
}