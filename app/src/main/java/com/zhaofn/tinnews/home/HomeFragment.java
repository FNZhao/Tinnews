package com.zhaofn.tinnews.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaofn.tinnews.R;
import com.zhaofn.tinnews.databinding.FragmentHomeBinding;
import com.zhaofn.tinnews.model.Article;
import com.zhaofn.tinnews.repository.NewsRepository;
import com.zhaofn.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;//这里就是说这个fragment是用xml生成的
    //这两个在这里定义是scope原因
    private CardStackLayoutManager layoutManager;
    private CardSwipeAdapter swipeAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //这也是一个生命周期，是create view的生命周期，activity里是糅合在一起的，而fragment里是分开的
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);//用生成的binding 调用这个function然后return root，这会在build里生成一个data binding文件
        return binding.getRoot();
        //viewbinding做了两件事，一个是下面的注释掉的部分，一个是bind
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);//自动生成的，把xml file转换成tree hierarchy的code
    }

    @Override//已经创建了view才能在创建view的声明周期里写函数，所以一定是要先有createview再onviewcreated
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {//ui在这里被new，在onstop之后就不能更新ui了
        super.onViewCreated(view, savedInstanceState);

        // Setup CardStackView
        swipeAdapter = new CardSwipeAdapter();//目前还没有set article，所以现在里面还没有新闻。注意前面不要谢CardSwipeAdapter，不然每一次调用都是一个null
        layoutManager = new CardStackLayoutManager(requireContext(), this);//加个this，表示把homefragment连上
        layoutManager.setStackFrom(StackFrom.Top);//一个小的ui效果
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);

        NewsRepository repository = new NewsRepository();//call了constructor
        //viewModel = new HomeViewModel(repository);//不应该自己new viewmodel的，但这个是系统给的，之后会改
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);//换成工厂模式创建，传入现在的fragment(this),和刚才写的factory，然后就可以在provider里get model
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe( //里面传两个东西
                        getViewLifecycleOwner(),//第一个是lifecycle，让他知道lifecycle的状态，他会判断这个状态是否可以更新ui
                        newsResponse -> {//第二个是每一次我gettopheadlines的时候我就可以拿到response，这个就是obsever，只要newsresponse发生变化就执行下面的
                            if (newsResponse != null) {
                                Log.d("HomeFragment", newsResponse.toString());
                                swipeAdapter.setArticles(newsResponse.articles);//到这里才会使得新闻显示
                            }
                        });
        // Handle like unlike button clicks
        //这个是点赞与踩的onclick事件
        //lambda写法
        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(v -> swipeCard(Direction.Left));
        //Java写法
        //homefragment想去listen，那我就在我的inner class(这个class就是onclick listener)实现你的interface然后传给你
//        binding.homeUnlikeButton.setOnClickListener(new View.OnClickListener() {//匿名类，实现了View.setOnClickListener这个interface（interface转化的）
//            @Override                                                           //是homefragment的inner class， interface是没法单独成为一个class不能new instance，
//            public void onClick(View v) {//onclick是interface里的
//                swipeCard(Direction.Left);
//            }
//        });


    }

    private void swipeCard(Direction direction) {//注意这个function是home fragment里的
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        layoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }

    @Override
    public void onCardDragging(Direction direction, float v) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Left) {
            Log.d("CardStackView", "Unliked " + layoutManager.getTopPosition());
        } else if (direction == Direction.Right) {
            Log.d("CardStackView", "Liked "  + layoutManager.getTopPosition());
            Article article = swipeAdapter.getArticles().get(layoutManager.getTopPosition() -1);
            viewModel.setFavoriteArticleInput(article);

        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int i) {

    }

    @Override
    public void onCardDisappeared(View view, int i) {

    }

    //如果把homefragment本身当作listener去implement onclicklistener，那我们就需要在这个class本身的onclick里实现一个if判断传入的view然后决定向左向右
    //而在onviewcreated里就可以直接传入binding.homeLikeButton.setOnClickListener(this);
}