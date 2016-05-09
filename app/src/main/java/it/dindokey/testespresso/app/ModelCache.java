package it.dindokey.testespresso.app;

import it.dindokey.testespresso.app.model.ProductsModel;

/**
 * Created by simone on 5/6/16.
 */
public interface ModelCache
{
    ProductsModel model();
    void save();
}
