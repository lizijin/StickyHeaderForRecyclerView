package com.xuanyu.stickyheader.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.StickyHeaderAdapter;
import com.xuanyu.stickyheader.StickyHeaderHelper;
import com.xuanyu.stickyheader.StickyHeaderRegistry;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.bean.AmericanNation;
import com.xuanyu.stickyheader.sample.bean.Person;
import com.xuanyu.stickyheader.sample.bean.Naming;

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
        mRecyclerView.setItemAnimator(null);
        mHeaderLayout = findViewById(R.id.header_layout);

        List<Naming> models = new ArrayList<>();
        models.addAll(getCharacters(R.array.feihuwaizhuan, "飞狐外传"));
        models.addAll(getCharacters(R.array.xueshangfeihu, "雪山飞狐"));
        models.addAll(getCharacters(R.array.lianchengjue, "连城诀"));
        models.addAll(getCharacters(R.array.tianlongbabu, "天龙八部"));
        models.addAll(getCharacters(R.array.shediaoyingxiong, "射雕英雄传"));
        models.addAll(getCharacters(R.array.baimaxiaoxifeng, "白马啸西风"));
        models.addAll(getCharacters(R.array.ludingji, "鹿鼎记"));
        mRecyclerView.setAdapter(new TextAdapter(models));

        StickyHeaderHelper.init(mRecyclerView, 0, mHeaderLayout, 0);
        StickyHeaderRegistry.registerTransfer(AmericanNation.class, ChineseNationStickyHeaderModel.class);
        StickyHeaderRegistry.registerTransfer(Book.class, BookStickyHeaderModel.class);


    }

    public void turnOn(View view) {
        StickyHeaderHelper.turnStickyHeader(true, mRecyclerView);
    }

    public void turnOff(View view) {
        StickyHeaderHelper.turnStickyHeader(false, mRecyclerView);

    }

    private static class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderAdapter<Naming> {
        private List<Naming> mNamings;
        public static final int PERSON = 0;
        public static final int AMERICAN_NATION = 1;
        public static final int BOOK = 2;


        public TextAdapter(List<Naming> namings) {
            this.mNamings = namings;

        }

        @Override
        public Naming getItem(int position) {
            return mNamings.get(position);
        }

        @Override
        public BaseStickyHeaderModel<Naming> transferToStickyHeaderModel(int position) {
            return StickyHeaderHelper.transferToStickyHeaderModel(this, position);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == PERSON) {
                View view = new PersonView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new PersonViewHolder(view);
            } else if (viewType == AMERICAN_NATION) {
                View view = new AmericanNationView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new AmericanNationViewHolder(view);
            } else if (viewType == BOOK) {
                View view = new BookView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new ChineseNationViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof PersonViewHolder) {
                ((PersonView) holder.itemView).mTextView.setText(mNamings.get(position).getName());
            } else if (holder instanceof AmericanNationViewHolder) {
                ((AmericanNationView) holder.itemView).setData2((AmericanNation) mNamings.get(position));
            } else if (holder instanceof ChineseNationViewHolder) {
                ((BookView) holder.itemView).setData((Book) mNamings.get(position));
            }
        }

        @Override
        public int getItemViewType(int position) {
            Naming naming = mNamings.get(position);
            if (naming instanceof Person) return PERSON;
            if (naming instanceof AmericanNation) return AMERICAN_NATION;
            if (naming instanceof Book) return BOOK;
            return -1;
        }

        @Override
        public int getItemCount() {
            return mNamings.size();
        }
    }

    private static class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class AmericanNationViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public AmericanNationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class ChineseNationViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ChineseNationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
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