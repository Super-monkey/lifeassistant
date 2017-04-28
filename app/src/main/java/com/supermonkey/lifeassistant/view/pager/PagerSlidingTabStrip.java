/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.supermonkey.lifeassistant.view.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supermonkey.lifeassistant.R;

import java.util.Locale;


public class PagerSlidingTabStrip extends HorizontalScrollView {

    /***************
     * 定义的一个接口
     ******************/

    public interface IconTabProvider {
        /**
         * 根据postion获取图标资源的ID
         *
         * @param position
         * @return
         */
        public int getPageIconResId(int position);
    }

    /*************************
     * 定义本地变量
     *****************************/

    // 使用系统属性的字体大小和字体颜色
    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    // 默认的布局
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    // 充满屏幕布局
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    // 实例化PagerListener,实现了ViewPager页面滚动的监听事件
    private final PageListener pageListener = new PageListener();
    // 委托给ViewPager默认的PagerListener
    public OnPageChangeListener delegatePageListener;

    // 定义一个线性布局，放置tab
    private LinearLayout tabsContainer;
    // 定义一个ViewPager绑定在当前控件
    private ViewPager pager;

    // tab的数量
    private int tabCount;

    // 显示tab的编号
    private int currentPosition = 0;
    // 选中tab的编号
    private int selectedPosition = 0;
    // 当前位置的偏移量
    private float currentPositionOffset = 0f;

    // 矩形画笔
    private Paint rectPaint;
    // 分割线画笔
    private Paint dividerPaint;

    // 指示器颜色
    private int indicatorColor = 0xFF666666;
    // tab线的颜色
    private int underlineColor = 0x1A000000;
    // 分割线颜色
    private int dividerColor = 0x1A000000;

    // 自动充满屏幕
    private boolean shouldExpand = false;
    // 全部大写
    private boolean textAllCaps = true;

    // 默认偏移量
    private int scrollOffset = 52;
    // 默认指示器的高度
    private int indicatorHeight = 8;
    // 默认底部/顶部的高度
    private int underlineHeight = 2;
    // 默认分割线外边距
    private int dividerPadding = 12;
    // 默认tab的外边距
    private int tabPadding = 24;
    // 分割线宽度
    private int dividerWidth = 1;

    // tab文字大小
    private int tabTextSize = 12;
    // tab文字颜色
    private int tabTextColor = 0xFF666666;
    // 被选中文字颜色
    private int selectedTabTextColor = 0xFF666666;
    // 默认外观样式
    private Typeface tabTypeface = null;
    // 字体
    private int tabTypefaceStyle = Typeface.NORMAL;

    // tab对于父控件左上角的偏移量
    private int lastScrollX = 0;

    // tab的背景
    private int tabBackgroundResId = R.drawable.pager_background_tab;

    // 本地配置
    private Locale locale;

    // Underline和Indicator布局在控件的上面或者下面
    private String controlStyle;

    /*********************
     * 构造函数，获取在布局文件中设置的属性
     ***********************/

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 本方法是使子View可以拉伸来填满整个屏幕
        setFillViewport(true);
        // 解决onDraw方法不被执行
        setWillNotDraw(false);

        // 实例化tab容器
        tabsContainer = new LinearLayout(context);
        // tab设置方向为水平
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        // 设置tab布局（宽度macth_parent,高度match_parent）
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // 在HorizontalScrollView中添加tab
        addView(tabsContainer);

        // 获取屏幕尺寸
        DisplayMetrics dm = getResources().getDisplayMetrics();

        //将整形换算为dip的单位
        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)
        // 获取系统字体和颜色
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = a.getColor(1, tabTextColor);

        // 垃圾回收
        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);

        // 获取指示器颜色
        indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
        // 获取底部/顶部线的颜色
        underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
        // 分割线的颜色
        dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
        // 选中字体的颜色
        selectedTabTextColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstSelectedTextColor,
                selectedTabTextColor);
        // 获取指示器高度
        indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
        // 获取底部/顶部线高度
        underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
        // 获取分割线的间隔
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
        // 获取tab间的间隔
        tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
        // tab的背景颜色
        tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
        // 获取是否充满屏幕
        shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
        // 获取滑动的偏移量
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
        // 获取是否全部大写
        textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);
        // 获取底部/顶部线位置
        controlStyle = a
                .getString(R.styleable.PagerSlidingTabStrip_pstsControlStyle);

        a.recycle();

        // 实例化矩形画笔
        rectPaint = new Paint();
        // 设置画笔的锯齿效果
        rectPaint.setAntiAlias(true);
        // 填充形式
        rectPaint.setStyle(Style.FILL);

        // 分割线画笔
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        // 默认布局
        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        // 扩展布局
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        // 获取本地配置
        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    /**
     * 关联ViewPager
     *
     * @param pager
     */
    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    /**
     * pager改变监听事件
     *
     * @param listener
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {

        // 移除所有View
        tabsContainer.removeAllViews();

        // 获取pager数量
        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }

        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });

    }

    /**
     * 添加tab标题
     *
     * @param position
     * @param title
     */
    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();

        addTab(position, tab);
    }

    /**
     * 添加tab图标
     *
     * @param position
     * @param resId
     */
    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    /**
     * 添加tab
     *
     * @param position
     * @param tab
     */
    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position);
            }
        });

        tab.setPadding(tabPadding, 0, tabPadding, 0);
        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    /**
     * 更新tab样式
     */
    private void updateTabStyles() {

        // 循环tab
        for (int i = 0; i < tabCount; i++) {
            // 获取子元素
            View v = tabsContainer.getChildAt(i);
            // 设置背景
            v.setBackgroundResource(tabBackgroundResId);
            // 如果子元素是TextView
            if (v instanceof TextView) {
                // 强制转转为TextView
                TextView tab = (TextView) v;
                // 设置字体
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                // 设置字体样式
                tab.setTypeface(tabTypeface, tabTypefaceStyle);
                // 设置颜色
                tab.setTextColor(tabTextColor);

                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                // pre-ICS-build
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }

    }

    /**
     * 滑动
     *
     * @param position
     * @param offset
     */
    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    /**
     * 重写onDraw方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // isEditMode()解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode() || tabCount == 0) {
            return;
        }
        // 返回控件的高度
        final int height = getHeight();

        // draw indicator line
        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        // currentPosition从0开始
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }

        canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);

        // draw underline
        rectPaint.setColor(underlineColor);
        // 画底部/顶部线的位置，画在底部
        // 底部
        /*
         * canvas.drawRect(0, height - underlineHeight,
		 * tabsContainer.getWidth(), height, rectPaint);
		 */
        // 顶部
        canvas.drawRect(0, 0, tabsContainer.getWidth(), underlineHeight,
                rectPaint);

        // draw divider

        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            selectedPosition = position;
            updateTabStyles();
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }

    }

    /**
     * 设置指示器颜色
     *
     * @param indicatorColor
     */
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * 设置指示器颜色，引用资源ID
     *
     * @param resId
     */
    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 获取指示器颜色
     *
     * @return
     */
    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    /**
     * 设置指示器高度
     *
     * @param indicatorLineHeightPx
     */
    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    /**
     * 获取指示器高度
     *
     * @return
     */
    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    /**
     * 设置底部/顶部线颜色
     *
     * @param underlineColor
     */
    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    /**
     * 设置底部/顶部线颜色，引用资源ID
     *
     * @param resId
     */
    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 获取底部顶部线颜色
     *
     * @return
     */
    public int getUnderlineColor() {
        return underlineColor;
    }

    /**
     * 设置分割线颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    /**
     * 设置分割线颜色，引用资源ID
     *
     * @param resId
     */
    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 获取分割线的颜色
     *
     * @return
     */
    public int getDividerColor() {
        return dividerColor;
    }

    /**
     * 设置底部/顶部线的高度
     *
     * @param underlineHeightPx
     */
    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    /**
     * 获取底部/顶部的高度
     *
     * @return
     */
    public int getUnderlineHeight() {
        return underlineHeight;
    }

    /**
     * 设置分割线的边距
     *
     * @param dividerPaddingPx
     */
    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    /**
     * 获取分割线的边距
     *
     * @return
     */
    public int getDividerPadding() {
        return dividerPadding;
    }

    /**
     * 设置滑动偏移量
     *
     * @param scrollOffsetPx
     */
    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    /**
     * 获取滑动偏移量
     *
     * @return
     */
    public int getScrollOffset() {
        return scrollOffset;
    }

    /**
     * 设置是否充满屏幕，true为自动充满，false为不自动充满
     *
     * @param shouldExpand
     */
    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    /**
     * 获取时候自动充满
     *
     * @return
     */
    public boolean getShouldExpand() {
        return shouldExpand;
    }

    /**
     * 获取是否字体全部大写
     *
     * @return
     */
    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    /**
     * 设置字体全部大写
     *
     * @param textAllCaps
     */
    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    /**
     * 设置字体大小
     *
     * @param textSizePx
     */
    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    /**
     * 获取字体大小
     *
     * @return
     */
    public int getTextSize() {
        return tabTextSize;
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    /**
     * 设置字体颜色，引用资源ID
     *
     * @param resId
     */
    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    /**
     * 获取字体颜色
     *
     * @return
     */
    public int getTextColor() {
        return tabTextColor;
    }

    public void setSelectedTextColor(int textColor) {
        this.selectedTabTextColor = textColor;
        updateTabStyles();
    }

    public void setSelectedTextColorResource(int resId) {
        this.selectedTabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getSelectedTextColor() {
        return selectedTabTextColor;
    }

    /**
     * 设置TypeFace
     *
     * @param typeface
     * @param style
     */
    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    /**
     * 设置背景颜色，引用资源ID
     *
     * @param resId
     */
    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    /**
     * 获取资源ID
     *
     * @return
     */
    public int getTabBackground() {
        return tabBackgroundResId;
    }

    /**
     * 设置控件左右边距
     */
    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    /**
     * 获取控件左右边距
     *
     * @return
     */
    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    /**
     * 获取指示器和分割线的位置
     *
     * @return
     */
    public String getUnderLineLocation() {
        return controlStyle;
    }

    /**
     * 设置指示器和分割线位置
     *
     * @param controlstyle
     */
    public void setUnderLineLocation(String controlstyle) {
        this.controlStyle = controlstyle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
