package it.dindonkey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import it.dindokey.testespresso.app.cache.ObservableCache;
import it.dindokey.testespresso.app.rx.SchedulerManager;
import it.dindokey.testespresso.app.api.HttpClient;
import it.dindokey.testespresso.app.api.SimpleProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import it.dindonkey.testespresso.app.AppTestCase;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by simone on 2/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends AppTestCase
{
    @Mock
    HttpClient httpClientMock;
    @Mock
    MainView mockedMainView;
    @Mock
    Bundle savedInstanceStateMock;

    private MainPresenter presenter;
    private List<String> sampleProducts;
    private ModelViewHolder modelViewHolderMock;
    private SchedulerManager schedulerManager;

    @Before
    public void setup() throws Exception
    {
        schedulerManager = new SchedulerManager(Schedulers.immediate(),
                Schedulers.immediate());
        presenter = new MainPresenter(new SimpleProductsApiService(httpClientMock),
                schedulerManager, new ObservableCache());

        sampleProducts = Arrays.asList("test product");
        modelViewHolderMock = new ModelViewHolder(mockedMainView, savedInstanceStateMock);
        when(httpClientMock.get()).thenReturn(sampleProducts);
    }

    @Test
    public void load_products_on_resume() throws Exception
    {
        presenter.resume(modelViewHolderMock);
        verify(httpClientMock).get();
        verify(mockedMainView).refreshProductList(sampleProducts);
    }

    @Test
    public void retain_model_after_first_load() throws Exception
    {
        presenter.resume(modelViewHolderMock);
        presenter.resume(modelViewHolderMock);
        verify(httpClientMock, times(1)).get();
    }

    @Test
    public void load_model_from_saved_instance_state_and_update_view() throws Exception
    {
        putTestModelIntoInstanceState();

        ModelViewHolder modelViewHolderMock = new ModelViewHolder(mockedMainView,
                savedInstanceStateMock);
        presenter.resume(modelViewHolderMock);

        verifyNoMoreInteractions(httpClientMock);
        verify(mockedMainView).refreshProductList(sampleProducts);
    }

    @Test
    public void call_show_loading_while_fetching_data() throws Exception
    {
        presenter.resume(modelViewHolderMock);
        verify(mockedMainView).showLoading();
    }

    @Test
    public void call_show_error_if_occours() throws Exception
    {
        when(httpClientMock.get()).thenThrow(new RuntimeException());
        presenter.resume(modelViewHolderMock);
        verify(mockedMainView).showError();

    }

    @Test
    public void refresh_data() throws Exception
    {
        //e.g. a previous request was done and was completed, UI triggers refresh data, we need to reload data
        presenter.resume(modelViewHolderMock); // first request

        MainView anotheMainViewMock = mock(MainView.class);
        modelViewHolderMock.setView(anotheMainViewMock);

        List<String> freshData = Arrays.asList("fresh data");
        when(httpClientMock.get()).thenReturn(freshData);

        presenter.loadData(); //e.g. reload button or pull to refresh

        verify(anotheMainViewMock).refreshProductList(freshData);
    }


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

    private void putTestModelIntoInstanceState()
    {
        ProductsModel model = new ProductsModel();
        model.setItems(sampleProducts);
        when(savedInstanceStateMock.getParcelable(anyString())).thenReturn(model);
    }

}
