package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.content.Context;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.OPTION_CHECK_SET_ID;

/**
 * Created by user on 9/30/2017.
 */

public abstract class BaseGuideStepFragment extends GuidedStepFragment {
    // step - create button actions -for navigate to another step
    protected static void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }

    protected static void addCheckedAction(List<GuidedAction> actions, int iconResId, Context context,
                                         String title, String desc, boolean checked) {
        GuidedAction guidedAction = new GuidedAction.Builder()
                .title(title)
                .description(desc)
                .checkSetId(OPTION_CHECK_SET_ID)
                .iconResourceId(iconResId, context)
                .build();
        guidedAction.setChecked(checked);
        actions.add(guidedAction);
    }
}
