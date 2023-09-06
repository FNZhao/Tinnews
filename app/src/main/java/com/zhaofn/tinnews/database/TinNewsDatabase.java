package com.zhaofn.tinnews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zhaofn.tinnews.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false) //room setup的最下面的部分
public abstract class TinNewsDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}

//database是跟着app走的，是app level的，所以要在app上进行设置（activity是一个入口，提供了一个screen，一个app里可以有很多activity。而TinNewsApplication是本项目的single term，只有一个。）
//在mvvm中，在model里访问，操作data
//在这里，我们的model是newsrepository
