package it.dindokey.testespresso.app.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import it.dindokey.testespresso.app.MainView;
import it.dindokey.testespresso.app.MyApplication;
import it.dindokey.testespresso.app.R;
import it.dindokey.testespresso.app.api.ProductsApi;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView
{

    @Inject
    public ProductsApi productsApi;

    private ProductListViewAdapter productListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyApplication) getApplication()).apiComponent().inject(this);

        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_view);

        productListViewAdapter = new ProductListViewAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(productListViewAdapter);

        new AsyncTask<Void, Void, String[]>()
        {
            @Override
            protected void onPostExecute(String[] result)
            {
                refreshProductList(result);
            }

            @Override
            protected String[] doInBackground(Void... params)
            {
                return productsApi.getProducts();
            }
        }.execute();

    }

    @Override
    public void refreshProductList(String[] products)
    {
        productListViewAdapter.setValues(products);
        productListViewAdapter.reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
