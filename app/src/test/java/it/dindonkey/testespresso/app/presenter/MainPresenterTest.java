package it.dindonkey.testespresso.app.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApi;
import rx.schedulers.Schedulers;

/**
 * Created by simone on 2/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest
{
    @Mock ProductsApi mockedProductsApi;

    @Mock MainView mockedMainView;

    private MainPresenter presenter;

    @Before
    public void setup()
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApi, schedulerManager);
    }

    @Test
    public void load_products_on_start() throws Exception
    {
        presenter.resume(mockedMainView);
        Mockito.verify(mockedProductsApi).getProducts();
        Mockito.verify(mockedMainView).refreshProductList(Matchers.any(String[].class));
    }
}
