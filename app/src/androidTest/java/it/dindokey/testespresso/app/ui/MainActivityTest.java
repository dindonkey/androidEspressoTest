package it.dindokey.testespresso.app.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.dindokey.testespresso.app.api.ProductsApi;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by simone on 2/3/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest
{
    //TODO: get from dagger with dagger rule
    ProductsApi mockedProductsApi;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class, true, false);

    @Before
    public void setup()
    {
       //TODO: get from dagger with dagger rule
       mockedProductsApi = mock(ProductsApi.class);
    }

    @Test
    public void listGoesOverTheFold() {
        mActivityRule.launchActivity(new Intent());

        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void show_product_list() throws Exception
    {
        when(mockedProductsApi.getProducts()).thenReturn(new String[]{"test product"});

        mActivityRule.launchActivity(new Intent());

        verify(mockedProductsApi).getProducts();
        onView(withText("test product")).check(matches(isDisplayed()));
    }

//    @Ignore
//    @Test
//    public void espressoTestIsSyncWithAsyncTask() throws Exception
//    {
//        mActivityRule.launchActivity(new Intent());
//
//        // first onView waits for async task termination
//        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
//        onView(withText("Loading...")).check(doesNotExist());
//        onView(withText("First product")).check(matches(isDisplayed()));
//        onData(allOf(is("First product"))).check(matches(isDisplayed()));
//    }

}
