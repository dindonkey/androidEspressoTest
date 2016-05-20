package it.dindokey.testespresso.app.api;

import java.util.Collections;
import java.util.List;

public class HttpClient
{
    public List<String> get() throws Exception
    {
        Thread.sleep(10000); //simulate slow network communication
        return Collections.singletonList("First product");
    }
}
