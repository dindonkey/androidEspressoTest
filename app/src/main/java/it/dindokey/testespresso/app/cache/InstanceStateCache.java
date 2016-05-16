package it.dindokey.testespresso.app.cache;

import android.os.Bundle;

import it.dindokey.testespresso.app.model.ProductsModel;

/**
 * Created by simone on 5/13/16.
 */
public class InstanceStateCache implements ModelCache
{
    public static final String MODEL = "model";

    private ProductsModel model;

    public InstanceStateCache(Bundle savedInstanceState)
    {
        if(null != savedInstanceState && null != savedInstanceState.getParcelable(MODEL))
        {
            model = savedInstanceState.getParcelable(MODEL);
        }
    }

    @Override
    public ProductsModel model()
    {
        return model;
    }

    @Override
    public void setModel(ProductsModel productsModel)
    {
        model = productsModel;


    }

    @Override
    public void saveModelTo(Bundle instanceState)
    {
        instanceState.putParcelable(MODEL,model);
    }
}
