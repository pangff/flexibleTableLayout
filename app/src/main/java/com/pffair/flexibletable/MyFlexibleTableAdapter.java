package com.pffair.flexibletable;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangff on 16/8/15.
 * Description MyFlexibleTableAdapter
 */
public class MyFlexibleTableAdapter extends FlexibleTableAdapter{

    List<Item> mItemList = new ArrayList<>();

    static class Item{
        public int row;
        public int column;
        public View itemView;

        public Item(int row,int column,View itemView){
            this.row = row;
            this.column = column;
            this.itemView = itemView;
        }
    }

    public void refresh(List<Item> list){
        if(list!=null){
            mItemList.clear();
            mItemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getRow(int position) {
        return mItemList.get(position).row;
    }

    @Override
    public int getColumn(int position) {
        return mItemList.get(position).column;
    }

    @Override
    public View getView(int position) {
        return  mItemList.get(position).itemView;
    }
}
