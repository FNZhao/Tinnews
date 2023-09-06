package com.zhaofn.tinnews.repository;

import android.os.Handler;
import android.os.Looper;

import com.zhaofn.tinnews.model.Article;


public abstract class MyAsyncTask<Params, Progress, Result> {
    private Handler handler = new Handler(Looper.getMainLooper());

    protected abstract Result doInBackground(Params params);

    protected void onPreExecute() {

    }
    protected void publishProgress(final Progress progress) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }

    protected void onProgressUpdate(Progress progress) {

    }

    protected void onPostExecute(Result result) {

    }

    public MyAsyncTask<Params, Progress, Result> execute(final Params params) {
        onPreExecute();
        new Thread(new Runnable() {//先在ui thrad上执行，然后建一个新的thread让他在background处理，最后出了结果之后再把结果post到ui thread
            @Override
            public void run() {
                Result result = doInBackground(params);
                handler.post(new Runnable() {//用handler，将runnable class post到ui thread（background的任务post到ui thread），ui thread会执行下面的run
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });
            }
        }).start();
        return this;
    }

    public static void execute(Runnable runnable) {
        new MyAsyncTask() {
            @Override
            protected Object doInBackground(Object o) {
                runnable.run();
                return null;
            }
        };
    }


    public void main() {
        new MyFavoriteAsyncTask().execute(new Article());
        MyAsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public static class MyFavoriteAsyncTask extends MyAsyncTask<Article, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Article article) {
            return null;
        }
    }
}
