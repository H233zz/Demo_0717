package com.andy.yaokao0717;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.library.BaseActvity;
import com.example.library.http.MyCallback;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActvity implements View.OnClickListener{
    private SharedPreferences sp;
    @ViewInject(R.id.lv)
    private ListView lv;
    private final String urlPath ="http://api.expoon.com/AppNews/getNewsList/type/1/p/";
    private int index;
    private List<DataBean> list=new ArrayList<>();

    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user_setting",MODE_PRIVATE);
        adapter=new MyAdapter(this,list,R.layout.item);
        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        loadData();
    }

    private void loadData() {
        WhereBuilder b = WhereBuilder.b();
        b.and("urlPath","=",urlPath+index);
        DbManager db = ((MyApp)getApplication()).getDb();
        try {
            List<DataBean> listBean = db.selector(DataBean.class).where(b).findAll();
            if (listBean != null && listBean.size() > 0) {
                Toast.makeText(MainActivity.this, "从数据库", Toast.LENGTH_SHORT).show();
                list.addAll(listBean);
                adapter.notifyDataSetChanged();

                index++;
            }else{
                Toast.makeText(MainActivity.this, "从网络", Toast.LENGTH_SHORT).show();
                http().get(urlPath+index, new MyCallback<Data>() {
                    @Override
                    public void success(Data data) {
                        for(DataBean bean :data.getData()){
                            bean.setUrlPath(urlPath+index);
                        }

                        list.addAll(data.getData());
                        adapter.notifyDataSetChanged();

                        DbManager db = ((MyApp)getApplication()).getDb();
                        try {
                            db.save(data.getData());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        index++;

                    }
                    @Override
                    public void error(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        changeNight();
    }

    private void changeNight() {
        boolean isNight = sp.getBoolean("night", false);
        if (isNight) {
            //这是设置成非夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sp.edit().putBoolean("night", false).commit();
        } else {
            //这是设置成夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sp.edit().putBoolean("night", true).commit();
        }
        recreate();
    }
}
