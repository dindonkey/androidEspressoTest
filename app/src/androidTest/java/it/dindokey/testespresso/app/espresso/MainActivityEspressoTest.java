package it.dindokey.testespresso.app.espresso;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import it.dindokey.testespresso.app.MainActivity;
import it.dindokey.testespresso.app.MyApplication;
import it.dindokey.testespresso.app.R;
import it.dindokey.testespresso.app.api.ProductsApi;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by simone on 2/3/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest
{
    @Mock
    ProductsApi mockedProductsApi;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class, true, false);

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listGoesOverTheFold() {
        mActivityRule.launchActivity(new Intent());

        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void espressoTestIsSyncWithAsyncTask() throws Exception
    {
        mActivityRule.launchActivity(new Intent());

        // first onView waits for async task termination
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onView(withText("Loading...")).check(doesNotExist());
        onView(withText("First product")).check(matches(isDisplayed()));
        onData(allOf(is("First product"))).check(matches(isDisplayed()));
    }

    @Test
    public void getProducts() throws Exception
    {
        getApplication().setProductsApi(mockedProductsApi);
        when(mockedProductsApi.getProducts()).thenReturn(new String[]{"test product"});

        mActivityRule.launchActivity(new Intent());

        verify(mockedProductsApi).getProducts();
        onView(withText("test product")).check(matches(isDisplayed()));
    }

    private MyApplication getApplication()
    {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (MyApplication)instrumentation.getTargetContext().getApplicationContext();
    }
}
