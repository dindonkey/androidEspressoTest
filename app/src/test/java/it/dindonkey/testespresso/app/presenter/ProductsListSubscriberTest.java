package it.dindonkey.testespresso.app.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.cache.ModelCache;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.presenter.ProductsListSubscriber;
import it.dindokey.testespresso.app.view.MainView;
import it.dindonkey.testespresso.app.AppTestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by simone on 5/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductsListSubscriberTest extends AppTestCase
{
    @Mock
    MainView mainViewMock;
    @Mock
    ModelCache modelCacheMock;

    private ProductsListSubscriber productsListSubscriber;

    @Before
    public void setUp() throws Exception
    {
        productsListSubscriber = new ProductsListSubscriber(mainViewMock, modelCacheMock);
    }

    @Test
    public void should_refresh_list_on_request_success() throws Exception
    {
        productsListSubscriber.onNext(sampleProducts);
        verify(mainViewMock).refreshProductList(sampleProducts);
    }

    @Test
    public void should_save_model_to_cache_on_request_success() throws Exception
    {
        productsListSubscriber.onNext(sampleProducts);
        verify(modelCacheMock).setModel(any(ProductsModel.class)); //TODO: ProductsModel should be refactored asap
    }

    @Test
    public void should_notify_view_if_an_error_occours() throws Exception
    {
        productsListSubscriber.onError(mock(Throwable.class));
        verify(mainViewMock).showError();
    }
}
