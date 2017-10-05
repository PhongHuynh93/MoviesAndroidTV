package com.gabilheri.moviestmdb.ui.playback;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.myapplication.Movie;

/**
 * Created by CPU11112-local on 10/5/2017.
 */

public class SharePlaybackViewModel extends ViewModel {
    private final MutableLiveData<SharePlaybackModel> mMovie = new MutableLiveData<>();

    public void select(SharePlaybackModel sharePlaybackModel) {
        mMovie.setValue(sharePlaybackModel);
    }

    public LiveData<SharePlaybackModel> getSelected() {
        return mMovie;
    }

    public static class SharePlaybackModel {
        private Movie mMovie = new Movie();
        private int mPosition;
        private boolean mPlayPause;

        public SharePlaybackModel(Movie movie, int position, boolean playPause) {
            mMovie = movie;
            mPosition = position;
            mPlayPause = playPause;
        }

        public Movie getMovie() {
            return mMovie;
        }

        public int getPosition() {
            return mPosition;
        }

        public boolean isPlayPause() {
            return mPlayPause;
        }
    }
}
