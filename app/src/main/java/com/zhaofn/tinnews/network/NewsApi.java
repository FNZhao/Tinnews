package com.zhaofn.tinnews.network;

import com.zhaofn.tinnews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//这里写newsapi网站的两个api，一个是everything，一个是top headline，对应网站上的两个endpoint
//newsapi定义了一个框架，声明了两方的contract，下面的就是怎么做这个call，是做call的规则
public interface NewsApi {//下面是两个function，调用这两个就可以实现networking，我们不去实现这个interface，retrofit会去实现它

    //eg. https://newsapi.org/v2/top-headlines?country=us
    @GET("top-headlines")//网站上就用的get
    Call<NewsResponse> getTopHeadlines(@Query("country") String country); //return的是一个叫call(retrofit提供的与networking交流的方式)的东西，里面包裹一个newsresponse
                                                                          //传入的就是query，比如us，business等
                                                                          //@query里面写country，就是对应query里的key，而我们的string country就是他的value

    //eg. https://newsapi.org/v2/everything?q=tesla&PageSize=3
    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize);//这里我们传了两个query key，一个是q，一个是pagesize

    //这两个所需要的api key是在client文件里传进来的，而返回的newsresponse是个json，他与Java的互转也是client里实现
}
