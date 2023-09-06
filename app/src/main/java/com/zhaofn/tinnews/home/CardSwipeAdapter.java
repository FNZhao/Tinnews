package com.zhaofn.tinnews.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhaofn.tinnews.R;
import com.zhaofn.tinnews.databinding.SwipeNewsCardBinding;
import com.zhaofn.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder> {//意思是说recycleerview里只有这一种viewholder
    //不管显示成什么样，这在数据上都是一个list of article
    // 1. Supporting data:
    // TODO
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();//这个使得recycler view知道数据的变化
    }

    @NonNull
    @Override
    public CardSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_news_card, parent, false);
        return new CardSwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSwipeViewHolder holder, int position) {//这里就是set up data
        Article article = articles.get(position);
        holder.titleTextView.setText(article.title);
        holder.descriptionTextView.setText(article.description);
        if (article.urlToImage != null) { //这里不知道图片多大，所以不建议resize
            Picasso.get().load(article.urlToImage)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 2. CardSwipeViewHolder:
    // TODO
    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public CardSwipeViewHolder(@NonNull View itemView) { //这里所接的view是由swipe news card xml文件生成来的，是从上面的onCreateViewHolder的inflater来的
            super(itemView);
            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            imageView = binding.swipeCardImageView;
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
        }
    }

    // 3. Adapter overrides:
    // TODO

    //article getter
    public List<Article> getArticles() {
        return articles;
    }



}
