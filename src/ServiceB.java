@MyComponent
public class ServiceB {

    @MyAutowired
    private ServiceA serviceA;

    public void execute(){
        System.out.println("serviceB execution");
        serviceA.execute();
    }

}
