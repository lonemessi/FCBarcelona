package lonamessi.fcb_arcelona.di.component;

import javax.inject.Singleton;

import dagger.Component;
import lonamessi.fcb_arcelona.app.FCBarcelonaApp;
import lonamessi.fcb_arcelona.di.module.AppModule;
import lonamessi.fcb_arcelona.di.module.HttpModule;

/**
 * Created by gyp on 2018/7/19.
 */
@Singleton
@Component(modules = {HttpModule.class,AppModule.class})
public interface AppComponent {

    /**
     * 注入FCBarcelonaApp 实例
     * @param fcBarcelonaApp
     */
    void inject(FCBarcelonaApp fcBarcelonaApp);
    /**
     * 提供App的Context
     *
     */
    FCBarcelonaApp getContext();
}
