package it.dindokey.testespresso.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import it.dindokey.testespresso.app.App;
import it.dindokey.testespresso.app.DaggerTestAppComponent;
import it.dindokey.testespresso.app.TestAppComponent;
import it.dindokey.testespresso.app.TestAppModule;
import it.dindokey.testespresso.app.api.ProductsApiService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by simone on 2/3/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest
{
    @Inject ProductsApiService mockedProductsApiService;

    @Rule public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class,
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
    }

    @Test
    public void listGoesOverTheFold()
    {
        mActivityRule.launchActivity(new Intent());

        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void show_product_list() throws Exception
    {
        when(mockedProductsApiService.getProducts()).thenReturn(new String[]{"test product"});

        mActivityRule.launchActivity(new Intent());

        verify(mockedProductsApiService).getProducts();
        onView(withText("test product")).check(matches(isDisplayed()));
    }

    @Test
    public void retain_products_on_rotation() throws Exception
    {
        when(mockedProductsApiService.getProducts()).thenReturn(new String[]{"test product"});

        mActivityRule.launchActivity(new Intent());
        onView(withText("test product")).check(matches(isDisplayed()));
        rotateScreen();
        onView(withText("test product")).check(matches(isDisplayed()));

        verify(mockedProductsApiService, times(1)).getProducts();
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation
                = context.getResources().getConfiguration().orientation;

        Activity activity = mActivityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void refresh_product_list() throws Exception
    {
        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivityRule.getActivity().refreshProductList(new String[]{"test product"});
            }
        });

        onView(withText("test product")).check(matches(isDisplayed()));
    }

    @Test
    public void show_loading_message() throws Exception
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
    public void show_load_error_message() throws Exception
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
    public void show_load_error_if_occours() throws Exception
    {
        when(mockedProductsApiService.getProducts()).thenThrow(new RuntimeException());
        mActivityRule.launchActivity(new Intent());
        onView(withText("error")).check(matches(isDisplayed()));
    }
}
