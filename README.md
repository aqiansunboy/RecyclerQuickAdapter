# RecyclerQuickAdapter
提供一套可以为RecyclerView添加Header和Footer的适配器解决方案。
<p align="center" >
  <img src="https://github.com/aqiansunboy/RecyclerQuickAdapter/blob/master/Screenshot.png?raw=true" alt="RecyclerQuickAdapter" title="RecyclerQuickAdapter">
</p>
### 头疼的RecyclerView
**RecyclerView**是Android Support V7库提供的一个全新的控件，是统一代替ListView和GridView的全新解决方案，并且提供更多的布局模式。有兴趣的小伙伴，是时候抛弃传统臃肿的ListView和GridView，拥抱RecyclerView吧。但是，虽然RecyclerView什么都好，但偏偏不能添加头部和尾部。这点，我相信让一开始接触RecyclerView的伙伴们走了不少弯路，包括我也是。为了方便团队的开发，我对官方的RecyclerAdapter进行了一些封装。使得开发者可以方便的为RecyclerView添加头部和尾部。并在公司项目实施过程中，修复了相关BUG。在这里开源出来，希望能安利更多的Android开发小伙伴。

### 主要Api

#### 工具类Api
```java
    /**
     * 设置头部视图
     * @param headerView 头部的示图
     */
    public void setHeaderView(RecyclerQuickViewHolder headerView);
    
    /**
     * 设置尾部视图
     * @param footerView
     */
    public void setFooterView(RecyclerQuickViewHolder footerView);
    
    /**
     * 添加一个成员到数据源
     * @param elem 成员
     */
    public void add(T elem);
    
    /**
     * 添加一个列表到数据源
     * @param elem
     */
    public void addAll(List<T> elem);
    
    /**
     * 指定旧的项，替换成新的项
     * @param oldElem 旧的项
     * @param newElem 新的项
     */
    public void set(T oldElem, T newElem);
    
    /**
     * 指定位置 设置一个新的成员
     * @param index
     * @param elem
     */
    public void set(int index, T elem);
    
    /**
     * 移除指定的项
     * @param elem 要移除的项
     */
    public void remove(T elem);
    
    /**
     * 移除指定位置的项
     * @param index
     */
    public void remove(int index);
    
    /**
     * 重置列表数据源，并添加新数据源集合
     * @param elem
     */
    public void replaceAll(List<T> elem);
    
    /**
     * 设置列表项的点击事件
     * @param listener 点击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener);
    
    /**
     * 列表点击事件
     */
    public interface OnItemClickListener<T>
    {
        void onItemClick(View view, T data, int position);
    }
```

#### 需要子类实现的抽象Api
```java
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
     *  绑定VH与数据
     *  @param holder       VH对象
     * @param position      VH在RecyclerView中的位置（有Header或Footer时并不表示数据源的索引）
     * @param index         数据真实索引（为了安全，子类取数据源的数据，索引都用这个值）
     * @param isScrolling   标记是否是滚动中
     */
    protected abstract void onBindItemViewHolder(VH holder, int position, int index,
                                                 boolean isScrolling);
```

### 需要注意的坑
ViewHolder的布局文件，最顶级的容器height必须设置为**android:layout_height="wrap_content"**，如果是设为**match_parent**，在support-v23.3.0版本中，会有不可思议的BUG，通过断点RecyclerView的代码才定位问题原因并解决的。总之一句话，巨坑，慎重！！！上一个示例：
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="wrap_content">

    <TextView
        android:id="@+id/titleView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="8dp"
        android:textColor="@color/colorWhite"
        android:background="@color/colorPrimary"
        android:padding="24dp"
        android:gravity="center"/>

</LinearLayout>
```

### USAGE
在build.gradle里添加如下依赖：
```groovy
compile 'com.m4399:recycleradapter:1.0.0.1'
```

具体使用方法可以查看项目Demo源代码。欢迎吐槽抛砖~

###LICENSE

    Copyright 2016 The RecyclerQuickAdapter authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
