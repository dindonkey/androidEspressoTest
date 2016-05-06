package it.dindokey.testespresso.app;

import android.os.Bundle;

import org.junit.Test;

import java.util.Arrays;

import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.view.MainView;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by simone on 4/15/16.
 */
public class ModelViewHolderTest
{
    @Test
    public void save_model_to_instance_state() throws Exception
    {
        ProductsModel testModel = new ProductsModel();
        testModel.setItems(Arrays.asList("test product"));

        Bundle savedInstanceState = new Bundle();
        ModelViewHolder modelViewHolder = new ModelViewHolder(mock(MainView.class),savedInstanceState);

        modelViewHolder.setModel(testModel);
        modelViewHolder.saveInstanceState(savedInstanceState);

        ProductsModel expected = savedInstanceState.getParcelable(ModelViewHolder.MODEL);
        assertEquals(expected, testModel);
    }


}
