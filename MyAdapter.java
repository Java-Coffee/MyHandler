package com.example.dhw.myhandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DHW on 2016/8/24.
 */
public class MyAdapter extends BaseAdapter{
    private List<ItemBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private Context context;
    public MyAdapter(Context context, List<ItemBean> List) {
        mList = List;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
            view = mInflater.inflate(R.layout.item, null);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textViewtitle = (TextView) view.findViewById(R.id.music_title);
        TextView textViewcontent = (TextView) view.findViewById(R.id.music_content);
        //imageView.setImageBitmap(BitmapFactory.decodeStream((mList.get(i).ItemImageID).));
        //Bitmap bitmap = getHttpBitmap(mList.get(i).ItemImageID);
        imageView.setImageResource(Integer.parseInt(mList.get(i).ItemImageID));
        textViewtitle.setText(mList.get(i).ItemTitle);
        textViewcontent.setText(mList.get(i).ItemContent);
        return view;
    }
}
