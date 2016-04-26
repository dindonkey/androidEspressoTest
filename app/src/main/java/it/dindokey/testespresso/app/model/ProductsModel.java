package it.dindokey.testespresso.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone on 3/16/16.
 */
public class ProductsModel implements Parcelable
{
    private List<String> items;

    public ProductsModel() {
        items = new ArrayList<String>();
    }

    protected ProductsModel(Parcel in)
    {
        items = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringList(items);
    }

    public List<String> getItems()
    {
        return items;
    }

    public void setItems(List<String> items)
    {
        this.items = items;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>()
    {
        @Override
        public ProductsModel createFromParcel(Parcel in)
        {
            return new ProductsModel(in);
        }

        @Override
        public ProductsModel[] newArray(int size)
        {
            return new ProductsModel[size];
        }
    };
}
