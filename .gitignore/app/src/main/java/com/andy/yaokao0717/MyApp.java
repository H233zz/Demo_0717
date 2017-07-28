package com.andy.yaokao0717;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.example.library.App;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

/**
 * 类描述：
 * 创建人：yekh
 * 创建时间：2017/7/17 15:28
 */
public class MyApp extends App{
    private DbManager.DaoConfig daoConfig;

    private DbManager db;

    @Override
    public void onCreate() {
        super.onCreate();

        //NetImageLoader.getInstance().init(new GlideImageLoader());

        daoConfig = new DbManager.DaoConfig()
                .setDbName("azhong.db")//设置数据库的名字
                .setTableCreateListener(new DbManager.TableCreateListener() {//创建表的监听
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.d("TAG", table.getName());
                    }
                })
                .setAllowTransaction(true)//设置是否允许事务，默认是true
                .setDbDir(new File(Environment.getExternalStorageDirectory() + "/zsy"))//设置数据库路径，默认是data/data/包名/database
                .setDbOpenListener(new DbManager.DbOpenListener() {//设置数据库打开的监听
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();//开启多线程操作
                    }
                });
//          .setDbUpgradeListener()//设置数据库更新的监听
//          .setDbVersion(1);//设置数据库的版本

        db= x.getDb(daoConfig);

       SharedPreferences sp = getSharedPreferences("user_setting",MODE_PRIVATE);
        boolean isNight = sp.getBoolean("night", false);
        if (isNight) {
            //这是设置成夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //这是设置成非夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public DbManager getDb() {
        return db;
    }
}
