/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.billcombsdevelopment.bakingapp.R;
import com.billcombsdevelopment.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.step_exo_player)
    PlayerView mPlayerView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.step_iv)
    ImageView mStepIv;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.description_tv)
    TextView mDescriptionTv;
    private Step mStep;
    private SimpleExoPlayer mExoPlayer = null;
    private Long mPlayerPosition = 0L;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.step_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        if (getArguments() != null && getArguments().containsKey("step")) {
            mStep = getArguments().getParcelable("step");
        } else {
            mStep = null;
        }

        initUi();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("playerPosition")) {
                // Restore position of ExoPlayer
                mPlayerPosition = savedInstanceState.getLong("playerPosition");
                if (mExoPlayer != null) {
                    mExoPlayer.seekTo(mPlayerPosition);
                }
            }
        }
    }

    private void initUi() {
        if (mStep.getVideoUrl() != null && !mStep.getVideoUrl().isEmpty()) {
            Uri videoUri = Uri.parse(mStep.getVideoUrl());
            initPlayer(videoUri);
        } else if (mStep.getThumbnailUrl() != null && !mStep.getThumbnailUrl().isEmpty()) {
            loadImage();
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        mDescriptionTv.setText(mStep.getDescription());
    }

    private void initPlayer(Uri videoUri) {
        if (mExoPlayer == null) {

            // Create TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // Create player
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(),
                    getResources().getString(R.string.app_name));

            // Prepare MediaSource
            if (getContext() != null) {
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(videoUri);
                mExoPlayer.prepare(videoSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }

        // If in landscape mode go full screen
        if (getActivity() != null && getActivity().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            setFullScreen();
        }
    }

    private void releaseExoPlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void setFullScreen() {
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
    }

    private void loadImage() {

        // Hide the ExoPlayerView and load the image
        mPlayerView.setVisibility(View.GONE);
        Picasso.with(getContext()).load(mStep.getThumbnailUrl())
                .into(mStepIv, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Image was loaded, show the ImageView
                        mStepIv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        // Problem loading Image. Hide the ImageView
                        mStepIv.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playerPosition", mPlayerPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getCurrentPosition();
            releaseExoPlayer();
        }
    }
}
