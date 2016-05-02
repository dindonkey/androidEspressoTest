package it.dindokey.testespresso.app;

import android.os.Bundle;

import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.view.MainView;

/**
 * Created by simone on 4/14/16.
 */
public class ModelViewHolder
{
    public static final String MODEL = "model";

    private MainView view;

    private ProductsModel model;

    public ModelViewHolder(MainView view, Bundle savedInstanceState)
    {

        this.view = view;
        if(null != savedInstanceState && null != savedInstanceState.getParcelable(MODEL))
        {
            model = savedInstanceState.getParcelable(MODEL);
        }
    }

    public MainView getView()
    {
        return view;
    }

    public void setView(MainView view)
    {
        this.view = view;
    }

    public void setModel(ProductsModel model)
    {
        this.model = model;
    }

    public ProductsModel getModel()
    {
        return model;
    }

    public void saveInstanceState(Bundle outState)
    {
        outState.putParcelable(MODEL,model);
    }
}
