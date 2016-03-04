package it.dindokey.testespresso.app;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import it.dindokey.testespresso.app.api.ProductsApi;
import it.dindokey.testespresso.app.api.TestApiComponent;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by simone on 2/29/16.
 */
public class MainPresenterTest
{
    @Inject
    ProductsApi mockedProductsApi; //Dagger cannot inject private field

    MainView mockedMainView;

    private MainPresenter presenter;

    @Before
    public void setup()
    {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        AppTest app
                = (AppTest) instrumentation.getTargetContext().getApplicationContext();
        TestApiComponent apiComponent = (TestApiComponent) app.apiComponent();
        apiComponent.inject(this);

        mockedMainView = mock(MainView.class);
        presenter = new MainPresenter(app, mockedMainView);
    }

    @Test
    public void load_products_on_start() throws Exception
    {
        presenter.onStart();
        verify(mockedProductsApi).getProducts();
        verify(mockedMainView).refreshProductList(any(String[].class));
    }
}
