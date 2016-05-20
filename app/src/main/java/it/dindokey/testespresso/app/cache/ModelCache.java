package it.dindokey.testespresso.app.cache;

import android.os.Bundle;

import it.dindokey.testespresso.app.model.ProductsModel;

@SuppressWarnings("unused")
public interface ModelCache
{
    ProductsModel model();
    void setModel(ProductsModel productsModel);
    void initModelFrom(Bundle instanceState);
    void saveModelTo(Bundle instanceState);
}
