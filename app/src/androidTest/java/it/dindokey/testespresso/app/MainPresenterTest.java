package it.dindokey.testespresso.app;

import org.junit.Before;
import org.junit.Test;

import it.dindokey.testespresso.app.api.ProductsApi;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by simone on 2/29/16.
 */
public class MainPresenterTest
{
    ProductsApi mockedProductsApi;

    MainView mockedMainView;

    private MainPresenter presenter;

    @Before
    public void setup()
    {

        mockedMainView = mock(MainView.class);
        mockedProductsApi = mock(ProductsApi.class);
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApi, schedulerManager);
    }

    @Test
    public void load_products_on_start() throws Exception
    {
        presenter.resume(mockedMainView);
        verify(mockedProductsApi).getProducts();
        verify(mockedMainView).refreshProductList(any(String[].class));
    }
}
