package it.dindonkey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
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

    @Mock Bundle savedInstanceStateMock;

    private MainPresenter presenter;
    private String[] sampleProducts;

    @Before
    public void setup()
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApiService, schedulerManager);

        sampleProducts = new String[]{"test product"};
        when(mockedProductsApiService.getProducts()).thenReturn(sampleProducts);
    }

    @Test
    public void load_products_on_resume() throws Exception
    {
        presenter.resume(mockedMainView, savedInstanceStateMock);
        verify(mockedProductsApiService).getProducts();
        verify(mockedMainView, atLeastOnce()).refreshProductList(sampleProducts);
    }

    @Test
    public void retain_model_after_first_load() throws Exception
    {
        presenter.resume(mockedMainView, savedInstanceStateMock);
        presenter.resume(mockedMainView, savedInstanceStateMock);
        verify(mockedProductsApiService, times(1)).getProducts();
    }

    @Test
    public void load_model_from_saved_instance_state_and_update_view() throws Exception
    {
        ProductsModel model = new ProductsModel();
        model.setItems(sampleProducts);

        when(savedInstanceStateMock.getParcelable(anyString())).thenReturn(model);
        presenter.resume(mockedMainView, savedInstanceStateMock);

        assertEquals(model, presenter.getProductsModel());
        verify(mockedMainView).refreshProductList(sampleProducts);
    }
}
