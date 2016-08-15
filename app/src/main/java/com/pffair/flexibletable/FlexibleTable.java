package com.pffair.flexibletable;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangff on 16/8/12.
 * Description FlexibaleTableLayout
 */
public class FlexibleTable extends ViewGroup {

    private int rowCount = 1;

    private int columnCount = 1;

    private int cellWidth = 0;

    private int cellHeight = 0;


    DataSetObserver mDataSetObserver;

    FlexibleTableAdapter mFlexibleTableAdapter;

    private List<FlexibleItem> itemList = new ArrayList<>();

    private int[] indexArray;

    private TableItemClickListener mTableItemClickListener;

    public void setAdapter(FlexibleTableAdapter flexibleTableAdapter) {
        this.mFlexibleTableAdapter = flexibleTableAdapter;

        if (mFlexibleTableAdapter != null && mDataSetObserver != null) {
            mFlexibleTableAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        resetList();

        if(mFlexibleTableAdapter!=null){

            mDataSetObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    reLayout();
                }
            };

            mFlexibleTableAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    private void resetList(){
        itemList.clear();
        indexArray = new int[rowCount*columnCount];
    }

    public interface TableItemClickListener {

        void onItemClicked(View itemView, int position);
    }

    public FlexibleTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlexibleTable(Context context) {
        super(context);
    }

    public void setFlexibleTableVolume(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        indexArray = new int[cellWidth*columnCount];
    }

    private void reLayout() {
        resetList();
        int size = mFlexibleTableAdapter.getCount();
        for (int i = 0; i < size; i++) {
            final View itemView = mFlexibleTableAdapter.getView(i);
            final int row = mFlexibleTableAdapter.getRow(i);
            final int column = mFlexibleTableAdapter.getColumn(i);
            final FlexibleItem item = new FlexibleItem(row, column, itemView);
            addItemView(i, item);
        }
    }


    private void addItemView(int index, final FlexibleItem item) {
        item.startCellIndex = getCurrentCellStartIndex();
        item.setRowIndex(item.startCellIndex, columnCount);
        item.setColumnIndex(item.startCellIndex, columnCount);
        occupyCellIndex(item.startCellIndex, item.rowCount, item.columnCount);
        LayoutParams layoutParams = new LayoutParams(cellWidth, cellHeight);
        this.addViewInLayout(item.itemView, -1, layoutParams, true);
        final int position = index;
        item.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTableItemClickListener != null) {
                    mTableItemClickListener.onItemClicked(item.itemView, position);
                }
            }
        });
        itemList.add(item);
    }


    public void setOnTableItemClickListener(TableItemClickListener tableItemClickListener) {
        this.mTableItemClickListener = tableItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < itemList.size(); i++) {
            View v = getChildAt(i);
            float fw = cellWidth * itemList.get(i).columnCount;
            float fh = cellHeight * itemList.get(i).rowCount;
            int wSpec = MeasureSpec.makeMeasureSpec(Math.round(fw), MeasureSpec.EXACTLY);
            int hSpec = MeasureSpec.makeMeasureSpec(Math.round(fh), MeasureSpec.EXACTLY);
            v.measure(wSpec, hSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
                int[] location = calculateLocationByIndex(item.startRowIndex,item.startColumnIndex);
                int left = l + location[0];
                int top = t + location[1];
                int right = left + cellWidth * item.columnCount;
                int bottom = top + cellHeight * item.rowCount;
                item.itemView.layout(left, top, right, bottom);
            }
        }
    }

    /**
     * 根据单元格index计算view绘制起始位置
     */
    private int[] calculateLocationByIndex(int rowIndex, int columnIndex) {
        return new int[]{columnIndex * cellWidth, rowIndex * cellHeight};
    }


    /**
     * 根据当前元素行列数以及它应该的起始位置 将它占据的单元格设置为1（已占用）
     */
    private void occupyCellIndex(int index, int cellRowCount, int cellColumnCount) {
        for (int i = 0; i < cellColumnCount; i++) {
            indexArray[index + i] = 1;
        }
        for (int i = 0; i < cellRowCount; i++) {
            indexArray[index + i * columnCount] = 1;
        }
    }

    @Override
    public void requestLayout() {
        Log.e("pangff", "pangff-requestLayout");
        super.requestLayout();
    }

    /**
     * 当前index是否被占用
     */
    private boolean hasOccupy(int index) {
        return indexArray[index] == 1;
    }


    /**
     * 找到没有被占据的第一个单元格index
     */
    private int getCurrentCellStartIndex() {
        for (int i = 0; i < indexArray.length; i++) {
            if (!hasOccupy(i)) {
                return i;
            }
        }
        return 0;
    }


}
