package com.zhaofn.tinnews.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zhaofn.tinnews.home.HomeViewModel;
import com.zhaofn.tinnews.save.SaveViewModel;
import com.zhaofn.tinnews.search.SearchViewModel;

public class NewsViewModelFactory implements ViewModelProvider.Factory {//如果创建viewmodel的时候需要传参数的话，就需要使用provider提供的这个interface

    private final NewsRepository repository;

    public NewsViewModelFactory(NewsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {//这个factory里只有这一个方法，只接受一个参数，就是classname
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {//如果是home的viewmodel class就return home
            return (T) new HomeViewModel(repository);//因为定义viewmodel的时候需要给一个repository参数，所以要自己写，如果我们定义的时候不需要传东西，则可以不自己定义工厂
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(repository);
        } else if (modelClass.isAssignableFrom(SaveViewModel.class)) {
            return (T) new SaveViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}
