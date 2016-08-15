package com.pffair.flexibletable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DataAdapter mDataAdapter;

    FlexibleTable mFlexibleTableLayout;

    MyFlexibleTableAdapter mMyFlexibleTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataAdapter = new DataAdapter();

        ListView lvList = (ListView) findViewById(R.id.lv_list);
        lvList.setAdapter(mDataAdapter);

        initHeader(lvList);
    }


    private void initHeader(ListView lvList) {
        mMyFlexibleTableAdapter = new MyFlexibleTableAdapter();
        View headerView = LayoutInflater.from(this).inflate(R.layout.list_header,
                (ViewGroup) lvList.getRootView(), false);
        lvList.addHeaderView(headerView);

        mFlexibleTableLayout = (FlexibleTable) headerView.findViewById(R.id.ftl_layout);
        mFlexibleTableLayout.setFlexibleTableVolume(2, 4);


        mFlexibleTableLayout.setAdapter(mMyFlexibleTableAdapter);
        mMyFlexibleTableAdapter.refresh(createData());

        mFlexibleTableLayout.setOnTableItemClickListener(
                new FlexibleTable.TableItemClickListener() {
                    @Override
                    public void onItemClicked(View itemView, int position) {
                        Toast.makeText(MainActivity.this, "click:"+position, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private List<MyFlexibleTableAdapter.Item> createData(){
        List<MyFlexibleTableAdapter.Item> flexibleItemList = new ArrayList<>();

        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundColor(Color.BLUE);
        MyFlexibleTableAdapter.Item flexibleItem1 = new MyFlexibleTableAdapter.Item(3, 1, imageView1);
        flexibleItemList.add(flexibleItem1);


        ImageView imageView2 = new ImageView(this);
        imageView2.setBackgroundColor(Color.RED);
        MyFlexibleTableAdapter.Item flexibleItem2 = new MyFlexibleTableAdapter.Item(1, 2, imageView2);
        flexibleItemList.add(flexibleItem2);



        ImageView imageView3 = new ImageView(this);
        imageView3.setBackgroundColor(Color.CYAN);
        MyFlexibleTableAdapter.Item flexibleItem3 = new MyFlexibleTableAdapter.Item(1, 1, imageView3);
        flexibleItemList.add(flexibleItem3);



        FrameLayout frameLayout4 = new FrameLayout(this);
        ImageView imageView4 = new ImageView(this);
        imageView4.setBackgroundColor(Color.GREEN);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout4.addView(imageView4,lp);
        MyFlexibleTableAdapter.Item flexibleItem4 = new MyFlexibleTableAdapter.Item(1, 4, frameLayout4);
        flexibleItemList.add(flexibleItem4);

        return flexibleItemList;
    }


    public static class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return "测试mFlexibleTable和listview的结合";
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list, parent, false);
            }
            return convertView;
        }
    }
}
