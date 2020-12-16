package com.xuanyu.stickyheader.sample.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanyu.stickyheader.BaseStickyHeaderModel;
import com.xuanyu.stickyheader.StickyHeaderAdapter;
import com.xuanyu.stickyheader.StickyHeaderHelper;
import com.xuanyu.stickyheader.sample.bean.Author;
import com.xuanyu.stickyheader.sample.bean.CustomBook;
import com.xuanyu.stickyheader.sample.view.CustomBookView;
import com.xuanyu.stickyheader.sample.view.AuthorView;
import com.xuanyu.stickyheader.sample.view.BookView;
import com.xuanyu.stickyheader.sample.view.PersonView;
import com.xuanyu.stickyheader.sample.bean.Book;
import com.xuanyu.stickyheader.sample.bean.Naming;

import java.util.List;

public class NamingStickyHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderAdapter<Naming> {
    private List<Naming> mNamings;


    public NamingStickyHeaderAdapter(List<Naming> namings) {
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
        if (viewType == Naming.PERSON_NAME) {
            View view = new PersonView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new PersonViewHolder(view);
        } else if (viewType == Naming.CUSTOM_BOOK_NAME) {
            View view = new CustomBookView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new CustomBookViewHolder(view);
        } else if (viewType == Naming.BOOK_NAME) {
            View view = new BookView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BookViewHolder(view);
        } else if (viewType == Naming.AUTHOR_NAME) {
            View view = new AuthorView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new AuthorViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonViewHolder) {
            ((PersonView) holder.itemView).mTextView.setText(mNamings.get(position).getName());
        } else if (holder instanceof BookViewHolder) {
            ((BookView) holder.itemView).setData((Book) mNamings.get(position));
        } else if (holder instanceof AuthorViewHolder) {
            ((AuthorView) holder.itemView).setData((Author) mNamings.get(position));
        } else if (holder instanceof CustomBookViewHolder) {
            ((CustomBookView) holder.itemView).setData((CustomBook) mNamings.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Naming naming = mNamings.get(position);
        return naming.getItemType();
    }

    @Override
    public int getItemCount() {
        return mNamings.size();
    }

    private static class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class CustomBookViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public CustomBookViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
