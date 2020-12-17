package com.xuanyu.stickyheader.sample.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.bean.RouteItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<RouteItem> routeItems = new ArrayList<>();
        RouteItem singleStickyHeaderDemo = new RouteItem("单类型吸顶功能", "演示了RecyclerView中只有单个类型的吸顶情况", SingleStickyHeaderActivity.class);
        routeItems.add(singleStickyHeaderDemo);
        RouteItem multiStickyHeaderDemo = new RouteItem("多类型吸顶功能", "演示了RecyclerView中有多类型的吸顶情况", MultiStickyHeaderActivity.class);
        routeItems.add(multiStickyHeaderDemo);
        RouteItem switchStickyHeaderDemo = new RouteItem("开启和关闭吸顶功能", "演示了开启和关闭吸顶功能情况", SwitchStickyHeaderActivity.class);
        routeItems.add(switchStickyHeaderDemo);
        RouteItem positionStickyHeaderDemo = new RouteItem("指定位置吸顶功能", "演示了指定位置吸顶功能情况", PositionStickyHeaderActivity.class);
        routeItems.add(positionStickyHeaderDemo);
        RouteItem offsetStickyHeaderDemo = new RouteItem("设置吸顶偏移量", "演示了设置吸顶偏移量情况", OffsetStickyHeaderActivity.class);
        routeItems.add(offsetStickyHeaderDemo);
        RouteItem customTopStickyHeaderDemo = new RouteItem("吸顶TOP自定义", "演示了吸顶边界自定义情况", CustomTopStickyHeaderActivity.class);
        routeItems.add(customTopStickyHeaderDemo);
        RouteItem appBarLayoutStickyHeaderDemo = new RouteItem("配合AppBarLayout", "演示了配合AppBarLayout使用情况", AppBarLayoutStickyHeaderActivity.class);
        routeItems.add(appBarLayoutStickyHeaderDemo);
        mMainAdapter = new MainAdapter(routeItems);
        mRecyclerView.setAdapter(mMainAdapter);
    }

    private static class MainAdapter extends RecyclerView.Adapter<RouteViewHolder> {
        private List<RouteItem> routeItems;

        public MainAdapter(List<RouteItem> items) {
            this.routeItems = items;
        }

        @NonNull
        @Override
        public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
            return new RouteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
            final RouteItem routeItem = routeItems.get(position);
            holder.mTitleTextView.setText(routeItem.mainTitle);
            holder.mDescriptionTextView.setText(routeItem.subTitle);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), routeItem.activityClazz);
                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return routeItems.size();
        }
    }

    private static class RouteViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.main_title);
            mDescriptionTextView = itemView.findViewById(R.id.main_description);
        }
    }
}