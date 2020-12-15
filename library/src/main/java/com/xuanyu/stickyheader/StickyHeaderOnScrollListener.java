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
    private int topOffset = 0;
    private int downOffset = 0;


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
        //todo 下滑时查找下一个吸顶的可以通过链表查找
        BaseStickyHeaderModel<T> nextStickyHeaderModel = findNextStickyHeaderModel(recyclerView, false);

        View recyclerViewItemView = null;
        if (nextStickyHeaderModel != null && ((recyclerViewItemView = nextStickyHeaderModel.getRecyclerViewItemView()) != null) && mIsNeighbour && (getItemViewTop(recyclerViewItemView) >= mStickyHeaderLayoutTop && getItemViewTop(recyclerViewItemView) <= (mStickyHeaderLayoutTop + mStickyHeaderLayout.getHeight() - dy))) {
            //紧相连状态
            System.out.println("jiangbin stickyHeaderBottom 11111");
            int wannaTop = Math.min(mStickyHeaderLayoutTop, getItemViewTop(recyclerViewItemView) - mStickyHeaderLayout.getHeight()) + downOffset;

            ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, wannaTop - mStickyHeaderLayout.getTop());//offset为正 往下平移

            StickyHeaderModelPool.recycle(nextStickyHeaderModel);
        } else {
            //不是紧相连状态
            System.out.println("jiangbin stickyHeaderBottom 2222");

            if (nextStickyHeaderModel != null) {//往下滑动，下一个没用，直接回收掉
                StickyHeaderModelPool.recycle(nextStickyHeaderModel);
            }
            BaseStickyHeaderModel<T> currentStickyHeaderModel = findCurrentStickyHeaderModel(recyclerView);

            if (currentStickyHeaderModel == null) {
                return;
            }
            mIsNeighbour = false;

            //当前吸顶View 不吸顶了
            if (getItemViewTop(currentStickyHeaderModel.getRecyclerViewItemView()) > mStickyHeaderLayoutTop) {
                if (mCurrentStickyHeaderNode.getPrevNode() == null) {
                    //前面没有吸顶的View了
                    mStickyHeaderLayout.setVisibility(View.GONE);
                } else {
                    System.out.println("jiangbin stickyHeaderBottom 33333");
                    mIsNeighbour = true;
//                    mStickyHeaderLayout.setVisibility(VISIBLE);
                    View newStickyView = mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().createIfAbsent(mRecyclerView, mRecyclerView.getContext());
                    addStickyView(newStickyView);
                    mStickyHeaderLayout.setAlpha(0);
                    newStickyView.post(new Runnable() {
                        @Override
                        public void run() {
                            mStickyHeaderLayout.setAlpha(1);
                            ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, mStickyHeaderLayoutTop - mStickyHeaderLayout.getMeasuredHeight() - mStickyHeaderLayout.getTop());
                        }
                    });
                    ///11111
                    mCurrentStickyHeaderNode.getPrevNode().getStickyHeaderModel().onBindView();
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
        BaseStickyHeaderModel<T> nextStickyHeaderModel = findNextStickyHeaderModel(recyclerView, true);
        if (nextStickyHeaderModel == null) return;

        //下一个可能需要吸顶的View 进入吸顶状态
        View nextStickyItemView = nextStickyHeaderModel.getRecyclerViewItemView();
        if (getItemViewTop(nextStickyItemView) < mStickyHeaderLayoutTop) {

            T recyclerViewItemModel = nextStickyHeaderModel.getRecyclerViewItemModel();

            //吸顶View对应的数据类的Class
            Class<?> recyclerViewItemModelClazz = recyclerViewItemModel.getClass();
            View stickyHeaderView = nextStickyHeaderModel.createIfAbsent(mRecyclerView, recyclerView.getContext());

            addStickyView(stickyHeaderView);
            if (mCurrentStickyHeaderNode == null) {
                //如果当前没有吸顶的View。直接设置可见，并add到viewGroup中
                mStickyHeaderLayout.setVisibility(VISIBLE);
            } else {
                //如果当前有吸顶的View。将mStickyHeaderLayout重置回来
                ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, mStickyHeaderLayoutTop - mStickyHeaderLayout.getTop());//offset为正 往下平移
            }
            nextStickyHeaderModel.onBindView();
            StickyHeaderNode<T> stickyHeaderNode = new StickyHeaderNode();
            stickyHeaderNode.setStickyHeaderModel(nextStickyHeaderModel);
            stickyHeaderNode.setPrevNode(mCurrentStickyHeaderNode);
            mCurrentStickyHeaderNode = stickyHeaderNode;
        } else if (getItemViewTop(nextStickyItemView) <= (mStickyHeaderLayoutTop + mStickyHeaderLayout.getHeight() + topOffset)) {
            //紧相邻

            int wannaTop = getItemViewTop(nextStickyItemView) - mStickyHeaderLayout.getHeight() - topOffset;

            ViewCompat.offsetTopAndBottom(mStickyHeaderLayout, wannaTop - mStickyHeaderLayout.getTop());
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
            stickyHeaderModel.setRecyclerViewItemView(child);

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
     * @param slideUp      为true 表示向上滑，false表示向下滑
     * @return
     */
    private BaseStickyHeaderModel<T> findNextStickyHeaderModel(RecyclerView recyclerView, boolean slideUp) {
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

            stickyHeaderModel.setRecyclerViewItemView(child);
            T recyclerViewItemModel = stickyHeaderAdapter.getItem(adapterPosition);
            if (slideUp) {
                //向上滑 找到一个非当前吸顶的 就返回
                if (mCurrentStickyHeaderNode == null || !mCurrentStickyHeaderNode.getStickyHeaderModel().getRecyclerViewItemModel().equals(recyclerViewItemModel)) {
                    return stickyHeaderModel;
                } else {
                    StickyHeaderModelPool.recycle(stickyHeaderModel);
                    return null;
                }
            } else {
                //当前不吸顶或者找到当前吸顶的就返回null

                if (mCurrentStickyHeaderNode == null || mCurrentStickyHeaderNode.getStickyHeaderModel().getRecyclerViewItemModel().equals(recyclerViewItemModel)) {
                    StickyHeaderModelPool.recycle(stickyHeaderModel);
                    return null;
                }
                return stickyHeaderModel;
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
            mStickyHeaderLayout.addView(stickyHeaderModel.createIfAbsent(mRecyclerView, mRecyclerView.getContext()));
        } else {
            if (mStickyHeaderLayout.getChildAt(0) != StickyHeaderRegistry.getView(mRecyclerView, stickyHeaderModel.getRecyclerViewItemModel().getClass())) {
                mStickyHeaderLayout.removeAllViews();
                mStickyHeaderLayout.addView(stickyHeaderModel.createIfAbsent(mRecyclerView, mRecyclerView.getContext()));
            }
            stickyHeaderModel.onBindView();

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

    private int getItemViewTop(View view) {
        if (view instanceof IStickyHeaderView) {
            return ((IStickyHeaderView) view).getItemViewTop();
        } else {
            return view.getTop();
        }
    }

    private boolean stickyLayoutContains(View view) {
        for (int i = 0; i < mStickyHeaderLayout.getChildCount(); i++) {
            View child = mStickyHeaderLayout.getChildAt(i);
            if (child == view) return true;
        }
        return false;
    }

    private void onlyShowStickyView(View view) {
        for (int i = 0; i < mStickyHeaderLayout.getChildCount(); i++) {
            View child = mStickyHeaderLayout.getChildAt(i);
            if (child == view) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(View.GONE);
            }
        }
    }

    private void addStickyView(View view) {
        if (!stickyLayoutContains(view)) {
            mStickyHeaderLayout.addView(view);
        }
        onlyShowStickyView(view);
    }


}
