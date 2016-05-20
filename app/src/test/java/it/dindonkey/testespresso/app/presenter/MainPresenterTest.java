package it.dindonkey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.cache.ModelCache;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.rx.ObservableExecutor;
import it.dindokey.testespresso.app.view.MainView;
import it.dindonkey.testespresso.app.AppTestCase;
import rx.Observer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends AppTestCase
{
    @Mock
    MainView mainViewMock;
    @Mock
    Bundle savedInstanceStateMock;
    @Mock
    ModelCache modelCacheMock;
    @Mock
    ObservableExecutor observableExecutorMock;
    @Mock
    ProductsApiService productsApiServiceMock;

    private MainPresenter presenter;

    @Before
    public void setup()
    {
        presenter = new MainPresenter(productsApiServiceMock,
                observableExecutorMock, modelCacheMock);
    }

    @Test
    public void should_execute_get_products_and_show_loading_on_resume()
    {
        presenter.resume(mainViewMock);

        verify(observableExecutorMock).execute(eq(productsApiServiceMock.getProducts()),
                any(Observer.class));
        verify(mainViewMock).showLoading();
    }

    @Test
    public void should_not_execute_get_products_and_refresh_products_list_if_model_is_cached()
    {
        ProductsModel model = new ProductsModel();
        model.setItems(sampleProducts);
        when(modelCacheMock.model()).thenReturn(model);

        presenter.resume(mainViewMock);

        verify(mainViewMock).refreshProductList(sampleProducts);
        verifyZeroInteractions(observableExecutorMock);
    }

    @Test
    public void should_unsubscribe_subcription_on_pause()
    {
        presenter.pause();
        verify(observableExecutorMock).unsubscribe();
    }

    //    @Test
//    public void refresh_data() throws Exception
//    {
//        //e.g. a previous request was done and was completed, UI triggers refresh data, we need to reload data
//        presenter.resume(modelViewHolderMock); // first request
//
//        MainView anotheMainViewMock = mock(MainView.class);
//        modelViewHolderMock.setView(anotheMainViewMock);
//
//        List<String> freshData = Arrays.asList("fresh data");
//        when(httpClientMock.get()).thenReturn(freshData);
//
//        presenter.loadData(view); //e.g. reload button or pull to refresh
//
//        verify(anotheMainViewMock).refreshProductList(freshData);
//    }


//    @Test
//    public void unsubscribe_observer_on_presenter_pause() throws Exception
//    {
//        //e.g. unsubscription is necessary to release references and perform a good GC
//        Subscription subscriptionMock = mock(Subscription.class);
//        TestSubscriber testSubscriber = new TestSubscriber();
//
//        ObservableCache observableCache = new ObservableCache();
//        observableCache.store(testSubscriber);
//        presenter = new MainPresenter(new SimpleProductsApiService(httpClientMock), schedulerManager, observableCache);
//
//        presenter.resume(modelViewHolderMock);
//
//        presenter.pause();
//
//        testSubscriber.assertUnsubscribed();
//    }

}
