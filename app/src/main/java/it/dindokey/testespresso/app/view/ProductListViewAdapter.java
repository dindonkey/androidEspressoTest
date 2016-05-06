package it.dindokey.testespresso.app.view;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone on 2/3/16.
 */
public class ProductListViewAdapter extends ArrayAdapter<String>
{
    private List<String> values = new ArrayList<String>();

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

    public void setValues(List<String> values)
    {
        this.values = values;
    }
}


