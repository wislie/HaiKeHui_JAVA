package com.haier.haikehui.base;

import com.haier.haikehui.BuildConfig;
import com.haier.rxjava3.adapter.RxJava3CallAdapterFactory;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 11:54 AM
 * desc   : 网络请求基本工具类
 * version: 1.0
 */
public abstract class BaseModel {

    public static final String BASE_URL = "http://td.api.doordu.com:9110/";


    private static Retrofit retrofit;

    private Map<String, Object> mServiceCache = new HashMap<>();

    /**
     * 请求失败重连次数
     */
    private int RETRY_COUNT = 0;

    static {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            //创建日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //添加日志拦截器
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        //添加header拦截器
        Interceptor headerInterceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request();

                Request request = originalRequest.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .build();

                return chain.proceed(request);
            }
        };
        okHttpClientBuilder.addInterceptor(headerInterceptor);

        OkHttpClient okHttpClient = okHttpClientBuilder
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

                //需要添加日志拦截器
//                .addNetworkInterceptor()
//                .cookieJar()
//                .sslSocketFactory()
                .build();


        //Retrofit创建
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    protected <T> T createService(Class<T> cls) {
        T service = (T) mServiceCache.get(cls.getCanonicalName());
        if (service == null) {
            service = retrofit.create(cls);
            mServiceCache.put(cls.getCanonicalName(), service);
        }
        return service;
    }

    protected RequestBody jsonToRequestBody(JSONObject json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
    }

    protected RequestBody stringToRequestBody(String string) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
    }


    protected RequestBody formToRequestBody(MediaType mediaType, String field) {
        return RequestBody.create(mediaType, field);
    }

    /**
     * 上传的文件构造成 MultipartBody.Part
     * @param filePath
     * @param paramFileName
     * @return
     */
    protected MultipartBody.Part fileToMultipartBody(String filePath, String paramFileName) {
        File originalFile = new File(filePath);
        RequestBody filePart = RequestBody.create(MediaType.parse(filePath), originalFile);
        MultipartBody.Part multipart = MultipartBody.Part.createFormData(paramFileName, originalFile.getName(), filePart);
        return multipart;
    }

    /**
     * 设置订阅 和 所在的线程环境
     */
    protected <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(s);
    }

    /**
     * 设置订阅 和 所在的线程环境
     */
    protected <T> void toSubscribe(Observable<T> o,Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(s);
    }


}
