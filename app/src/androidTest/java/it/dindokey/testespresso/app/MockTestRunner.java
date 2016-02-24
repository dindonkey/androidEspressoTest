package it.dindokey.testespresso.app;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by simone on 2/23/16.
 */
public class MockTestRunner extends AndroidJUnitRunner
{
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return super.newApplication(cl, MyApplicationTest.class.getName(), context);
    }
}
