package com.iww.classifiedolx.api;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import com.iww.classifiedolx.api.interfaces.AppComponent;
import com.iww.classifiedolx.api.interfaces.DaggerAppComponent;


public class AppController extends Application {
    private static AppController instance;
  //  private static SharedPreferences spScrapperVender;
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

  //  spScrapperVender = getSharedPreferences("spScrapperVender", MODE_PRIVATE);
   }
    public AppComponent getComponent() {
        return component;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
   /*  public static SharedPreferences getSharedPref() {
    //    return spScrapperVender;
    }*/
    public static synchronized AppController getInstance() {
        return instance;
    }
}
