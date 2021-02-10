package com.xuanyu.stickyheader.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xuanyu.stickyheader.StickyHeaderHelper;
import com.xuanyu.stickyheader.StickyHeaderRegistry;
import com.xuanyu.stickyheader.sample.R;
import com.xuanyu.stickyheader.sample.adapter.NamingStickyHeaderAdapter;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.bean.Naming;
import com.xuanyu.stickyheader.sample.bean.Person;
import com.xuanyu.stickyheader.sample.stickymodel.BookStickyHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class SwitchStickyHeaderActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FrameLayout mHeaderLayout;
    private NamingStickyHeaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sticky_header);
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
        findViewById(R.id.layout_switch).setVisibility(View.VISIBLE);
        findViewById(R.id.button_turn_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOn();
            }
        });

        findViewById(R.id.button_turn_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOff();
            }
        });

        List<Naming> models = new ArrayList<>();
        models.addAll(getCharacters(R.array.feihuwaizhuan, "飞狐外传"));
        models.addAll(getCharacters(R.array.xueshangfeihu, "雪山飞狐"));
        models.addAll(getCharacters(R.array.lianchengjue, "连城诀"));
        models.addAll(getCharacters(R.array.tianlongbabu, "天龙八部"));
        models.addAll(getCharacters(R.array.shediaoyingxiong, "射雕英雄传"));
        models.addAll(getCharacters(R.array.baimaxiaoxifeng, "白马啸西风"));
        models.addAll(getCharacters(R.array.ludingji, "鹿鼎记"));
        mAdapter = new NamingStickyHeaderAdapter(models);
        mRecyclerView.setAdapter(mAdapter);

        StickyHeaderHelper.init(mRecyclerView, mHeaderLayout, 0);
        StickyHeaderRegistry.registerTransfer(Book.class, BookStickyHeaderModel.class);


    }

    public void turnOn() {
        StickyHeaderHelper.turnStickyHeader(true, mRecyclerView);
    }

    public void turnOff() {
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
}