package it.dindonkey.testespresso.app.rx;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.cache.ObservableCache;
import it.dindokey.testespresso.app.rx.CacheObservableExecutor;
import it.dindokey.testespresso.app.rx.SchedulerManager;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

/**
 * Created by simone on 5/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CacheObservableExecutorTest
{
    @Mock
    ObservableCache observableCacheMock;

    private CacheObservableExecutor cacheObservableExecutor;

    @Before
    public void setUp() throws Exception
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),Schedulers.immediate());
        cacheObservableExecutor = new CacheObservableExecutor(observableCacheMock, schedulerManager);
    }

    @Ignore
    @Test
    public void should_() throws Exception
    {
        Observable testObservable = Observable.empty();
        TestSubscriber testSubscriber = new TestSubscriber();

        cacheObservableExecutor.execute(testObservable, testSubscriber);
//        verify(testObservable).connect
        verify(testObservable).subscribe(testSubscriber);
    }
}
