package it.dindokey.testespresso.app.view;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

class ProductListViewAdapter extends ArrayAdapter<String>
{
    private List<String> values = new ArrayList<String>();

    public ProductListViewAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1);
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


