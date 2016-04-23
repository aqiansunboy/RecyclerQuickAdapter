package com.m4399.recyclerview.adapter.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.m4399.recyclerview.adapter.RecyclerQuickAdapter;
import com.m4399.recyclerview.adapter.RecyclerQuickViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView testRecycler = (RecyclerView) findViewById(R.id.testRecyclerView);

        if (testRecycler != null)
        {
            testRecycler.setLayoutManager(new LinearLayoutManager(this));

            TestAdapter adapter = new TestAdapter(testRecycler);
            adapter.setOnItemClickListener(new RecyclerQuickAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View view, Object data, int position)
                {
                    Log.d("Test", "data = " + data + " index = " + position);
                }
            });
            testRecycler.setAdapter(adapter);

            // 既无Header也无Footer
            ArrayList<String> datas = new ArrayList<>();
            datas.add("Civic TypeR");
            datas.add("Golf GTI");
            datas.add("BRZ");
            datas.add("Focus RS");

            adapter.replaceAll(datas);

            // 验证适配器各个数据源更改方法
            // adapter.add("GTR");
            // adapter.addAll(Arrays.asList("GTR", "911", "BMW M4"));
            // adapter.set(1,"911");
            // adapter.remove(1);

            //--------我是分割线---------

            // 有Header无Footer

            // 添加Header
            View headerView = LayoutInflater.from(this).inflate(R.layout.viewholder_test, testRecycler, false);
            TestViewHolder headerItemView = new TestViewHolder(this, headerView);
            headerItemView.getTitleView().setBackgroundColor(
                    getResources().getColor(R.color.colorAccent));
            headerItemView.getTitleView().setText("高性能小钢炮合集");

            adapter.setHeaderView(headerItemView);

            // 验证适配器各个数据源更改方法
            // adapter.add("GTR");
            // adapter.addAll(Arrays.asList("GTR", "911", "BMW M4"));
            // adapter.set(1,"911");
            // adapter.remove(1);

            //--------我是分割线---------

            // 有Header有Footer

            // 添加Footer
            View footerView = LayoutInflater.from(this).inflate(R.layout.viewholder_test, testRecycler, false);
            TestViewHolder footerItemView = new TestViewHolder(this, footerView);
            footerItemView.getTitleView().setBackgroundColor(
                    getResources().getColor(R.color.colorAccent));
            footerItemView.getTitleView().setText("By aqiansunboy");

            adapter.setFooterView(footerItemView);

            // 验证适配器各个数据源更改方法
            // adapter.add("GTR");
            // adapter.addAll(Arrays.asList("GTR", "911", "BMW M4"));
            // adapter.set(1,"911");
            // adapter.remove(1);

            //--------我是分割线---------

            // 无Header有Footer

            // 去掉Header
            // adapter.setHeaderView(null);

            // 验证适配器各个数据源更改方法
            // adapter.add("GTR");
            // adapter.addAll(Arrays.asList("GTR", "911", "BMW M4"));
            // adapter.set(1,"911");
            // adapter.remove(1);

            //--------我是分割线---------

        }
    }


    private static class TestAdapter extends RecyclerQuickAdapter<String,TestViewHolder>
    {

        public TestAdapter(RecyclerView recyclerView)
        {
            super(recyclerView);
        }

        @Override
        protected int getItemLayoutID(int viewType)
        {
            return R.layout.viewholder_test;
        }

        @Override
        protected TestViewHolder createItemViewHolder(View itemView, int viewType)
        {
            TestViewHolder testViewHolder = new TestViewHolder(getContext(), itemView);
            if (viewType == 0)
            {
                testViewHolder.getTitleView().setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            }
            else
            {
                testViewHolder.getTitleView().setTextColor(getContext().getResources().getColor(R.color.colorWhite));
            }

            return testViewHolder;

        }

        @Override
        protected int getViewType(int position)
        {

            if (position % 2 == 0)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }

        @Override
        protected void onBindItemViewHolder(TestViewHolder holder, int position, int index,
                                            boolean isScrolling)
        {
            holder.getTitleView().setText(getData().get(index));
        }
    }

    private static class TestViewHolder extends RecyclerQuickViewHolder
    {
        private TextView mTitleView;

        public TestViewHolder(Context context, View itemView)
        {
            super(context, itemView);
        }

        @Override
        protected void initView()
        {
            mTitleView = findViewById(R.id.titleView);
        }

        public TextView getTitleView()
        {
            return mTitleView;
        }
    }
}
