package com.zhaofn.tinnews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.zhaofn.tinnews.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void saveArticle(Article article);//存

    @Query("SELECT * FROM article")//这里用androidx的query
    LiveData<List<Article>> getAllArticles();//livedata本身就会做一个multithread

    @Delete
    void deleteArticle(Article article);//删

}
