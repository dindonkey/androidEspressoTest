package it.dindokey.testespresso.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by simone on 3/16/16.
 */
public class ProductsModel implements Parcelable
{
    private String[] items;

    public ProductsModel() {
        items = new String[]{};
    }

    protected ProductsModel(Parcel in)
    {
        items = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(items);
    }

    public String[] getItems()
    {
        return items;
    }

    public void setItems(String[] items)
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
