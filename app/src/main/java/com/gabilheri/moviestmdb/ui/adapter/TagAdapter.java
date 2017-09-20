package com.gabilheri.moviestmdb.ui.adapter;

import android.content.Context;
import android.nfc.Tag;

import com.gabilheri.moviestmdb.ui.presenter.TagPresenter;

import java.util.ArrayList;
import java.util.List;

// it also extend page to load more with page
public class TagAdapter extends PaginationAdapter {

    public TagAdapter(Context context, String tag) {
        super(context, new TagPresenter(), tag);
    }

    @Override
    public void addAllItems(List<?> items) {
        addPosts(items);
    }

    @Override
    public List<?> getAllItems() {
        List<Object> itemList = getItems();
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            Object object = itemList.get(i);
            if (object instanceof Tag) tags.add((Tag) object);
        }
        return tags;
    }
}
