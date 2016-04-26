package it.dindokey.testespresso.app.view;

import java.util.List;

/**
 * Created by simone on 2/24/16.
 */
public interface MainView
{
    void refreshProductList(List<String> products);

    void showLoading();

    void showError();
}
