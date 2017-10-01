package com.gabilheri.moviestmdb.ui.moresample.guide;

import android.content.Context;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static com.gabilheri.moviestmdb.util.Constant.OPTION_CHECK_SET_ID;

/**
 * Created by user on 9/30/2017.
 */

public abstract class BaseGuideStepFragment extends GuidedStepFragment {
    // step - create button actions -for navigate to another step
    protected void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder(getActivity())
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }

    protected void addActionHasNext(Context context, List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder(context)
                .id(id)
                .title(title)
                .description(desc)
                .hasNext(true)
                .build());
    }

    protected void addActionSubAction(Context context, List<GuidedAction> actions, long id, long subid, String title, String desc) {
        List<GuidedAction> subActions = new ArrayList<GuidedAction>();
        subActions.add(new GuidedAction.Builder(context)
                .id(subid)
                .title(title)
                .description(desc)
                .build());

        actions.add(new GuidedAction.Builder(context)
                .id(id)
                .title(title)
                .description(desc)
                .subActions(subActions)
                .build());
    }

    protected void addCheckedAction(Context context, List<GuidedAction> actions, int iconResId,
                                         String title, String desc, boolean checked) {
        GuidedAction guidedAction = new GuidedAction.Builder(context)
                .title(title)
                .description(desc)
                .checkSetId(OPTION_CHECK_SET_ID)
                .icon(iconResId)
                .build();
        guidedAction.setChecked(checked);
        actions.add(guidedAction);
    }

    protected void addMutlipleAction(Context context, List<GuidedAction> actions,
                                           int title, int desc,
                                            String[] OPTION_NAMES,
                                            String[] OPTION_DESCRIPTIONS,
                                            int[] OPTION_DRAWABLES,
                                            boolean[] OPTION_CHECKED) {
        actions.add(new GuidedAction.Builder(context)
                .title(context.getResources().getString(title))
                .description(context.getResources().getString(desc))
                .multilineDescription(true)
                .infoOnly(true)
                .enabled(false)
                .build());
        for (int i = 0; i < OPTION_NAMES.length; i++) {
            addCheckedAction(context, actions,
                    OPTION_DRAWABLES[i],
                    OPTION_NAMES[i],
                    OPTION_DESCRIPTIONS[i],
                    OPTION_CHECKED[i]);
        }
    }

    protected GuidanceStylist.Guidance createLeftGuidance(Context context, int breadcrumb, int title, int description, int icon) {
        return new GuidanceStylist.Guidance(
                context.getString(title),
                context.getString(description),
                context.getString(breadcrumb),
                ContextCompat.getDrawable(context, icon));
    }

    protected void addNextStep(GuidedStepFragment nextStepFrag) {
        GuidedStepFragment.add(getFragmentManager(), nextStepFrag);
    }

    protected void returnPreviousStep() {
        getFragmentManager().popBackStack();
    }

    protected void getOutStep() {
        getActivity().finishAfterTransition();
    }
}
