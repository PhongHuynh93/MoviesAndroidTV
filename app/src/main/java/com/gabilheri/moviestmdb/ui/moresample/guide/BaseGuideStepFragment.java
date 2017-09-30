package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;

/**
 * Created by user on 9/30/2017.
 */

public class BaseGuideStepFragment extends GuidedStepFragment {
    protected static void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }
}
