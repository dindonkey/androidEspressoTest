package it.dindokey.testespresso.app.api;

import java.util.List;

import rx.Observable;

/**
 * Created by simone on 4/26/16.
 */
public interface ProductsApiService
{
    Observable<List<String>> getProducts();
}
