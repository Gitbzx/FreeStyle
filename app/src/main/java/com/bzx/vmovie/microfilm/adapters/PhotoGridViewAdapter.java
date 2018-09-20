package com.bzx.vmovie.microfilm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzx.vmovie.microfilm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created by bzx on 2018/9/17/017
 * Email:seeyou_x@126.com
 */

public class PhotoGridViewAdapter extends BaseAdapter{

    private Context mContext;
    private int mMaxPosition;
    private List<String> list = new ArrayList<>();

    public PhotoGridViewAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        mMaxPosition = list.size()+1;
        return mMaxPosition;
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
    public View getView(final int position, View v, final ViewGroup parent) {
        ViewHolder vh = null;
        if(v==null){
            vh = new ViewHolder();
            v = LayoutInflater.from(mContext).inflate(R.layout.photo_gridview_adapter,parent,false);
            vh.img = (ImageView) v.findViewById(R.id.pg_img);
            vh.btn = (ImageView) v.findViewById(R.id.pg_delbtn);
            v.setTag(vh);
        }else {
            vh = (ViewHolder) v.getTag();
        }

        if(position+1==mMaxPosition){
            Glide.with(mContext).load(R.mipmap.id_photo).dontAnimate().centerCrop().into(vh.img);
            vh.img.setVisibility(View.VISIBLE);
            vh.btn.setVisibility(View.GONE);
            if(position==6&&mMaxPosition==7){
                vh.img.setVisibility(View.GONE);
            }
        }else {
            vh.btn.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(list.get(position)).into(vh.img);
        }

        vh.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return v;
    }

    public class ViewHolder{
        private ImageView img,btn;
    }
}
