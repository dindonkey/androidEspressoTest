package it.dindokey.testespresso.app.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
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
}
