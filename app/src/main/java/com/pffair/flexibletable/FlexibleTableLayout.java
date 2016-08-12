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

    /**
     * 根据单元格index计算view绘制起始位置
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    private int[] calculateLocationByIndex(int rowIndex, int columnIndex) {
        return new int[]{columnIndex * cellWidth, rowIndex * cellHeight};
    }


    /**
     * 根据当前元素行列数以及它应该的起始位置 将它占据的单元格设置为1（已占用）
     * @param index
     * @param cellRowCount
     * @param cellColumnCount
     */
    private void occuptyCellIndex(int index,int cellRowCount,int cellColumnCount){
        for(int i=0;i<cellColumnCount;i++){
            indexArray[index+i] = 1;
        }
        for(int i=0;i<cellRowCount;i++){
            indexArray[index+i*columnCount]=1;
        }
    }

    /**
     * 当前index是否被占用
     * @param index
     * @return
     */
    private boolean hasOccupy(int index){
        return indexArray[index] == 1;
    }


    /**
     * 找到没有被占据的第一个单元格index
     * @return
     */
    private int getCurrentCellStartIndex(){
        for(int i=0;i<indexArray.length;i++){
            if(!hasOccupy(i)){
                return i;
            }
        }
        return 0;
    }


}
