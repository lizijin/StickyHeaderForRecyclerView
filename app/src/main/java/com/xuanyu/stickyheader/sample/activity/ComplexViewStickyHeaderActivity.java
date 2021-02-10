package com.xuanyu.stickyheader.sample.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

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
import com.xuanyu.stickyheader.sample.stickymodel.ComplexViewBookStickyHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class ComplexViewStickyHeaderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FrameLayout mHeaderLayout;
    private NamingStickyHeaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_view_sticky_header);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                StickyHeaderHelper.rebuildStickyHeader(mRecyclerView);

            }
        });
        mRecyclerView.setItemAnimator(null);
        mHeaderLayout = findViewById(R.id.header_layout);

        List<Naming> models = new ArrayList<>();
        models.addAll(getCharacters(R.array.feihuwaizhuan, "飞狐外传", R.drawable.feihuwaizhuan));
        models.addAll(getCharacters(R.array.xueshangfeihu, "雪山飞狐", R.drawable.xueshanfeihu));
        models.addAll(getCharacters(R.array.lianchengjue, "连城诀", R.drawable.lianchengjue));
        models.addAll(getCharacters(R.array.tianlongbabu, "天龙八部", R.drawable.tianlongbabu));
        models.addAll(getCharacters(R.array.shediaoyingxiong, "射雕英雄传", R.drawable.shediaoyingxiongzhuan));
        models.addAll(getCharacters(R.array.baimaxiaoxifeng, "白马啸西风", R.drawable.baimaxiaoxifeng));
        models.addAll(getCharacters(R.array.ludingji, "鹿鼎记", R.drawable.ludingji));
        mAdapter = new NamingStickyHeaderAdapter(models);
        mRecyclerView.setAdapter(mAdapter);

        StickyHeaderHelper.init(mRecyclerView, mHeaderLayout, 0);
        StickyHeaderRegistry.registerTransfer(Book.class, ComplexViewBookStickyHeaderModel.class);


    }

    public void turnOn(View view) {
        StickyHeaderHelper.turnStickyHeader(true, mRecyclerView);
    }

    public void turnOff(View view) {
        StickyHeaderHelper.turnStickyHeader(false, mRecyclerView);

    }


    private List<Naming> getCharacters(int arrayId, String bookName, int image) {
        List<Naming> namings = new ArrayList<>();
        namings.add(new Book(bookName, image));
        String[] names = getResources().getStringArray(arrayId);
        for (String name : names) {
            namings.add(new Person(name));
        }
        return namings;
    }
}