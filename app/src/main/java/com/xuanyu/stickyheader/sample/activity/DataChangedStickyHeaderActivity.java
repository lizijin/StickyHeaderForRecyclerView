package com.xuanyu.stickyheader.sample.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanyu.stickyheader.StickyHeaderHelper;
import com.xuanyu.stickyheader.StickyHeaderRegistry;
import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.adapter.NamingStickyHeaderAdapter;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.bean.Naming;
import com.xuanyu.stickyheader.sample.bean.Person;
import com.xuanyu.stickyheader.sample.stickymodel.BookStickyHeaderModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataChangedStickyHeaderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FrameLayout mHeaderLayout;
    private NamingStickyHeaderAdapter mAdapter;
    private List<Naming> models = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_changed_sticky_header);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                StickyHeaderHelper.rebuildStickyHeader(mRecyclerView);

            }
        });
        mHeaderLayout = findViewById(R.id.header_layout);
        mRecyclerView.getItemAnimator().setAddDuration(1000);
        mRecyclerView.getItemAnimator().setRemoveDuration(1000);
        mRecyclerView.getItemAnimator().setChangeDuration(1000);


        models.addAll(getCharacters(R.array.feihuwaizhuan, "飞狐外传"));
        models.addAll(getCharacters(R.array.xueshangfeihu, "雪山飞狐"));
        models.addAll(getCharacters(R.array.lianchengjue, "连城诀"));
        models.addAll(getCharacters(R.array.tianlongbabu, "天龙八部"));
        models.addAll(getCharacters(R.array.shediaoyingxiong, "射雕英雄传"));
        models.addAll(getCharacters(R.array.baimaxiaoxifeng, "白马啸西风"));
        models.addAll(getCharacters(R.array.ludingji, "鹿鼎记"));
        models.add(new Book("反转后的Item"));
        mAdapter = new NamingStickyHeaderAdapter(models);
        mRecyclerView.setAdapter(mAdapter);

        StickyHeaderHelper.init(mRecyclerView, mHeaderLayout, 0);
        StickyHeaderRegistry.registerTransfer(Book.class, BookStickyHeaderModel.class);


    }

    public void turnOn(View view) {
        StickyHeaderHelper.turnStickyHeader(true, mRecyclerView);
    }

    public void turnOff(View view) {
        StickyHeaderHelper.turnStickyHeader(false, mRecyclerView);

    }


    private List<Naming> getCharacters(int arrayId, String bookName) {
        List<Naming> namings = new ArrayList<>();
        namings.add(new Book(bookName));
        String[] names = getResources().getStringArray(arrayId);
        for (String name : names) {
            namings.add(new Person(name));
        }
        return namings;
    }

    public void reverse(View view) {
        Collections.reverse(models);
        mAdapter.notifyDataSetChanged();
    }

    public void removeDiff(View view) {
        models.remove(0);
        mAdapter.notifyItemRemoved(0);
    }

    public void addDiff(View view) {
        models.add(1, new Book("AddDiff Item"));
        mAdapter.notifyItemInserted(1);
    }

    public void updateDiff(View view) {
        Naming model = models.get(0);
        if (model instanceof Book) {
            ((Book) model).name = "Update Diff Item";
        } else {
            Toast.makeText(this, "请调整第一个为书名", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyItemChanged(0);
    }
}