package it.dindokey.testespresso.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import javax.inject.Inject;

import it.dindokey.testespresso.app.App;
import it.dindokey.testespresso.app.DaggerTestAppComponent;
import it.dindokey.testespresso.app.TestAppComponent;
import it.dindokey.testespresso.app.TestAppModule;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindonkey.testespresso.app.AppTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends AppTestCase
{
    @Inject
    ProductsApiService mockedProductsApiService;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class,
            true,
            false);

    @Before
    public void setup()
    {
        //TODO: use a dagger rule
        TestAppComponent testAppComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule())
                .build();

        App application = (App) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.setComponent(testAppComponent);
        testAppComponent.inject(this);

        when(mockedProductsApiService.getProducts()).thenReturn(testProductsObservable());
    }

    @Test
    public void listGoesOverTheFold()
    {
        mActivityRule.launchActivity(new Intent());

        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void show_product_list()
    {
        when(mockedProductsApiService.getProducts()).thenReturn(testProductsObservable());

        mActivityRule.launchActivity(new Intent());

        verify(mockedProductsApiService).getProducts();
        onView(withText("test product")).check(matches(isDisplayed()));
    }

    @Test
    public void retain_products_on_rotation()
    {
        when(mockedProductsApiService.getProducts()).thenReturn(testProductsObservable());

        mActivityRule.launchActivity(new Intent());
        onView(withText("test product")).check(matches(isDisplayed()));
        rotateScreen();
        onView(withText("test product")).check(matches(isDisplayed()));

        verify(mockedProductsApiService, times(1)).getProducts();
    }

    @Test
    public void refresh_product_list()
    {
        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivityRule.getActivity().refreshProductList(Arrays.asList("test product"));
            }
        });

        onView(withText("test product")).check(matches(isDisplayed()));
    }

    @Test
    public void show_loading_message()
    {
        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivityRule.getActivity().showLoading();
            }

        });

        onView(withText("loading")).check(matches(isDisplayed()));
    }

    @Test
    public void show_load_error_message()
    {
        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivityRule.getActivity().showError();
            }

        });

        onView(withText("error")).check(matches(isDisplayed()));
    }

    @Test
    public void show_load_error_if_occours()
    {
        when(mockedProductsApiService.getProducts()).thenReturn(brokenProductsObservable());
        mActivityRule.launchActivity(new Intent());
        onView(withText("error")).check(matches(isDisplayed()));
    }

    private void rotateScreen()
    {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation
                = context.getResources().getConfiguration().orientation;

        Activity activity = mActivityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @After
    public void tearDown()
    {
        mActivityRule.getActivity().finish();
    }
}
