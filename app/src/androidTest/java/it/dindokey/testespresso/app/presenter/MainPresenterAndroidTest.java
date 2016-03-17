package it.dindokey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Test;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by simone on 3/17/16.
 */
public class MainPresenterAndroidTest
{
    @Test
    public void save_model_to_instance_state() throws Exception
    {
        ProductsApiService productsApiServiceMock = mock(ProductsApiService.class);
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        MainPresenter presenter = new MainPresenter(productsApiServiceMock, schedulerManager);

        ProductsModel testModel = new ProductsModel();
        testModel.setItems(new String[]{"test product"});
        presenter.productsModel = testModel;

        Bundle outState = new Bundle();
        presenter.saveInstanceState(outState);

        ProductsModel expected = outState.getParcelable(MainPresenter.MODEL);
        assertEquals(expected, testModel);

    }
}
