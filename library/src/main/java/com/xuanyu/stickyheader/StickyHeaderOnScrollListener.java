/*
 *
 *  @date 创建时间 10/1/20 7:45 AM
 *  @author jiangbin
 *  @email 549974268@qq.com
 *  @description
 *
 */

package com.xuanyu.stickyheader;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.VISIBLE;

public class StickyHeaderOnScrollListener<T> extends RecyclerView.OnScrollListener {
    private int mStickyHeaderLayoutTop;
    private ViewGroup mStickyHeaderLayout;
    private RecyclerView mRecyclerView;
    private StickyHeaderAdapter<T> mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private StickyHeaderNode<T> mCurrentStickyHeaderNode;// 当前吸顶的Node
    private boolean mIsNeighbour = false;
    private long mDelayTime = 300L;
    private boolean mOn = true;
    private String tag = getClass().getSimpleName();


    public StickyHeaderOnScrollListener(RecyclerView recyclerView, ViewGroup stickyHeaderLayout, int headViewTop) {
        this.mStickyHeaderLayoutTop = headViewTop;
        this.mStickyHeaderLayout = stickyHeaderLayout;
        this.mRecyclerView = recyclerView;
        mAdapter = (StickyHeaderAdapter<T>) mRecyclerView.getAdapter();
        mDelayTime = 250;
        mRecyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                System.out.println(tag + "onChanged() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                System.out.println(tag + "onItemRangeChanged() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                System.out.println(tag + "onItemRangeChanged() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
                super.onItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                System.out.println(tag + "onItemRangeInserted() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
                super.onItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                System.out.println(tag + "onItemRangeRemoved() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
                super.onItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                System.out.println(tag + "onItemRangeMoved() " + mRecyclerView.getScrollX());
                if (mOn) {
                    mHandler.postDelayed(rebuildStickyHeaderRunnable, mDelayTime);
                }
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        });

    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("jiangbinpage", "onScrollStateChanged " + newState);
    }

    //    private boolean mChanged = false;
    private Runnable rebuildStickyHeaderRunnable = new Runnable() {
        @Override
        public void run() {
            rebuildStickyHeader();
        }
    };


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!mOn) return;
        Log.d(tag + " onScrolled", "onScrolled dx " + dx + "  dy " + dy);
        stickyHeader(recyclerView, dx, dy);
    }


    private void stickyHeader(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {//向上滑动
            stickyHeaderTop(recyclerView, dy);
        } else {
            stickyHeaderBottom(recyclerView, dy);
        }
    }

    private void stickyHeaderBottom(RecyclerView recyclerView, int dy) {
        //向下滑动
        if (mCurrentStickyHeaderNode == null) {
            mIsNeighbour = false;
            return;
        }
        BaseStickyHeaderModel<T> nextStickyHeaderModel = findNextStickyHeaderModel(recyclerView);
        if (nextStickyHeaderModel != null && mIsNeighbour && (nextStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() >= mStickyHeaderLayoutTop && nextStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() <= (mStickyHeaderLayoutTop + mStickyHeaderLayout.getHeight() - dy))) {
            int wannaTop = Math.min(mStickyHeaderLayoutTop, nextStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() - mStickyHeaderLayout.getHeight());

            ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, wannaTop - mStickyHeaderLayout.getTop());//offset为正 往下平移

            StickyHeaderModelPool.recycle(nextStickyHeaderModel);
        } else {
            if (nextStickyHeaderModel != null) {
                StickyHeaderModelPool.recycle(nextStickyHeaderModel);
            }
            BaseStickyHeaderModel<T> currentStickyHeaderModel = findCurrentStickyHeaderModel(recyclerView);

            if (currentStickyHeaderModel == null) {
                return;
            }
            mIsNeighbour = false;
            if (currentStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() > mStickyHeaderLayoutTop) {
                if (mCurrentStickyHeaderNode.getPrevNode() == null) {
                    mStickyHeaderLayout.setVisibility(View.GONE);

                } else {
                    mStickyHeaderLayout.setVisibility(VISIBLE);

                    if (mStickyHeaderLayout.getChildAt(0) != StickyHeaderRegistry.getView(mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().getClass())) {
                        mStickyHeaderLayout.removeAllViews();
                        mStickyHeaderLayout.addView((View) mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().createIfAbsent(recyclerView.getContext(),mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().getClass()));
                    }
                    ///11111
                    ((IStickyHeaderView<T>) mStickyHeaderLayout.getChildAt(0)).setData(mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().getRecyclerViewItemModel());
                }
                StickyHeaderNode<T> prevTemp = mCurrentStickyHeaderNode.getPrevNode();
                mCurrentStickyHeaderNode.setPrevNode(null);
                StickyHeaderModelPool.recycle(mCurrentStickyHeaderNode.getStickyHeaderModel());
                mCurrentStickyHeaderNode = prevTemp;

            }
            StickyHeaderModelPool.recycle(currentStickyHeaderModel);
        }
    }

    /**
     * 向上滑动RecyclerView 吸顶
     *
     * @param recyclerView
     * @param dy
     */
    private void stickyHeaderTop(RecyclerView recyclerView, int dy) {
        mIsNeighbour = false;
        BaseStickyHeaderModel<T> nextStickyHeaderModel = findNextStickyHeaderModel(recyclerView);
        if (nextStickyHeaderModel == null) return;

        if (nextStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() < mStickyHeaderLayoutTop) {
            if (mCurrentStickyHeaderNode == null) {
                //如果当前没有吸顶的View。直接设置可见，并add到viewGroup中
                mStickyHeaderLayout.setVisibility(VISIBLE);
                if (mStickyHeaderLayout.getChildCount() == 0) {
                    mStickyHeaderLayout.addView((View) nextStickyHeaderModel.createIfAbsent(recyclerView.getContext(),nextStickyHeaderModel.getRecyclerViewItemModel().getClass()));
                } else {
                    if (mStickyHeaderLayout.getChildAt(0) != StickyHeaderRegistry.getView(nextStickyHeaderModel.getClass())) {
                        mStickyHeaderLayout.removeAllViews();
                        mStickyHeaderLayout.addView((View) nextStickyHeaderModel.createIfAbsent(recyclerView.getContext(),nextStickyHeaderModel.getRecyclerViewItemModel().getClass()));
                    }
                }
            } else {
                if (mStickyHeaderLayout.getChildAt(0) != StickyHeaderRegistry.getView(nextStickyHeaderModel.getClass())) {
                    mStickyHeaderLayout.removeAllViews();
                    mStickyHeaderLayout.addView((View) nextStickyHeaderModel.createIfAbsent(recyclerView.getContext(),nextStickyHeaderModel.getRecyclerViewItemModel().getClass()));
                }
                //如果当前有吸顶的View。将mStickyHeaderLayout重置回来
                ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, mStickyHeaderLayoutTop - mStickyHeaderLayout.getTop());//offset为正 往下平移
            }
            ((IStickyHeaderView<T>) mStickyHeaderLayout.getChildAt(0)).setData(nextStickyHeaderModel.getRecyclerViewItemModel());
            StickyHeaderNode<T> stickyHeaderNode = new StickyHeaderNode();
            stickyHeaderNode.setStickyHeaderModel(nextStickyHeaderModel);
            stickyHeaderNode.setPrevNode(mCurrentStickyHeaderNode);
            mCurrentStickyHeaderNode = stickyHeaderNode;
        } else if (nextStickyHeaderModel.getRecyclerVIewItem().getItemViewTop() < (mStickyHeaderLayoutTop + mStickyHeaderLayout.getHeight())) {
            //紧相邻
            int offset = Math.min(Math.max(mStickyHeaderLayout.getBottom() - mStickyHeaderLayoutTop, 0), dy);
            ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, -offset);//为负数 往上走
            mIsNeighbour = true;
            StickyHeaderModelPool.recycle(nextStickyHeaderModel);
        } else {
            StickyHeaderModelPool.recycle(nextStickyHeaderModel);
        }
    }

    /**
     * 寻找当前的吸顶View
     *
     * @param recyclerView
     * @return
     */
    private BaseStickyHeaderModel<T> findCurrentStickyHeaderModel(RecyclerView recyclerView) {
        if (mCurrentStickyHeaderNode == null) return null;
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);

            int adapterPosition = recyclerView.getChildAdapterPosition(child);
            if (adapterPosition == RecyclerView.NO_POSITION) {
                continue;
            }
            StickyHeaderAdapter<T> adapter = ((StickyHeaderAdapter<T>) recyclerView.getAdapter());
            BaseStickyHeaderModel<T> stickyHeaderModel = adapter.transferToStickyHeaderModel(adapterPosition);
            if (stickyHeaderModel == null) {
                continue;
            }
            stickyHeaderModel.setRecyclerViewItemView(((IStickyHeaderView) child));

            T recyclerViewItemModel = adapter.getItem(adapterPosition);

            if (mCurrentStickyHeaderNode.getStickyHeaderModel().getRecyclerViewItemModel().equals(recyclerViewItemModel)) {
                return stickyHeaderModel;
            } else {
                StickyHeaderModelPool.recycle(stickyHeaderModel);
            }

        }
        return null;
    }

    /**
     * 寻找下一个可能吸顶的View
     *
     * @param recyclerView
     * @return
     */
    private BaseStickyHeaderModel<T> findNextStickyHeaderModel(RecyclerView recyclerView) {
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            int adapterPosition = recyclerView.getChildAdapterPosition(child);
            if (adapterPosition == RecyclerView.NO_POSITION) {
                continue;
            }
            StickyHeaderAdapter<T> stickyHeaderAdapter = (StickyHeaderAdapter<T>) recyclerView.getAdapter();
            BaseStickyHeaderModel<T> stickyHeaderModel = stickyHeaderAdapter.transferToStickyHeaderModel(adapterPosition);
            if (stickyHeaderModel == null) {
                continue;
            }
            if (!(child instanceof IStickyHeaderView)) {
                throw new IllegalStateException(child.getClass().getName() + " 必须实现IStickyHeaderView 接口");
            }
            stickyHeaderModel.setRecyclerViewItemView(((IStickyHeaderView) child));
            T recyclerViewItemModel = stickyHeaderAdapter.getItem(adapterPosition);
            if (mCurrentStickyHeaderNode == null || !mCurrentStickyHeaderNode.getStickyHeaderModel().getRecyclerViewItemModel().equals(recyclerViewItemModel)) {
                return stickyHeaderModel;
            } else {
                StickyHeaderModelPool.recycle(stickyHeaderModel);
            }


        }
        return null;
    }

    public View findChildViewUnder(RecyclerView recyclerView, float x, float y) {
        final int count = recyclerView.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = recyclerView.getChildAt(i);
            final float translationX = child.getTranslationX();
            final float translationY = child.getTranslationY();
            if (x >= child.getLeft() + translationX
                    && x <= child.getRight() + translationX
                    && y >= child.getTop() + translationY
                    && y <= child.getBottom() + translationY) {
                return child;
            }
        }
        return null;
    }


    private void rebuildStickyHeader() {
        if (!mOn) return;

        //todo mRecyclerView.getMeasuredWidth() / 4 应该更优雅一点
        View view = findChildViewUnder(mRecyclerView, mRecyclerView.getMeasuredWidth() / 4, mStickyHeaderLayoutTop);
        if (view == null) {
            System.out.println("rebuildStickyHeader method view == null");
            return;
        }

        int position = mRecyclerView.getChildAdapterPosition(view);
        System.out.println("rebuildStickyHeader position = " + position);
        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        int itemCount = ((RecyclerView.Adapter) mAdapter).getItemCount();
        System.out.println("rebuildStickyHeader itemCount = " + itemCount);

        BaseStickyHeaderModel<T> stickyHeaderModel = null;
        for (int i = position; i >= 0; i--) {
            stickyHeaderModel = StickyHeaderHelper.transferToStickyHeaderModel(mAdapter, i);
            if (stickyHeaderModel != null) break;
        }
        resetStickyHeader();
        if (stickyHeaderModel == null) {
            //如果当前没有吸顶
            mStickyHeaderLayout.setVisibility(View.GONE);
            return;
        }
        if (mStickyHeaderLayout.getVisibility() == View.GONE) {
            mStickyHeaderLayout.setVisibility(VISIBLE);
        }
        ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, mStickyHeaderLayoutTop - mStickyHeaderLayout.getTop());//offset为正 往下平移
        if (mStickyHeaderLayout.getChildCount() == 0) {
            mStickyHeaderLayout.addView((View) stickyHeaderModel.createIfAbsent(mRecyclerView.getContext(),stickyHeaderModel.getRecyclerViewItemModel().getClass()));
        } else {
            if (mStickyHeaderLayout.getChildAt(0) != StickyHeaderRegistry.getView(stickyHeaderModel.getClass())) {
                mStickyHeaderLayout.removeAllViews();
                mStickyHeaderLayout.addView((View) stickyHeaderModel.createIfAbsent(mRecyclerView.getContext(),stickyHeaderModel.getRecyclerViewItemModel().getClass()));
            }
            ((IStickyHeaderView<T>) mStickyHeaderLayout.getChildAt(0)).setData(stickyHeaderModel.getRecyclerViewItemModel());

        }
        StickyHeaderModelPool.recycle(stickyHeaderModel);
        rebuildStickyHeadNode(position);
        System.out.println("rebuildStickyHeader method position = " + position);


    }

    /**
     * 重新设置吸顶状态
     */
    public void resetStickyHeader() {
        resetStickyHeaderNode();
        mIsNeighbour = false;
    }

    public void resetStickyHeaderNode() {
        while (mCurrentStickyHeaderNode != null) {
            BaseStickyHeaderModel<T> stickyHeaderModel = mCurrentStickyHeaderNode.getStickyHeaderModel();
            mCurrentStickyHeaderNode.setStickyHeaderModel(null);
            System.out.println("StickyHeaderModelPool -- recycle");
            StickyHeaderModelPool.recycle(stickyHeaderModel);
            StickyHeaderNode<T> prevNode = mCurrentStickyHeaderNode.getPrevNode();
            mCurrentStickyHeaderNode.setPrevNode(null);
            mCurrentStickyHeaderNode = prevNode;
        }
    }

    public void rebuildStickyHeadNode(int position) {
        StickyHeaderNode<T> nextNode = null;
        for (int index = position; index >= 0; index--) {
            BaseStickyHeaderModel<T> stickyHeaderModel = StickyHeaderHelper.transferToStickyHeaderModel(mAdapter, index);
            if (stickyHeaderModel == null) continue;
            StickyHeaderNode<T> stickyHeaderNode = new StickyHeaderNode();
            stickyHeaderNode.setStickyHeaderModel(stickyHeaderModel);
            if (mCurrentStickyHeaderNode == null) {
                mCurrentStickyHeaderNode = stickyHeaderNode;
            }
            if (nextNode != null) {
                nextNode.setPrevNode(stickyHeaderNode);
            }
            nextNode = stickyHeaderNode;
        }
    }

    public void turnStickyHeader(boolean on) {
        mOn = on;
        if (on) {
            rebuildStickyHeader();
        } else {
            resetStickyHeader();
            mStickyHeaderLayout.setVisibility(View.GONE);
        }
    }
}
