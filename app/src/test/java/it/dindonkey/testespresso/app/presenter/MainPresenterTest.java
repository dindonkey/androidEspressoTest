package it.dindonkey.testespresso.app.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import it.dindokey.testespresso.app.SchedulerManager;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by simone on 2/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest
{
    @Mock ProductsApiService mockedProductsApiService;

    @Mock MainView mockedMainView;

    private MainPresenter presenter;

    @Before
    public void setup()
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApiService, schedulerManager);
    }

    @Test
    public void load_products_on_start() throws Exception
    {
        presenter.resume(mockedMainView);
        verify(mockedProductsApiService).getProducts();
        verify(mockedMainView).refreshProductList(Matchers.any(String[].class));
    }

    @Test
    public void retain_model_after_first_load() throws Exception
    {
        when(mockedProductsApiService.getProducts()).thenReturn(new String[]{"test product"});
        presenter.resume(mockedMainView);
        presenter.resume(mockedMainView);
        verify(mockedProductsApiService, times(1)).getProducts();
    }
}
