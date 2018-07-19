package lonamessi.fcb_arcelona.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lonamessi.fcb_arcelona.app.FCBarcelonaApp;
import lonamessi.fcb_arcelona.core.http.HttpHelper;
import lonamessi.fcb_arcelona.core.http.HttpHelperImpl;

/**
 * Created by gyp on 2018/7/19.
 */
@Module
public class AppModule {

    private final FCBarcelonaApp application;

    public AppModule(FCBarcelonaApp application) {
        this.application = application;
    }


    @Provides
    @Singleton
    FCBarcelonaApp provideApplicationContext(){
        return application;
    }
    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelperImpl) {
        return httpHelperImpl;
    }

}
