package lonamessi.fcb_arcelona.di.module;

import android.os.Debug;
import android.util.TimeUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lonamessi.fcb_arcelona.BuildConfig;
import lonamessi.fcb_arcelona.app.Constants;
import lonamessi.fcb_arcelona.core.http.api.GeeksApis;
import lonamessi.fcb_arcelona.core.http.cookies.CookiesManager;
import lonamessi.fcb_arcelona.di.qualifier.FCBarcelona;
import lonamessi.fcb_arcelona.utils.CommonUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gyp on 2018/7/19.
 */
@Module
public class HttpModule {

    //提供GeeksApis实例
    @Singleton
    @Provides
    GeeksApis provideGeeksApi(@FCBarcelona Retrofit retrofit){
      return retrofit.create(GeeksApis.class);
    }
    //提供Retrofit
    @Singleton
    @Provides
    @FCBarcelona
    Retrofit provideGeeksRetrodit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,GeeksApis.HOST);
    }

    //提供Retrofit.Builder
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }
    //提供OkHttpClient.Builder
    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder(){
        return new OkHttpClient.Builder();
    }

    //提供OkHttpClient
    OkHttpClient provideClient(OkHttpClient.Builder builder){

        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if(!CommonUtils.isNetworkConnected()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtils.isNetworkConnected()){
                int maxAge=0;
                //有网络时，不缓存，最大保存时长为0；
                response.newBuilder()
                        .header("Cache-Control","public,max-age="+maxAge)
                        .removeHeader("Pragma")
                        .build();
            }else {
            //无网络，设置超时为4周
                int maxStale = 60*60*24*28;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.readTimeout(20,TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //cookie认证
        builder.cookieJar(new CookiesManager());
        return builder.build();

    }


    //创建Retrofit
    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client,String url){
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
