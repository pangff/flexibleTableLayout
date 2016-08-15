package com.pffair.flexibletable;

import android.view.View;

/**
 * Created by pangff on 16/8/12.
 * Description FlexibleItem
 */
public class FlexibleItem {

    public View itemView;

    public int rowCount;

    public int columnCount;

    public int startCellIndex;

    public int startRowIndex;

    public int startColumnIndex;

    public FlexibleItem(int rowCount, int columnCount, View itemView) {
        this.itemView = itemView;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public void setRowIndex(int index, int containerColumnCount) {
        startRowIndex = index / containerColumnCount;
    }

    public void setColumnIndex(int index, int containerColumnCount) {
        startColumnIndex =  index%containerColumnCount;
    }
}
