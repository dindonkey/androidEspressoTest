package it.dindokey.testespresso.app.view;

import java.util.List;

public interface MainView
{
    void refreshProductList(List<String> products);

    void showLoading();

    void showError();
}
