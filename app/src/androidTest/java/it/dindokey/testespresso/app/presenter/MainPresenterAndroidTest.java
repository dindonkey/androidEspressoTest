package it.dindokey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Created by simone on 3/17/16.
 */
public class MainPresenterAndroidTest
{
    @Mock
    ProductsApiService productsApiServiceMock;
    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(productsApiServiceMock, schedulerManager);
    }

    @Test
    public void save_model_to_instance_state() throws Exception
    {
        ProductsModel testModel = new ProductsModel();
        testModel.setItems(new String[]{"test product"});
        presenter.setProductsModel(testModel);

        Bundle outState = new Bundle();
        presenter.saveInstanceState(outState);

        ProductsModel expected = outState.getParcelable(MainPresenter.MODEL);
        assertEquals(expected, testModel);
    }
}