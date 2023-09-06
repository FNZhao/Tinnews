package com.zhaofn.tinnews.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhaofn.tinnews.databinding.FragmentDetailsBinding;
import com.zhaofn.tinnews.model.Article;
import com.squareup.picasso.Picasso;

//viewmodel的作用是take user ui action，这个时候才需要view model
//如果只是更新ui还不需要，如果要做一些logic参与就需要view model
//

public class DetailsFragment extends Fragment {
   private FragmentDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                binding = FragmentDetailsBinding.inflate(inflater, container, false);
                return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Article article = DetailsFragmentArgs.fromBundle(getArguments()).getArticle();
        binding.detailsTitleTextView.setText(article.title);
        binding.detailsAuthorTextView.setText(article.author);
        binding.detailsDateTextView.setText(article.publishedAt);
        binding.detailsDescriptionTextView.setText(article.description);
        binding.detailsContentTextView.setText(article.content);
        Picasso.get().load(article.urlToImage).into(binding.detailsImageView);
    }

}
