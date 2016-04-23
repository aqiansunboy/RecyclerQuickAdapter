package com.m4399.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Project Name: RecyclerQuickAdapter
 * File Name:    RecyclerQuickViewHolder.java
 * ClassName:    RecyclerQuickViewHolder
 *
 * Description: 封装统一的RecyclerView的ViewHolder.
 *
 * @author Chaoqian Wu
 * @date 2015年09月01日 上午10:03
 *
 * Copyright (c) 2015年, 4399 Network CO.ltd. All Rights Reserved.
 */
public abstract class RecyclerQuickViewHolder extends RecyclerView.ViewHolder
{
    private SparseArray<View> mViews;

    private Context mContext;

    public RecyclerQuickViewHolder(Context context, View itemView)
    {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mContext = context;
        this.initView();
    }

    public Context getContext()
    {
        return mContext;
    }

    protected abstract void initView();

    protected <T extends View> T findViewById(int id)
    {
        View view = mViews.get(id);
        if (view == null)
        {
            view = itemView.findViewById(id);
            if (view != null)
            {
                mViews.put(id, view);
            }
        }
        return (T) view;
    }

    public <T extends View> T getView(int viewId)
    {
        return findViewById(viewId);
    }

    public RecyclerQuickViewHolder setText(int viewId, int valueId)
    {
        TextView view = findViewById(viewId);
        return setText(view, valueId);
    }

    public RecyclerQuickViewHolder setText(TextView view, int valueId)
    {
        if (view == null)
        {
            return this;
        }

        view.setText(getContext().getString(valueId));
        return this;
    }

    public RecyclerQuickViewHolder setText(int viewId, CharSequence value)
    {
        TextView view = findViewById(viewId);
        return setText(view, value);
    }

    public RecyclerQuickViewHolder setText(TextView view, CharSequence value)
    {
        if (view == null)
        {
            return this;
        }

        view.setText(value);
        return this;
    }

    public RecyclerQuickViewHolder setImageResource(int viewId, int imageResId)
    {
        ImageView view = findViewById(viewId);
        return setImageResource(view, imageResId);
    }

    public RecyclerQuickViewHolder setImageResource(ImageView view, int imageResId)
    {
        if (view == null)
        {
            return this;
        }

        view.setImageResource(imageResId);
        return this;
    }

    public RecyclerQuickViewHolder setImageUrl(int viewId, String imageUrl, int imageHolderId)
    {
        return setImageUrl(viewId, imageUrl, imageHolderId, false);
    }

    public RecyclerQuickViewHolder setImageUrl(ImageView view, String imageUrl, int imageHolderId)
    {
        return setImageUrl(view, imageUrl, imageHolderId, false);
    }

    public RecyclerQuickViewHolder setImageUrl(int viewId, String imageUrl, int imageHolderId,
                                               boolean isScrolling)
    {
        ImageView view = findViewById(viewId);
        return setImageUrl(view,imageUrl,imageHolderId, isScrolling);
    }

    public RecyclerQuickViewHolder setImageUrl(ImageView view, String imageUrl, int imageHolderId,
                                               boolean isScrolling)
    {
        return this;
    }

    public RecyclerQuickViewHolder setBackgroundColor(int viewId, int colorId)
    {
        View view = findViewById(viewId);
        return setBackgroundColor(view, colorId);
    }

    public RecyclerQuickViewHolder setBackgroundColor(View view, int colorId)
    {
        if (view == null)
        {
            return this;
        }

        view.setBackgroundColor(mContext.getResources().getColor(colorId));
        return this;
    }

    public RecyclerQuickViewHolder setBackgroundResource(int viewId, int backgroundRes)
    {
        View view = findViewById(viewId);
        return setBackgroundResource(view, backgroundRes);
    }

    public RecyclerQuickViewHolder setBackgroundResource(View view, int backgroundRes)
    {
        if (view == null)
        {
            return this;
        }

        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecyclerQuickViewHolder setTextColor(int viewId, int textColor)
    {
        TextView view = findViewById(viewId);
        return setTextColor(view, textColor);
    }

    public RecyclerQuickViewHolder setTextColor(TextView view, int textColor)
    {
        if (view == null)
        {
            return this;
        }

        view.setTextColor(textColor);
        return this;
    }

    public RecyclerQuickViewHolder setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = findViewById(viewId);
        return setImageDrawable(view,drawable);
    }

    public RecyclerQuickViewHolder setImageDrawable(ImageView view, Drawable drawable)
    {
        if (view == null)
        {
            return this;
        }

        view.setImageDrawable(drawable);
        return this;
    }

    public RecyclerQuickViewHolder setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = findViewById(viewId);
        return setImageBitmap(view, bitmap);
    }

    public RecyclerQuickViewHolder setImageBitmap(ImageView view, Bitmap bitmap)
    {
        if (view == null)
        {
            return this;
        }

        view.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerQuickViewHolder setAlpha(int viewId, float value)
    {
        View view = findViewById(viewId);
        return setAlpha(view,value);
    }

    public RecyclerQuickViewHolder setAlpha(View view, float value)
    {
        if (view == null)
        {
            return this;
        }

        if (Build.VERSION.SDK_INT >= 11) {
            view.setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        }
        return this;
    }

    public RecyclerQuickViewHolder setVisible(int viewId, boolean visible)
    {
        View view = findViewById(viewId);
        return setVisible(view, visible);
    }

    public RecyclerQuickViewHolder setVisible(View view, boolean visible)
    {
        if (view == null)
        {
            return this;
        }

        int visibility = visible ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 当界面被销毁时调用此方法.
     * 子类重写此方法用于释放内存
     * 此方法必须依赖于界面的onDestroy来调用
     */
    public void onDestroy()
    {
        if (mViews!=null)
        {
            mViews.clear();
            mViews=null;
        }
        mContext=null;
    }

}
