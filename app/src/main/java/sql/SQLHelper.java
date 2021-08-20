package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import home.item.video.Video;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "MyVideoVer2.db";
    static final String DB_TABLE_VIDEO = "Video";
    static final String DB_TABLE_USER = "USER";
    static final int DB_VERSION = 3;
    public SQLHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTableVideo="CREATE TABLE Video("+
                "id INTEGER NOT NULL PRIMARY KEY ,"+
                "title TEXT,"+
                "avatar TEXT,"+
                "urlLink TEXT,"+
                "views INTEGER," +
                "likes INTEGER)";


        String queryCreateTableUser="CREATE TABLE User("+
                "id INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT,"+
                "tdn TEXT,"+
                "mk TEXT,"+
                "vip INTEGER)";
        db.execSQL(queryCreateTableVideo);
        db.execSQL(queryCreateTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion!=oldVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE_VIDEO);
            db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE_USER);
            onCreate(db);
        }
    }

    public  void insertVideo(Video video){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("id",video.getId());
        contentValues.put("title",video.getTitle());
        contentValues.put("avatar",video.getAvatar());
        contentValues.put("urlLink",video.getUrlLink());
        contentValues.put("views",video.getViews()+1);
        contentValues.put("likes",video.getLikes());
        sqLiteDatabase.insert(DB_TABLE_VIDEO,null,contentValues);
    }
    public void insertUser(String tdn, String mk){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("tdn",tdn);
        contentValues.put("mk",mk);
        contentValues.put("vip",0);
        sqLiteDatabase.insert(DB_TABLE_USER,null,contentValues);
    }
    public User checkLogin(String tdn,String mk){
        User user= null;
        SQLiteDatabase sqLiteDatabase= getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(DB_TABLE_USER,null,"tdn=? AND mk=?",new String[]{tdn,mk},
                null,null,null,null);
        if(cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndex("id"));
            int vip= cursor.getInt(cursor.getColumnIndex("vip"));
            user= new User(tdn,mk);
            user.setVip(vip);

        }
            return user;
    }
    public List<User> getListUser(){
        List<User> list= new ArrayList<>();
        SQLiteDatabase sqLiteDatabase= getReadableDatabase();
        Cursor cursor= sqLiteDatabase.query(false,DB_TABLE_USER,null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndex("id"));
            String tdn= cursor.getString(cursor.getColumnIndex("tdn"));
            String mk= cursor.getString(cursor.getColumnIndex("mk"));
            int vip= cursor.getInt(cursor.getColumnIndex("vip"));
            User user= new User(tdn, mk);
            user.setVip(vip);
            list.add(user);
        }

        return list;
    }
    public void searchVideo(int id){
        SQLiteDatabase sqLiteDatabase= getReadableDatabase();
    }
    public void updateVideoViews(int id,int views){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("views",views);
        sqLiteDatabase.update(DB_TABLE_VIDEO,contentValues,
                "id=?",new String[]{id+""});

    }
    public void updateVIP(String tdn){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("vip",1);
        sqLiteDatabase.update(DB_TABLE_USER,contentValues,"tdn=?",new String[]{tdn});
    }
    public void updateVideoLikes(int id,int likes){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("likes",likes);
        sqLiteDatabase.update(DB_TABLE_VIDEO,contentValues,
                "id=?",new String[]{id+""});

    }
    public List<Video> getAllVideo(){
        List<Video> videoList = new ArrayList<>();
        Video video;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(false,DB_TABLE_VIDEO,
                null,
                null,
                null,
                null,
                null,
                "views DESC",
                null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String  title=cursor.getString(cursor.getColumnIndex("title"));
            String  avatar=cursor.getString(cursor.getColumnIndex("avatar"));
            String  urlLink=cursor.getString(cursor.getColumnIndex("urlLink"));
            int views=cursor.getInt(cursor.getColumnIndex("views"));
            int likes=cursor.getInt(cursor.getColumnIndex("likes"));
            video= new Video(id,title,avatar,urlLink,views,likes);
            videoList.add(video);
        }
        return videoList;
    }
    public List<Video> getAllLikedVideo(){
        List<Video> videoList = new ArrayList<>();
        Video video;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(false,DB_TABLE_VIDEO,
                null,
                "likes>?",
                new String[]{"0"},
                null,
                null,
                "likes DESC",
                null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String  title=cursor.getString(cursor.getColumnIndex("title"));
            String  avatar=cursor.getString(cursor.getColumnIndex("avatar"));
            String  urlLink=cursor.getString(cursor.getColumnIndex("urlLink"));
            int views=cursor.getInt(cursor.getColumnIndex("views"));
            int likes=cursor.getInt(cursor.getColumnIndex("likes"));
            video= new Video(id,title,avatar,urlLink,views,likes);
            videoList.add(video);
        }
        return videoList;
    }

}
