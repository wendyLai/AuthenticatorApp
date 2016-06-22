package com.admin.app0612;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class UserDBManager {

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;
    private String TBNAME = DBOpenHelper.TABLENAME;

    public UserDBManager(Context context) {
        // this.context = context;   
        dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
    }

    /**
     * 添加数据
     */
    public void add(UserEntity user) {
        //开启事务
        db.beginTransaction();
        try {
            db.execSQL("insert into " + TBNAME + "(username,pin) values(?,?)", new Object[]{user.getusername(), user.getPin()});
            //事务成功完成
            db.setTransactionSuccessful();
        } finally {
            //结束事务
            db.endTransaction();
        }
    }

    public void add(List<UserEntity> users) {
        //开启事务
        db.beginTransaction();
        try {
            for (UserEntity user : users) {
                db.execSQL("insert into " + TBNAME + "(username,pin) values(?,?)", new Object[]{user.getusername(), user.getPin()});
            }
            //事务成功完成
            db.setTransactionSuccessful();
        } finally {
            //结束事务
            db.endTransaction();
        }
    }

    /**
     * 修改数据-修改用户名
     */
    public void modifiedUserName(String oldUserName,String newUserName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", newUserName);
        db.update(TBNAME, contentValues, "username = ?", new String[]{String.valueOf(oldUserName)});
    }

    /**
     * 删除数据-删除某个用户
     */
    public void deleteUser(String delUserName) {
        db.delete(TBNAME, "username = ?", new String[]{String.valueOf(delUserName)});
    }

    /**
     * 查询数据-查询当前数据库所有用户数据
     */
    public List<UserEntity> query() {

        List<UserEntity> users = new ArrayList<UserEntity>();
        Cursor c = db.rawQuery("select * from " + TBNAME, null);

        if (c != null) {
            while (c.moveToNext()) {
                UserEntity user = new UserEntity();

                String userName = c.getString(c.getColumnIndex("username"));
                String pin = c.getString(c.getColumnIndex("pin"));

                user.setusername(userName);
                user.setPin(pin);


                users.add(user);
                //Log.i("info", "user: " + user);
            }
        }
        Log.i("info", "users: " + users);

        c.close();
        return users;
    }

    /**
     * 将object转化为map-UserEntity转化为List<map<string,object>>
     */
    public Map<String, Object> setObjectToMap(UserEntity objectUser) {

        Map<String, Object> mapUser = new HashMap<>();

        mapUser.put("username", objectUser.getusername());
        mapUser.put("pin", objectUser.getPin());

        return mapUser;
    }

    public List<Map<String, Object>> setObjectToMap(List<UserEntity> objectUsers) {

        List<Map<String, Object>> mapUsers = new ArrayList<>();

        for (UserEntity user : objectUsers) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", user.getusername());
            map.put("pin", user.getPin());
            mapUsers.add(map);
        }

        return mapUsers;
    }

    /**
     * 将map转化为object-List<map<string,object>>转化为UserEntity
     */
    public UserEntity setMapToObject(Map<String, Object> mapUser) {

        UserEntity objectUser = new UserEntity();

        objectUser.setusername(mapUser.get("username").toString());
        objectUser.setPin(mapUser.get("pin").toString());

        return objectUser;
    }

    public List<UserEntity> setMapToObject(List<Map<String, Object>> mapUsers) {

        List<UserEntity> objectUsers = new ArrayList<>();

        for (Map mapUser : mapUsers) {
            UserEntity objectUser = new UserEntity();

            objectUser.setusername(mapUser.get("username").toString());
            objectUser.setPin(mapUser.get("pin").toString());

            objectUsers.add(objectUser);
        }

        return objectUsers;
    }

    /**
     * 刷新数据库
     */
    public void updateDB(List<Map<String, Object>> users){

        clearDB();

//        List<UserEntity> objectUsers = new ArrayList<>();
//
//        for (Map user : users) {
//            UserEntity objectUser = new UserEntity();
//
//            objectUser.setusername(user.get("username").toString());
//            objectUser.setPin(user.get("pin").toString());
//
//            objectUsers.add(objectUser);
//        }

    }

    /**
     * 清空数据库
     */
    public void clearDB() {
        db.execSQL("DELETE FROM " + TBNAME);
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        db.close();
    }
}
