package it.dindokey.testespresso.app.cache;

import android.os.Bundle;

import it.dindokey.testespresso.app.model.ProductsModel;

public class InstanceStateCache implements ModelCache
{
    public static final String MODEL = "model";

    private ProductsModel model;

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
    public void initModelFrom(Bundle instanceState)
    {
        if(null != instanceState && null != instanceState.getParcelable(MODEL))
        {
            model = instanceState.getParcelable(MODEL);
        }
    }

    @Override
    public void saveModelTo(Bundle instanceState)
    {
        instanceState.putParcelable(MODEL,model);
    }
}
