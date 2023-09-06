package com.zhaofn.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zhaofn.tinnews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private NavController navController;
//binding:    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//binding:        binding = ActivityMainBinding.inflate(getLayoutInflater());
//binding:        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);

//binding:        BottomNavigationView navView = binding.navView; //最前面的是类名，可以去xml查看
        BottomNavigationView navView = findViewById(R.id.nav_view);//得到activity。xml里的navview

        //这个就类似于container的主人（controller）
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);//getsupportfragmentmanager,在有图以前都需要这个东西来做navigation，是fragment的管理者，管理每一个fragment，里面拥有所有的fragment
                                                          //最后面的id对应的就是之前在activity xml里引入navigation的部分的id（是manager或者controller）
        navController = navHostFragment.getNavController();//之后就可以从manager里获取controller，类似于管理每一个fragment的工具
        // click on tab on BottomNavView can navigate
        NavigationUI.setupWithNavController(navView, navController);//直接把工具交给view，之后view里就有这个controller了，然后点击下面的button的时候就可以跳转了
                                                                    //因为controller本身可以完成点击跳转这个操作，而navview里有这个工具
                                                                    //而这里也是我们让nav graph里的id与bottom nav menu里的id一致的原因
                                                                    //因为controller在导航的时候会传入一个id，而这个id是nav graph上的id，但是view只知道自己的id不知道graph的id
                                                                    //所以如果把他们定义成一个id，我们点view里的id所传过去的就会对应上navigation graph的id，所以就能够正确导航
        // can display label on action bar，让label显示在左上角，action bar是系统给的，所以默认放到activity里了,是属于activity的，所以直接this就好了，从navigation里读出label然后显示
        NavigationUI.setupActionBarWithNavController(this, navController);

//        //这一部分是network request test code，测试用的，之后要删掉
//        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);//做一个call，用create去实现newsapi的interface并且实例成api
//        //然后就能使用它
//        Call<NewsResponse> call = api.getTopHeadlines("us");//调用gettoheadlines函数，Call相当于一个task，里面包裹着task的结果
//
//        call.enqueue(new Callback<NewsResponse>() {
//       @Override
//       public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                           if (response.isSuccessful()) {
//                                   Log.d("getTopHeadlines", response.body().toString());
//                               } else {
//                                   Log.d("getTopHeadlines", response.toString());
//                               }
//                       }
//
//               @Override
//       public void onFailure(Call<NewsResponse> call, Throwable t) {
//                           Log.d("getTopHeadlines", t.toString());
//                       }
//   });//执行函数，里面要传两个重写，表示结束以后要做的工作

    }


    @Override
    public boolean onSupportNavigateUp() { //重写一下返回到上一级，因为action bar本身就是属于这个类的所以可以在这直接写
        // can click back
        return navController.navigateUp();//navigation up这个function就是controller工具里的返回上一层的api
    }
}