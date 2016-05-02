package it.dindonkey.testespresso.app.presenter;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import it.dindokey.testespresso.app.ModelViewHolder;
import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

/**
 * Created by simone on 2/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest
{
    @Mock
    ProductsApiService mockedProductsApiService;

    @Mock MainView mockedMainView;

    @Mock Bundle savedInstanceStateMock;

    private MainPresenter presenter;
    private List<String> sampleProducts;
    private ModelViewHolder modelViewHolderMock;

    @Before
    public void setup()
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApiService, schedulerManager);

        sampleProducts = Arrays.asList("test product");
        when(mockedProductsApiService.getProducts()).thenReturn(just(sampleProducts));
        modelViewHolderMock = new ModelViewHolder(mockedMainView,savedInstanceStateMock);
    }

    @Test
    public void load_products_on_resume() throws Exception
    {
        presenter.resume(modelViewHolderMock);
        verify(mockedProductsApiService).getProducts();
        verify(mockedMainView).refreshProductList(sampleProducts);
    }

    @Test
    public void retain_model_after_first_load() throws Exception
    {
        presenter.resume(modelViewHolderMock);
        presenter.resume(modelViewHolderMock);
        verify(mockedProductsApiService, times(1)).getProducts();
    }

    @Test
    public void load_model_from_saved_instance_state_and_update_view() throws Exception
    {
        putTestModelToInstanceState();

        ModelViewHolder modelViewHolderMock = new ModelViewHolder(mockedMainView,savedInstanceStateMock);
        presenter.resume(modelViewHolderMock);

        verifyNoMoreInteractions(mockedProductsApiService);
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
        when(mockedProductsApiService.getProducts()).thenReturn(brokenProductsObservable());
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
        when(mockedProductsApiService.getProducts()).thenReturn(just(freshData));

        presenter.loadData(modelViewHolderMock); //e.g. reload button or pull to refresh

        verify(anotheMainViewMock).refreshProductList(freshData);
    }


    @Test
    public void unsubscribe_observer_on_presenter_pause() throws Exception
    {
        //e.g. unsubscription is necessary to release references and perform a good GC
        Subscription subscriptionMock = mock(Subscription.class);
        when(mockedProductsApiService.getProducts()).thenReturn(observableWithSubscription(subscriptionMock));
        presenter.resume(modelViewHolderMock);
        presenter.pause();

        verify(subscriptionMock).unsubscribe();
    }

    private void putTestModelToInstanceState()
    {
        ProductsModel model = new ProductsModel();
        model.setItems(sampleProducts);
        when(savedInstanceStateMock.getParcelable(anyString())).thenReturn(model);
    }

    private Observable<List<String>> brokenProductsObservable()
    {
        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                subscriber.onError(new RuntimeException());
            }
        });
    }

    private Observable<List<String>> observableWithSubscription(final Subscription subscription)
    {
        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                subscriber.add(subscription);
            }
        });
    }
}
