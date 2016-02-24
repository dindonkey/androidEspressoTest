package it.dindokey.testespresso.app.activity;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by simone on 2/3/16.
 */
public class ProductListViewAdapter extends ArrayAdapter<String>
{
    private String[] values = new String[]{"Loading..."};

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


