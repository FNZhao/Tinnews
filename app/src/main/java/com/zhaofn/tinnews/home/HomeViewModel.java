package com.zhaofn.tinnews.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.zhaofn.tinnews.model.Article;
import com.zhaofn.tinnews.model.NewsResponse;
import com.zhaofn.tinnews.repository.NewsRepository;

//只要data放在view model里，数据就不会丢失（否则configuration change的时候，ui状态会丢失，有些东西并不会存到db里）
public class HomeViewModel extends ViewModel {//要写继承，继承自之前添加的lifecycle dependency里的viewmodel
    //viewmodel要从repository拿到data
    private final NewsRepository newsRepository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {//constructor
        this.newsRepository = newsRepository;
    }

    //event
    public void setCountryInput(String country) {
        countryInput.setValue(country);//这个event改变了livedata
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, newsRepository::getTopHeadlines);//transformation.switchmap这个函数可以把第一个livedata的行为变成第二个livedata的行为，这样我们改变contryinput就可以同时改变newsrepository,然后重新call一次gettopheadlines函数，然后return回去
    }                                                               // :: 双冒号，lambda的简化版，表示传入一个newsRepository返回newsRepository下面的gettopheadlines函数
                                                                    // switchmap就是按照list挨个处理数据
                                                                    //这样只要发生改变
                                                                    //相当于做了一个生产线，有这个input我们就让他经过这个生产线

    public void setFavoriteArticleInput(Article article) {
        newsRepository.favoriteArticle(article);//这里不用写上面的transformation，因为不需要expose结果，所以就不需要生成这么一个生产线，通过监视他来看有没有结果
    }
}
