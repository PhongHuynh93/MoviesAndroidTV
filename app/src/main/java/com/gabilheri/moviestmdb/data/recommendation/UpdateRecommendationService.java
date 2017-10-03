package com.gabilheri.moviestmdb.data.recommendation;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by CPU11112-local on 10/3/2017.
 */

public class UpdateRecommendationService extends IntentService {
    private static final String TAG = UpdateRecommendationService.class.getSimpleName();

    public UpdateRecommendationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
