package net.betterbing.kotlincoroutinehelloworld.feature.network;

import android.util.Log;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory;
import net.betterbing.kotlincoroutinehelloworld.feature.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RetrofitHelper {

    private static final int DEFAULT_TIME_OUT_SECONDS = 10;
    private static final String TAG = "kotlin_coroutines_mvvm_room_constraintlayout";
    /**
     * header map
     */
    private static HashMap<String, String> mHeaderMap = new HashMap<>();

    public static void addHeader(Map<String, String> headerMap) {
        mHeaderMap.putAll(headerMap);
    }

    public static void removeHeader(String name) {
        mHeaderMap.remove(name);
    }

    public static void addHeader(String name, String value) {
        mHeaderMap.put(name, value);
    }

    public static Retrofit getRetrofit(String baseUrl, int timeout) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new MyHttpRequestInterceptor());
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS);
        httpClient.readTimeout(timeout, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLogger());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public static <T> T create(String baseUrl, Class<T> clazz) {
        return getRetrofit(baseUrl, DEFAULT_TIME_OUT_SECONDS).create(clazz);
    }

    public static <T> T create(String baseUrl, int timeout, Class<T> clazz) {
        return getRetrofit(baseUrl, timeout).create(clazz);
    }

    public static HashMap<String, String> getHeaderMap() {
        return mHeaderMap;
    }

    private static class MyLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            if (message != null && BuildConfig.DEBUG) {
                if (message.startsWith("{") || message.startsWith("[")) {
                    Log.e(TAG, message);
                } else {
                    Log.d(TAG, message);
                }
            }
        }
    }

    private static class MyHttpRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder build = original.newBuilder();
            addHeader("Accept-Language", Locale.getDefault().getLanguage());
            for (Map.Entry<String, String> entry : mHeaderMap.entrySet()) {
                Log.v(TAG, entry.getValue());
                if (entry.getKey() != null && entry.getValue() != null) {
                    build.header(entry.getKey(), entry.getValue());
                }
            }
            build.method(original.method(), original.body());
            return chain.proceed(build.build());
        }
    }

}
