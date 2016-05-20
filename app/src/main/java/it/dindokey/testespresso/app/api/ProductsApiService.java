package it.dindokey.testespresso.app.api;

import java.util.List;

import rx.Observable;

public interface ProductsApiService
{
    Observable<List<String>> getProducts();
}
