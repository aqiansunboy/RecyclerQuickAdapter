package com.m4399.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name: RecyclerQuickAdapter
 * File Name:    RecyclerQuickAdapter.java
 * ClassName:    RecyclerQuickAdapter
 *
 * Description: 封装统一的RecyclerView适配器.
 *
 * @author Chaoqian Wu
 * @date 2015年08月31日 下午3:29
 *
 * Copyright (c) 2015年, 4399 Network CO.ltd. All Rights Reserved.
 */
public abstract class RecyclerQuickAdapter<T, VH extends RecyclerQuickViewHolder> extends RecyclerView.Adapter<RecyclerQuickViewHolder>
{
    public static final int VIEW_TYPE_HEADER = -1001;

    public static final int VIEW_TYPE_FOOTER = -1002;

    private Context mContext;

    private List<T> mData;

    private RecyclerQuickViewHolder mHeaderView;

    private RecyclerQuickViewHolder mFooterView;

    protected boolean isScrolling;

    private OnItemClickListener mListener;

    private ArrayList<VH> mRecyclerViewHolders;

    /**
     * 获取一个对象，表示当前列表的上下文
     * @return
     */
    public Context getContext()
    {
        return mContext;
    }

    /**
     * 获取一个集合，表示当前列表对应的数据源
     * @return
     */
    public List<T> getData()
    {
        return mData;
    }

    /**
     * 获取一个集合，表示当前的所有已创建的ViewHolder
     * @return
     */
    protected List<VH> getRecyclerViewHolders()
    {
        return mRecyclerViewHolders;
    }

    /**
     *
     * Fragment 可见与隐藏的方法，子类重写此方法实现界面不可见时去除不必要的
     * 真正能回调fragment是否可见的方法,空方法体,子类需要处理页面逻辑的话，自行实现
     *
     * @param isVisibleToUser
     */
    public void onUserVisible(boolean isVisibleToUser)
    {

    }

    /**
     * 当界面被销毁时调用此方法，用于释放适配器的内存
     * 子类重写此方法用于释放适配器的内存
     * 此方法必须依赖于界面的onDestroy来调用
     */
    public void onDestroy()
    {
        mListener = null;
        mContext = null;

        if (mData != null)
        {
            mData.clear();
            mData = null;
        }
        if (mHeaderView != null)
        {
            mHeaderView = null;
        }
        if (mFooterView != null)
        {
            mFooterView = null;
        }

        if (mRecyclerViewHolders != null)
        {
            for (RecyclerQuickViewHolder holder : mRecyclerViewHolders)
            {
                holder.onDestroy();
            }
            mRecyclerViewHolders.clear();
            mRecyclerViewHolders=null;
        }

    }

    /**
     * 构造函数
     * @param recyclerView 当前列表控件
     */
    public RecyclerQuickAdapter(RecyclerView recyclerView)
    {
        this(recyclerView, null);
    }

    /**
     * 指定数据源的构造函数
     * @param recyclerView 当前列表控件
     * @param data 数据源
     */
    public RecyclerQuickAdapter(RecyclerView recyclerView,List<T> data)
    {
        this.mRecyclerViewHolders=new ArrayList<>();
        this.mContext = recyclerView.getContext();
        this.mData = (data == null ? new ArrayList<T>() : data);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING);
            }
        });
    }

    private boolean isHeader(int position)
    {
        return (hasHeader() && position==0);
    }

    private boolean isFooter(int position)
    {
        return (hasFooter() && position==getItemCount()-1);
    }

    /**
     * 获取一个值，表示列表是否有头部
     * @return
     */
    protected final boolean hasHeader()
    {
        return getHeaderViewHolder()!=null;
    }

    /**
     * 获取一个值，表示列表是否有FOOTER
     * @return
     */
    protected final boolean hasFooter()
    {
        return getFooterViewHolder()!=null;
    }

    /**
     * 重写创建ViewHolder方法，实现列表的头部机制
     * @param parent
     * @param viewType 列表项示图的类型
     * @return 列表项示图
     */
    @Override
    public final RecyclerQuickViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView;
        RecyclerQuickViewHolder viewHolder;
        if (viewType == VIEW_TYPE_HEADER)
        {
            viewHolder = getHeaderViewHolder();

        }
        else if (viewType == VIEW_TYPE_FOOTER)
        {
            viewHolder = getFooterViewHolder();
        }
        else
        {
            itemView = LayoutInflater.from(mContext)
                                     .inflate(getItemLayoutID(viewType), parent, false);
            viewHolder = createItemViewHolder(itemView, viewType);
        }

        mRecyclerViewHolders.add((VH)viewHolder);
        return viewHolder;
    }

    /**
     * 获取列表项成员个数
     * 列表的头部也会算在内
     * @return 成员个数
     */
    @Override
    public int getItemCount()
    {
        if (hasHeader())
        {
            return hasFooter() ? mData.size() + 1 + 1 : mData.size() + 1;
        }
        else
        {
            return hasFooter() ? mData.size() + 1 : mData.size();
        }
    }

    /**
     * 重写根据位置获取列表项示图类型的方法
     * 增加对列表头部示图类型的处理
     * @param position 当前项的位置
     * @return 示图类型
     */
    @Override
    public final int getItemViewType(int position)
    {
        if (isHeader(position))
        {
            return VIEW_TYPE_HEADER;
        }
        if (isFooter(position))
        {
            return VIEW_TYPE_FOOTER;
        }
        else
        {
            return hasHeader() ? getViewType(position-1) : getViewType(position);
        }
    }

    /**
     * 重写onBindViewHolder方法，处理有头部的列表
     * @param holder 当前的列表项示图
     * @param position 当前在列表的位置
     */
    @Override
    public final void onBindViewHolder(RecyclerQuickViewHolder holder, int position)
    {
        if (hasHeader())
        {
            if (!isHeader(position) && !isFooter(position))
            {
                int index = position - 1;
                onBindItemViewHolder((VH)holder, position,index, isScrolling);
                ((VH) holder).itemView.setOnClickListener(getOnClickListener(index));
            }
        }
        else
        {
            if (!isFooter(position))
            {
                onBindItemViewHolder((VH) holder, position, position, isScrolling);
                ((VH) holder).itemView.setOnClickListener(getOnClickListener(position));
            }
        }
    }

    /**
     * 设置头部的示图
     * @param headerView 头部的示图
     */
    public void setHeaderView(RecyclerQuickViewHolder headerView)
    {
        this.mHeaderView = headerView;
    }

    /**
     * 获取一个对象，表示列表的头部示图控件
     * @param <HEADER> 列表的头部
     * @return 列表的头部控件
     */
    public <HEADER extends RecyclerQuickViewHolder> HEADER getHeaderViewHolder()
    {
        return (HEADER) mHeaderView;
    }

    public void setFooterView(RecyclerQuickViewHolder footerView)
    {
        this.mFooterView = footerView;
    }

    /**
     * 获取一个对象，表示列表的FOOTER示图控件
     * @param <FOOTER> 列表的FOOTER
     * @return 列表的FOOTER控件
     */
    public <FOOTER extends RecyclerQuickViewHolder> FOOTER getFooterViewHolder()
    {
        return (FOOTER) mFooterView;
    }

    /**
     * 指定列表示图类型，获取列表的layoutId
     * @param viewType 列表的示图类型
     * @return layoutId
     */
    protected abstract int getItemLayoutID(int viewType);

    /**
     * 指定列表示图类型，创建对应的列表示图对象
     * @param itemView
     * @param viewType 列表示图类型
     * @return 列表示图对象
     */
    protected abstract VH createItemViewHolder(View itemView, int viewType);

    /**
     * 指定列表项的位置，获取示图类型
     * @param position 位置
     * @return 示图类型
     */
    protected abstract int getViewType(int position);

    /**
     * 绑定VH与数据
     *  @param holder       VH对象
     * @param position      VH在RecyclerView中的位置
     * @param index         数据真实索引（如果有header，index = positioin-1，如果没有header，index = position
     * @param isScrolling   标记是否是滚动中
     */
    protected abstract void onBindItemViewHolder(VH holder, int position, int index,
                                                 boolean isScrolling);

    /**
     * 添加一个成员到数据源
     * @param elem 成员
     */
    public void add(T elem)
    {
        if (elem == null)
        {
            return;
        }

        mData.add(elem);

        int positionStart = getItemCount() - 1;
        if (hasFooter())
        {
            // 减一是减FOOTER的
            positionStart--;
        }

        notifyItemInserted(positionStart);

    }

    /**
     * 添加一个列表到数据源
     * @param elem
     */
    public void addAll(List<T> elem)
    {
        if (elem == null || elem.isEmpty())
        {
            return;
        }

        int positionStart = getItemCount();
        if (hasFooter())
        {
            // 减一是减FOOTER的
            positionStart--;
        }

        mData.addAll(elem);

        notifyItemRangeInserted(positionStart, elem.size());
    }

    /**
     * 指定旧的项，替换成新的项
     * @param oldElem 旧的项
     * @param newElem 新的项
     */
    public void set(T oldElem, T newElem)
    {
        set(mData.indexOf(oldElem), newElem);
    }

    /**
     * 指定位置 设置一个新的成员
     * @param index
     * @param elem
     */
    public void set(int index, T elem)
    {
        if (elem == null)
        {
            return;
        }

        if (index >= mData.size())
        {
            return;
        }

        mData.set(index, elem);

        if (hasHeader())
        {
            notifyItemChanged(index+1);
        }
        else
        {
            notifyItemChanged(index);
        }
    }

    /**
     * 移除指定的项
     * @param elem 要移除的项
     */
    public void remove(T elem)
    {
        remove(mData.indexOf(elem));
    }

    /**
     * 移除指定位置的项
     * @param index
     */
    public void remove(int index)
    {
        if (index >= mData.size())
        {
            return;
        }

        mData.remove(index);

        if (hasHeader())
        {
            notifyItemRemoved(index+1);
        }
        else
        {
            notifyItemRemoved(index);
        }

    }

    /**
     * 重置列表数据源，并添加新数据源集合
     * @param elem
     */
    public void replaceAll(List<T> elem)
    {
        if (elem == null || elem.isEmpty())
        {
            return;
        }

        if (mData != elem)
        {
            mData.clear();
            mData.addAll(elem);
        }

        notifyDataSetChanged();
    }

    /**
     * 设置列表项的点击事件
     * @param l 点击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListener l)
    {
        mListener = l;
    }

    /**
     *
     * @param position
     * @return
     */
    private View.OnClickListener getOnClickListener(final int position)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(@Nullable View v)
            {
                if (mListener != null && v != null)
                {
                    if (position < getData().size())
                    {
                        mListener.onItemClick(v, getData().get(position), position);
                    }
                    else
                    {
                        mListener.onItemClick(v, null, position);
                    }
                }
            }
        };
    }

    /**
     * 列表点击事件
     */
    public interface OnItemClickListener<T>
    {
        void onItemClick(View view, T data, int position);
    }
}
