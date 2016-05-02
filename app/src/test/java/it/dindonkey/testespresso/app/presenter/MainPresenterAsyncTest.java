package it.dindonkey.testespresso.app.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.dindokey.testespresso.app.ModelViewHolder;
import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

/**
 * Created by simone on 4/28/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterAsyncTest
{
    private MainPresenter presenter;

    @Mock
    ProductsApiService mockedProductsApiService;

    @Mock
    MainView mockedMainView;

    @Mock
    Bundle savedInstanceStateMock;

    private ModelViewHolder modelViewHolderMock;

    private List<String> sampleProducts;
    private TestScheduler _God_scheduler;

    @Before
    public void setUp() throws Exception
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),
                Schedulers.immediate());
        presenter = new MainPresenter(mockedProductsApiService, schedulerManager);
        sampleProducts = Arrays.asList("test product");
        modelViewHolderMock = new ModelViewHolder(mockedMainView, savedInstanceStateMock);
        _God_scheduler = new TestScheduler();
    }

    @Ignore
    @Test
    public void slow_test_just_for_case_study() throws Exception
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        when(mockedProductsApiService.getProducts()).thenReturn(delayedProductsObservable());

        presenter.resume(modelViewHolderMock);
        executor.awaitTermination(4, TimeUnit.SECONDS);

        verify(mockedMainView).refreshProductList(sampleProducts);
    }

    @Test
    public void fast_test_just_for_case_study() throws Exception
    {
        int secondsToComplete = 4;
        when(mockedProductsApiService.getProducts()).thenReturn(timeControlledProductsObservable(
                secondsToComplete));

        presenter.resume(modelViewHolderMock);
        _God_scheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        verify(mockedMainView).refreshProductList(sampleProducts);
    }

    @Test
    public void do_not_start_new_request_if_one_is_aready_running() throws Exception
    {
        //e.g. in case we have two resume while fetching from network takes time
        int secondsToComplete = 10;
        when(mockedProductsApiService.getProducts()).thenReturn(timeControlledProductsObservable(
                secondsToComplete));

        presenter.resume(modelViewHolderMock);
        _God_scheduler.advanceTimeBy(1,
                TimeUnit.SECONDS); // advance just for 1 second, the request is in progress
        presenter.resume(modelViewHolderMock);
        _God_scheduler.advanceTimeBy(secondsToComplete, TimeUnit.SECONDS);

        verify(mockedProductsApiService, times(1)).getProducts();
    }

    @Test
    public void resume_request_if_presenter_is_recreated_while_a_previous_request_was_already_running() throws Exception
    {
        //e.g. we want to resume the request if presenter is destroyed and re-created (activity rotation)
        fail("TBD");

    }

    @NonNull
    private Observable<List<String>> delayedProductsObservable()
    {
        return just(sampleProducts).delay(4,
                TimeUnit.SECONDS);
    }

    @NonNull
    private Observable<List<String>> timeControlledProductsObservable(int secondsToComplete)
    {
        return just(sampleProducts).delay(secondsToComplete,
                TimeUnit.SECONDS, _God_scheduler);
    }


}
