package com.pffair.flexibletable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlexibleTableLayout mFlexibleTableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlexibleTableLayout = (FlexibleTableLayout) findViewById(R.id.ftl_layout);
        mFlexibleTableLayout.setFlexibleTemplate(2,4);
        List<FlexibleItem> flexibleItemList = new ArrayList<>();

        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundColor(Color.BLUE);
        FlexibleItem flexibleItem1 = new FlexibleItem(1,4,imageView1);
        flexibleItemList.add(flexibleItem1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setBackgroundColor(Color.RED);
        FlexibleItem flexibleItem2 = new FlexibleItem(1,4,imageView2);
        flexibleItemList.add(flexibleItem2);
//
//
//        ImageView imageView3 = new ImageView(this);
//        imageView3.setBackgroundColor(Color.CYAN);
//        FlexibleItem flexibleItem3 = new FlexibleItem(1,1,imageView3);
//        flexibleItemList.add(flexibleItem3);
//
//
//        ImageView imageView4 = new ImageView(this);
//        imageView4.setBackgroundColor(Color.GREEN);
//        FlexibleItem flexibleItem4 = new FlexibleItem(1,3,imageView4);
//        flexibleItemList.add(flexibleItem4);

        mFlexibleTableLayout.addItems(flexibleItemList);
    }
}
