package com.zhaofn.tinnews.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//这个class可以实现api，定义了做networking call所需的information，有header了就可以做call了
public class RetrofitClient {

    // TODO: Assign your API_KEY here
    private static final String API_KEY = "be1d7ed863164f95bce68ae677f06cbc";//用于authentication
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        return new Retrofit.Builder()//retrofit的创建方式是builder
                .baseUrl(BASE_URL)//这个是指 只要我用retrofit client去做networking call，使用的baseurl都是他
                .addConverterFactory(GsonConverterFactory.create())//json <--> javaclass
                .client(okHttpClient)//这个是真实帮我们做networking call的，所以也要传进来
                .build();//build结束
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {//chain可以理解为往sever发送，所以request可以从这里拿到
            Request original = chain.request();
            Request request = original //在这里我们给他加新的东西（不用去理解为什么要这样）
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)//intercept的时候往header里加入这个内容
                    .build();
            return chain.proceed(request);
        }
    }
}
