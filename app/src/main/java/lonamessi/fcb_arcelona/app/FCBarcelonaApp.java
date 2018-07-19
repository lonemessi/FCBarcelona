package lonamessi.fcb_arcelona.app;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import lonamessi.fcb_arcelona.di.component.AppComponent;
import lonamessi.fcb_arcelona.di.component.DaggerAppComponent;
import lonamessi.fcb_arcelona.di.module.AppModule;
import lonamessi.fcb_arcelona.di.module.HttpModule;

/**
 * Created by gyp on 2018/7/19.
 */
public class FCBarcelonaApp  extends Application {
    private static FCBarcelonaApp instace;
    private static volatile AppComponent appComponent;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .appModule(new AppModule(instace))
                .httpModule(new HttpModule())
                .build()
                .inject(this);

        instace=this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        refWatcher = LeakCanary.install(this);

    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


    public static RefWatcher getRefWatcher(Context context) {
        FCBarcelonaApp application = (FCBarcelonaApp) context.getApplicationContext();
        return application.refWatcher;
    }

    public  static synchronized  AppComponent getAppComponent(){
        if (appComponent==null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instace))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;

    }

    public static synchronized FCBarcelonaApp getInstance(){
        return instace;
    }
}
