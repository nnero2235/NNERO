package com.nnero.nnero.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nnero on 16/3/11.
 * listview adapter基类
 */
public abstract class NBaseAdapter<T,VH extends NBaseAdapter.ViewHolder> extends BaseAdapter{

    protected List<T> list;
    protected Activity mActivity;

    public NBaseAdapter(List<T> list,Activity activity){
        this.list = list;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(getLayoutId(),null);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        onBindViewHolder(viewHolder,position);
        return convertView;
    }

    public void setData(List<T> t){
        this.list = t;
    }

    /**
     * 获取layout
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 获取viewholder
     * @return
     */
    public abstract VH getViewHolder(View v);

    /**
     * 得到viewholder后执行操作
     * @param viewHolder
     */
    public abstract void onBindViewHolder(VH viewHolder,int pos);

    public static class ViewHolder{

        public View mItemView;

        public ViewHolder(View v){
            mItemView = v;
        }
    }
}
