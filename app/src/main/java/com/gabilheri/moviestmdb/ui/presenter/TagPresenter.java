package com.gabilheri.moviestmdb.ui.presenter;

import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.example.myapplication.data.models.Tag;
import com.example.myapplication.data.models.User;
import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.ui.widget.TagCardView;

/**
 * Created by CPU11112-local on 9/20/2017.
 */

public class TagPresenter extends Presenter {
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.primary);
        sSelectedBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.card_background);

        TagCardView cardView = new TagCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Tag) {
            Tag post = (Tag) item;
            TagCardView cardView = (TagCardView) viewHolder.view;

            if (post.tag != null) {
                cardView.setCardText(post.tag);
                cardView.setCardIcon(R.drawable.ic_tag);
            }
        } else if (item instanceof User) {
            User post = (User) item;
            TagCardView cardView = (TagCardView) viewHolder.view;

            if (post.username != null) {
                cardView.setCardText(post.username);
                cardView.setCardIcon(R.drawable.ic_user);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    private void updateCardBackgroundColor(TagCardView view, boolean selected) {
        view.setBackgroundColor(selected ? sSelectedBackgroundColor : sDefaultBackgroundColor);
    }

}
