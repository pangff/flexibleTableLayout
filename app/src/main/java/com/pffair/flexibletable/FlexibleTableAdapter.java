package com.pffair.flexibletable;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;

/**
 * Created by pangff on 16/8/15.
 * Description FlexibleTableAdapter
 */
public abstract  class FlexibleTableAdapter{

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public abstract int getCount();

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract int getRow(int position);

    public abstract int getColumn(int position);

    public abstract View getView(int position);

}
