package com.gabilheri.moviestmdb.ui.adapter;

import android.content.Context;

import com.gabilheri.moviestmdb.data.models.Movie;
import com.gabilheri.moviestmdb.ui.presenter.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/17/2017.
 */

public class PostAdapter  extends PaginationAdapter {
    public PostAdapter(Context context, String tag) {
        super(context, new MoviePresenter(context), tag);
    }

    @Override
    public void addAllItems(List<?> items) {
        List<Movie> currentPosts = getAllItems();
        ArrayList<Movie> posts = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Object object = items.get(i);
            if (object instanceof Movie && !currentPosts.contains(object)) {
                posts.add((Movie) object);
            }
        }
//        Collections.sort(posts);
        addPosts(posts);
    }

    @Override
    public List<Movie> getAllItems() {
        List<Object> itemList = getItems();
        ArrayList<Movie> posts = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            Object object = itemList.get(i);
            if (object instanceof Movie) posts.add((Movie) object);
        }
        return posts;
    }
}
