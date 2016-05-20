package it.dindokey.testespresso.app.cache;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindonkey.testespresso.app.AppTestCase;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unused")
public class InstanceStateCacheTest extends AppTestCase
{

    private InstanceStateCache instanceStateCache;
    private ProductsModel productsModel;
    private Bundle testInstanceState;

    @Before
    public void setUp()
    {
        instanceStateCache = new InstanceStateCache();
        productsModel = new ProductsModel(sampleProducts);
        testInstanceState = new Bundle();
    }


    @Test
    public void should_save_model_to_instance_state()
    {
        instanceStateCache.setModel(productsModel);
        instanceStateCache.saveModelTo(testInstanceState);

        assertEquals(productsModel, testInstanceState.get(InstanceStateCache.MODEL));
    }

    @Test
    public void should_init_model_from_instance_state()
    {
        testInstanceState.putParcelable(InstanceStateCache.MODEL,productsModel);

        instanceStateCache.initModelFrom(testInstanceState);

        assertEquals(productsModel, instanceStateCache.model());
    }
}
