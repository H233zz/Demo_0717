package com.andy.yaokao0717;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.adapter.CommAdapter;
import com.example.library.image.NetImageLoader;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类描述：
 * 创建人：yekh
 * 创建时间：2017/7/17 15:38
 */
public class MyAdapter extends CommAdapter<DataBean,MyAdapter.ViewHolder>{

    public MyAdapter(Context context, List<DataBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void setViewByHolder(ViewHolder viewHolder, DataBean dataBean) {
        viewHolder.title.setText(dataBean.getNews_title());
        NetImageLoader.getInstance().display(dataBean.getPic_url(),viewHolder.image);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    class ViewHolder{
        @ViewInject(R.id.title)
        TextView title;
        @ViewInject(R.id.image)
        ImageView image;
    }
}
