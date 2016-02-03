package it.dindokey.testespresso.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by simone on 2/3/16.
 */
public class ProductListViewAdapter extends ArrayAdapter<String>
{
    private String[] values = new String[]{"Caricamento..."};

    public ProductListViewAdapter(Context context, int resource, int textViewResourceId)
    {
        super(context, resource, textViewResourceId);
        addAll(values);
    }

    public void reload()
    {
        clear();
        addAll(values);
    }

    public void setValues(String[] values)
    {
        this.values = values;
    }
}


