package com.admin.app0612;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/21.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static String mDBname = "user";
    private static int mVersion = 1;
    public static final String TABLENAME = "usertb";

    public DBOpenHelper(Context context) {
        super(context, mDBname, null, mVersion);
    }


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // 第一个参数是应用的上下文
        // 第二个参数是应用的数据库名字
        // 第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        // 第四个参数是数据库版本，必须是大于0的int（即非负数）
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLENAME + " ("
                + "_id INTEGER PRIMARY KEY autoincrement," + "username text not null,"
                + "pin text not null" + ");");
        //Log.i("info", "Create Table ok");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。
        //一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失,从版本几更新到版本几
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }
}
