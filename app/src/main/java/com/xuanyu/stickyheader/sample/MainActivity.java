package com.xuanyu.stickyheader.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.StickyHeaderAdapter;
import com.xuanyu.stickyheader.StickyHeaderHelper;
import com.xuanyu.stickyheader.StickyHeaderRegistry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FrameLayout mHeaderLayout;
    private TextAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHeaderLayout = findViewById(R.id.header_layout);


        List<TextModel> models = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                models.add(new HeaderStringModel("Header Item " + i));

            }
            models.add(new NormalStringModel("Normal Item " + i));

        }
        mRecyclerView.setAdapter(new TextAdapter(models));

        StickyHeaderHelper.init(mRecyclerView, 0, mHeaderLayout);
        StickyHeaderRegistry.registerTransfer(HeaderStringModel.class, StickyModel.class);

    }

    private static class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderAdapter<TextModel> {
        private List<TextModel> mTextModels;
        public static final int NORMAL = 0;
        public static final int HEAD = 1;

        public TextAdapter(List<TextModel> textModels) {
            this.mTextModels = textModels;
        }

        @Override
        public TextModel getItem(int position) {
            return mTextModels.get(position);
        }

        @Override
        public BaseStickyHeaderModel<TextModel> transferToStickyHeaderModel(int position) {
            return StickyHeaderHelper.transferToStickyHeaderModel(this, position);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            if (viewType == NORMAL) {
                View view = new NormalView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new TextViewHolder(view);
            } else {
                View view = new HeaderView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                return new HeaderViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TextViewHolder) {
                ((NormalView) holder.itemView).mTextView.setText(mTextModels.get(position).getText());
            } else {
                ((HeaderView) holder.itemView).setData((HeaderStringModel) mTextModels.get(position));
            }
        }

        @Override
        public int getItemViewType(int position) {
            TextModel textModel = mTextModels.get(position);
            if (textModel instanceof NormalStringModel) return NORMAL;
            return HEAD;
        }

        @Override
        public int getItemCount() {
            return mTextModels.size();
        }
    }

    private static class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}