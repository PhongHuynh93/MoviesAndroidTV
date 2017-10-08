package com.gabilheri.moviestmdb.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.gabilheri.moviestmdb.ui.base.NavigationInterface;
import com.gabilheri.moviestmdb.ui.moresample.guide.BaseGuideStepFragment;

import java.util.List;

/**
 * Created by user on 10/8/2017.
 */

public class TypeLoginGuidedStepFragment extends BaseGuideStepFragment {
    private static final long MOVIE_ID = 0;
    private static final long MUSIC_ID = 1;

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = "Choose type of API";
        String breadcrumb = "Spotify or Movie DB";
        String description = "test";
        return new GuidanceStylist.Guidance(title, description, breadcrumb, null);    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        addAction(actions, MOVIE_ID, "Movie", "Movie DB");
        addAction(actions, MUSIC_ID, "Music", "Spotify");
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == MOVIE_ID) {
            ((NavigationInterface)getActivity()).goToMainScreen();
        } else if (action.getId() == MUSIC_ID){
            ((NavigationInterface)getActivity()).goToMainScreenMusic();
        }
    }
}
