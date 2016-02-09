package it.dindokey.testespresso.app.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import it.dindokey.testespresso.app.MainActivity;
import it.dindokey.testespresso.app.R;
import it.dindokey.testespresso.app.api.ProductsApi;
import org.junit.Before;
import org.junit.Ignore;
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

/**
 * Created by simone on 2/3/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest
{
    @Mock
    ProductsApi mockedProductsApi;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listGoesOverTheFold() {
        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void espressoTestIsSyncWithAsyncTask() throws Exception
    {
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onView(withText("Caricamento...")).check(doesNotExist()); // il test viene eseguito alla fine dell'async
        onView(withText("Primo prodotto")).check(matches(isDisplayed()));
        onData(allOf(is("Primo prodotto"))).check(matches(isDisplayed()));
    }

    @Test
    public void getProducts() throws Exception
    {
        onView(withText("Primo prodotto")).check(matches(isDisplayed()));

        verify(mockedProductsApi).getProducts();
    }
}
