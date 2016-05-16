package it.dindokey.testespresso.app.cache;

import android.os.Bundle;

import it.dindokey.testespresso.app.model.ProductsModel;

/**
 * Created by simone on 5/6/16.
 */
public interface ModelCache
{
    ProductsModel model();
    void setModel(ProductsModel productsModel);
    void initModelFrom(Bundle instanceState);
    void saveModelTo(Bundle instanceState);
}
