package it.dindokey.testespresso.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import javax.inject.Inject;

import it.dindokey.testespresso.app.App;
import it.dindokey.testespresso.app.MainPresenter;
import it.dindokey.testespresso.app.MainView;
import it.dindokey.testespresso.app.R;

public class MainActivity extends AppCompatActivity implements MainView
{
    private ProductListViewAdapter productListViewAdapter;

    @Inject MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        productListViewAdapter = new ProductListViewAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(productListViewAdapter);

        ((App)getApplication()).getComponent().inject(this);
        mainPresenter.resume(this);
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
