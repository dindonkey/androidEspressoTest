package it.dindonkey.testespresso.app.rx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.dindokey.testespresso.app.cache.ObservableCache;
import it.dindokey.testespresso.app.rx.CacheObservableExecutor;
import it.dindokey.testespresso.app.rx.SchedulerManager;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
public class CacheObservableExecutorTest
{
    @Mock
    ObservableCache observableCacheMock;
    @Mock
    FakeTask fakeTaskMock;

    private CacheObservableExecutor cacheObservableExecutor;
    private TestSubscriber testSubscriber;
    private Observable testObservable;

    @Before
    public void setUp()
    {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(),
                Schedulers.immediate());
        cacheObservableExecutor = new CacheObservableExecutor(observableCacheMock,
                schedulerManager);
        testObservable = createTestObservable();
        testSubscriber = new TestSubscriber();
    }

    @Test
    public void should_execute_observable_call()
    {
        cacheObservableExecutor.execute(testObservable, testSubscriber);
        verify(fakeTaskMock).doSomething();
    }

    @Test
    public void should_not_execute_observer_call_if_cached()
    {
        when(observableCacheMock.observable()).thenReturn(testObservable.replay());
        cacheObservableExecutor.execute(testObservable, testSubscriber);
        verifyNoMoreInteractions(fakeTaskMock);
    }

    @Test
    public void should_unsubscribe_observer()
    {
        cacheObservableExecutor.execute(testObservable, testSubscriber);
        cacheObservableExecutor.unsubscribe();

        testSubscriber.assertUnsubscribed();
    }

    private Observable createTestObservable()
    {
        return Observable.create(new Observable.OnSubscribe<Void>()
        {
            @Override
            public void call(Subscriber<? super Void> subscriber)
            {
                fakeTaskMock.doSomething();
            }
        });
    }

    class FakeTask
    {
        public void doSomething()
        {
            System.out.println("duh");
        }
    }

}
