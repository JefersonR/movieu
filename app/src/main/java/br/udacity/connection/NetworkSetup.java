package br.udacity.connection;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class NetworkSetup {

    private static final String contentType = "Content-Type";
    private static final String appJson     = "application/json";
    private static final String api_key     = "api_key";
    private static final String key         = "[add a key aqui]";
    private static final int maxRequest     = 20;
    private static final int timeOut     = 60;

    public static OkHttpClient getClient() {
        return new OkHttpClient()
                .newBuilder()
                .dispatcher(buildDispatcher())
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestBuilder.header(contentType, appJson);
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter(api_key, key)
                                .build();
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(getLoggingCapableHttpClient())
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build();
    }

    private static Dispatcher buildDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(maxRequest);
        return dispatcher;
    }

    private static HttpLoggingInterceptor getLoggingCapableHttpClient() {
        HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
        mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return mLogging;
    }

}
