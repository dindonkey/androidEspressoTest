package it.dindokey.testespresso.app.api;

import java.util.Arrays;
import java.util.List;

public class HttpClient
{
    public List<String> get() throws Exception
    {
        Thread.sleep(10000); //simulate slow network communication
        return Arrays.asList("First product");
    }
}
