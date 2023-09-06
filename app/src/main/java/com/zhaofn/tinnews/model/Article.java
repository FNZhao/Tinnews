package com.zhaofn.tinnews.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
//data class,目的是与json做mapping，serialize和deserialize, 一般我们会再加上tostring和equals and hashcode， 用右键里的generate即可。这样我们在debug print的时候就可以看到他们的内容
public class Article implements Serializable {//article里面都是string，可以序列化，但是注意某些class没法做序列化
    public String author;
    public String content;
    public String description;
    public String publishedAt;
    public String title;
    @NonNull // 这个东西在实际的code中没有用处，所以是documented，只是告诉他别放null。 删了也没关系
    @PrimaryKey
    public String url;
    public String urlToImage;

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) && Objects.equals(content, article.content) && Objects.equals(description, article.description) && Objects.equals(publishedAt, article.publishedAt) && Objects.equals(title, article.title) && Objects.equals(url, article.url) && Objects.equals(urlToImage, article.urlToImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, description, publishedAt, title, url, urlToImage);
    }
}
