package com.pffair.flexibletable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangff on 16/8/12.
 * Description FlexibaleTableLayout
 */
public class FlexibleTableLayout extends ViewGroup {

    private int rowCount = 1;

    private int columnCount = 1;

    private int cellWidth = 0;

    private int cellHeight = 0;

    private List<FlexibleItem> itemList = new ArrayList<>();

    private int[] indexArray;

    public FlexibleTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlexibleTableLayout(Context context) {
        super(context);
    }

    public void setFlexibleTemplate(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        indexArray = new int[rowCount*columnCount];
    }

    public void addItems(List<FlexibleItem> flexibleItemList) {
        itemList.clear();
        if (flexibleItemList != null) {
            this.removeAllViews();
            itemList.addAll(flexibleItemList);
            this.removeAllViews();
            for (int i = 0; i < itemList.size(); i++) {
                FlexibleItem item = itemList.get(i);
                if (item != null) {
                    item.index = getCurrentCellStartIndex();
                    item.setRowIndex(item.index,columnCount);
                    item.setColumnIndex(item.index,columnCount);
                    occuptyCellIndex(item.index,item.rowCount,item.columnCount);
                    LayoutParams layoutParams = new LayoutParams(cellWidth,cellHeight);
                    this.addView(item.itemView, layoutParams);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellWidth = w / columnCount;
        cellHeight = h / rowCount;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < itemList.size(); i++) {
            FlexibleItem item = itemList.get(i);
            if (item != null) {
                int location[] = calculateLocationByIndex(item.rowIndex, item.columnIndex);
                item.itemView.layout(l + location[0], t + location[1],
                        l + location[0] + cellWidth * item.columnCount,
                        t + location[1] + cellHeight * item.rowCount);
            }
        }
    }

    private int[] calculateLocationByIndex(int rowIndex, int columnIndex) {
        return new int[]{columnIndex * cellWidth, rowIndex * cellHeight};
    }


    private void occuptyCellIndex(int index,int cellRowCount,int cellColumnCount){
        for(int i=0;i<cellColumnCount;i++){
            indexArray[index+i] = 1;
        }
        for(int i=0;i<cellRowCount;i++){
            indexArray[index+i*columnCount]=1;
        }
    }

    private boolean hasOccupy(int index){
        return indexArray[index] == 1;
    }


    private int getCurrentCellStartIndex(){
        for(int i=0;i<indexArray.length;i++){
            if(!hasOccupy(i)){
                return i;
            }
        }
        return 0;
    }


}
