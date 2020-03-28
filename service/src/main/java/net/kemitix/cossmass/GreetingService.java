package net.kemitix.cossmass;

import javax.enterprise.context.Dependent;

@Dependent
public class GreetingService {
    public String goodbye() {
        return "Farewell, World!";
    }

    public String hello() {
        return "Hello, World!";
    }
}
