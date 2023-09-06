package com.zhaofn.tinnews.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zhaofn.tinnews.TinNewsApplication;
import com.zhaofn.tinnews.database.TinNewsDatabase;
import com.zhaofn.tinnews.model.Article;
import com.zhaofn.tinnews.model.NewsResponse;
import com.zhaofn.tinnews.network.NewsApi;
import com.zhaofn.tinnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    private final TinNewsDatabase database;

    public NewsRepository() {//constructor,这样在使用newsrepostitory的时候就把newsapi实例了
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {//建立了newsrepository和newsapi的联系
           //react里js可以直接反应state，但是java里不行。把newsresponse包裹在livedata里，而livedata可以被ui observe，当内容变化了，就可以更新
           //对应architecture里的viewmodel里面的livedata，这里面的每一个数据结构都可以被顶层的activity/fragment obseve
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();//mutable才可以set，所以用mutable
        newsApi.getTopHeadlines(country)//call的gettopheadlines api，是newsapi里的
                .enqueue(new Callback<NewsResponse>() {//enqueue是async的，在其他线程执行，call了之后瞬间跳到下面的retur。spring是multithread，可以同时处理很多request
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;//因为是异步的，所以第一次是空的，但是由于是livedata，函数在另一线程成功之后会set data，然后被ui捕捉到变化，livedata触发更新ui
    }

    public LiveData<NewsResponse> searchNews(String query) {//思路和上一个方法一样
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)//call的是everything api
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article) {//使用下面的database function
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);//这里的excute里面放的是param
        return resultLiveData;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

    //是一个static nested class，所以放在class里面
    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> { //用AsyncTask去开一个新线程执行database操作
                                                                                       //最后面尖括号内定义了三个值，param progress和result，比如下面的doInBackground里面就要放param，execute里也是，我们这里没用到progress所以设定为void

        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }

        @Override
        protected Boolean doInBackground(Article... articles) {//boolean是上面定义的result，其他function同理
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }


}
