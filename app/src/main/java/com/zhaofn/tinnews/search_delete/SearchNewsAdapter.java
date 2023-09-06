package com.zhaofn.tinnews.search_delete;

import com.zhaofn.tinnews.databinding.SearchNewsItemBinding;

//public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> { //声明我的view的adapter里叫做SearchNewsViewHolder
//    // 1. Supporting data:
//    private List<Article> articles = new ArrayList<>();
//
//    public void setArticles(List<Article> newsList) {
//        articles.clear();
//        articles.addAll(newsList);
//        notifyDataSetChanged();//注意到data set 的变化，这个可以触发下面的方法， 然后更新显示的内容
//    }
//
//    // 2. SearchNewsViewHolder:
//    //如果要实现这一步首先要实现三个方法
//    @NonNull
//    @Override
//    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//这个背一下
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
//        return new SearchNewsViewHolder(view);//拿到view传进来
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {//把内容和viewholder ui结合在一起，holder是将要显示到ui上的holder（比如最底下没显示的那个），position是要显示list上的哪个item这个position就是什么
//        Article article = articles.get(position);
//        holder.itemTitleTextView.setText(article.title);
//    }
//
//    @Override
//    public int getItemCount() {
//        return articles.size();
//    }
//
//    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder { //inner class，继承自recyclerview
//                                                                    //viewholder是包裹着内容的任务
//
//        ImageView itemImageView;
//        TextView itemTitleTextView;
//
//        public SearchNewsViewHolder(@NonNull View itemView) { //itemview是我们要显示的内容
//            super(itemView);
//            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView); //传入itemview，里面有两个东西，一个是imageview一个是title
//            itemImageView = binding.searchItemImage; //把上述两个分别赋值给
//            itemTitleTextView = binding.searchItemTitle;
//        }
//}
