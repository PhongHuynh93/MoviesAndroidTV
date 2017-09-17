package com.gabilheri.moviestmdb.ui.detail;

import android.graphics.drawable.GradientDrawable;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabilheri.moviestmdb.R;
import com.gabilheri.moviestmdb.data.models.Genre;
import com.gabilheri.moviestmdb.data.models.MovieDetails;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CPU11112-local on 9/15/2017.
 */
// INFO - VIEWHOLDER IS THE BINDER CLASS, bind the details to view
public class MovieDetailsViewHolder extends Presenter.ViewHolder {
    @BindView(R.id.movie_title)
    TextView movieTitleTV;

    @BindView(R.id.movie_year)
    TextView movieYearTV;

    @BindView(R.id.overview)
    TextView movieOverview;

    @BindView(R.id.runtime)
    TextView mRuntimeTV;

    @BindView(R.id.tagline)
    TextView mTaglineTV;

    @BindView(R.id.director_tv)
    TextView mDirectorTv;

    @BindView(R.id.overview_label)
    TextView mOverviewLabelTV;

    @BindView(R.id.genres)
    LinearLayout mGenresLayout;

    private View itemView;

    public MovieDetailsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        itemView = view;
    }

    // info - new way with pallete depend on the color of a movie
    public void bind(MovieDetails movie) {
        if (movie != null && movie.getTitle() != null) {
            mRuntimeTV.setText(String.format(Locale.getDefault(), "%d minutes", movie.getRuntime()));
            mTaglineTV.setText(movie.getTagline());
            movieTitleTV.setText(movie.getTitle());
            movieYearTV.setText(String.format(Locale.getDefault(), "(%s)", movie.getReleaseDate().substring(0, 4)));
            movieOverview.setText(movie.getOverview());
            // info - remove all old view
            mGenresLayout.removeAllViews();

            if (movie.getDirector() != null) {
                mDirectorTv.setText(String.format(Locale.getDefault(), "Director: %s", movie.getDirector()));
            }

            int _16dp = (int) itemView.getResources().getDimension(R.dimen.full_padding);
            int _8dp = (int) itemView.getResources().getDimension(R.dimen.half_padding);
            float corner = itemView.getResources().getDimension(R.dimen.genre_corner);

            // info - set the color depend on palette color because might hard to see the text
            int primaryDarkColor = ContextCompat.getColor(itemView.getContext(), R.color.primary_dark);
            if (movie.getPaletteColors() != null) {
                movieTitleTV.setTextColor(movie.getPaletteColors().getTitleColor());
                mOverviewLabelTV.setTextColor(movie.getPaletteColors().getTitleColor());
                mTaglineTV.setTextColor(movie.getPaletteColors().getTextColor());
                mRuntimeTV.setTextColor(movie.getPaletteColors().getTextColor());
                movieYearTV.setTextColor(movie.getPaletteColors().getTextColor());
                movieOverview.setTextColor(movie.getPaletteColors().getTextColor());
                mDirectorTv.setTextColor(movie.getPaletteColors().getTextColor());
                primaryDarkColor = movie.getPaletteColors().getStatusBarColor();
            }

            // info - Adds each genre to the genre layout, it's just GradientDrawable
            for (Genre g : movie.getGenres()) {
                TextView tv = new TextView(itemView.getContext());
                tv.setText(g.getName());
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadius(corner);
                shape.setColor(primaryDarkColor); // change this to use the palette color
//                shape.setColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_dark));
                tv.setPadding(_8dp, _8dp, _8dp, _8dp);
                tv.setBackground(shape);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(0, 0, _16dp, 0);
                tv.setLayoutParams(params);

                mGenresLayout.addView(tv);
            }
        }

    }

    // info - old way not use pallete
//    public void bind(MovieDetails movie) {
//        if (movie != null && movie.getTitle() != null) {
//            mRuntimeTV.setText(String.format(Locale.getDefault(), "%d minutes", movie.getRuntime()));
//            mTaglineTV.setText(movie.getTagline());
//            movieTitleTV.setText(movie.getTitle());
//            movieYearTV.setText(String.format(Locale.getDefault(), "(%s)", movie.getReleaseDate().substring(0, 4)));
//            movieOverview.setText(movie.getOverview());
//            mGenresLayout.removeAllViews();
//
//            if (movie.getDirector() != null) {
//                mDirectorTv.setText(String.format(Locale.getDefault(), "Director: %s", movie.getDirector()));
//            }
//
//            int _16dp = (int) itemView.getResources().getDimension(R.dimen.full_padding);
//            int _8dp = (int) itemView.getResources().getDimension(R.dimen.half_padding);
//            float corner = itemView.getResources().getDimension(R.dimen.genre_corner);
//
//            // Adds each genre to the genre layout
//            for (Genre g : movie.getGenres()) {
//                TextView tv = new TextView(itemView.getContext());
//                tv.setText(g.getName());
//                GradientDrawable shape = new GradientDrawable();
//                shape.setShape(GradientDrawable.RECTANGLE);
//                shape.setCornerRadius(corner);
//                shape.setColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_dark));
//                tv.setPadding(_8dp, _8dp, _8dp, _8dp);
//                tv.setBackground(shape);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT));
//                params.setMargins(0, 0, _16dp, 0);
//                tv.setLayoutParams(params);
//
//                mGenresLayout.addView(tv);
//            }
//        }
//
//    }
}
