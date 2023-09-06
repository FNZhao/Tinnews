package com.zhaofn.tinnews;

import android.app.Application;

import androidx.room.Room;

import com.zhaofn.tinnews.database.TinNewsDatabase;

//在某些操作的时候，activity会被销毁重新创建（包括activity里面的东西），只有这个application单例不会被改变
//最外层的结构
public class TinNewsApplication extends Application {

    private static TinNewsDatabase database;//database在这设置。这个东西不会被改变，因为oncreate只会经历一次（此class是单例）

    @Override
    public void onCreate() {
        super.onCreate();
               database = Room.databaseBuilder(this, TinNewsDatabase.class, "tinnews_db").build();
    }

   public static TinNewsDatabase getDatabase() {
               return database;
           }

}
