package com.zhaofn.tinnews.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaofn.tinnews.R;
import com.zhaofn.tinnews.databinding.FragmentSaveBinding;
import com.zhaofn.tinnews.model.Article;
import com.zhaofn.tinnews.repository.NewsRepository;
import com.zhaofn.tinnews.repository.NewsViewModelFactory;

public class SaveFragment extends Fragment {
    private FragmentSaveBinding binding;
    private SaveViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_save, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.newsSavedRecyclerView.setAdapter(savedNewsAdapter);//告诉他怎么创建删除
        binding.newsSavedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));//告诉怎么布局

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                            }
                        });

        savedNewsAdapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {//因为比较短就这么写，也可以用implement的方法去实现
            @Override
            public void onOpenDetails(Article article) {
                // TODO
                Log.d("onOpenDetails", article.toString());
                //在这里nav到details，这行是generate出来的
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetails direction =  //这是个class，名字是根据nav graph里面来的
                        SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article);
                NavHostFragment.findNavController(SaveFragment.this).navigate(direction);//这里不能直接用this，如果前面不加savefragment，则这个this是指"new SavedNewsAdapter.ItemCallback()"的这个itemcallback匿名类，因为这句话在这个inner class里面
                //这里想要的是一个fragment，是这个inner class的outer class

            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });

        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                                savedNewsAdapter.setArticles(savedArticles);
                            }
                        });

    }
}