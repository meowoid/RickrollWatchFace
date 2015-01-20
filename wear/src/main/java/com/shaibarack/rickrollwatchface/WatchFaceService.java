package com.shaibarack.rickrollwatchface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {

        private Movie mMovie;
        private float mMovieHeight;
        private float mMovieWidth;
        private int mMoviePivotX;
        private int mMoviePivotY;
        private Drawable mAmbient;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            mMovie = Movie.decodeStream(getResources().openRawResource(R.raw.rickroll));
            mMovieHeight = mMovie.height();
            mMovieWidth = mMovie.width();
            mMoviePivotX = (int) mMovieHeight / 2;
            mMoviePivotY = (int) mMovieWidth / 2;
            mAmbient = getResources().getDrawable(R.drawable.ambient);
            mAmbient.setAlpha(127);
            setWatchFaceStyle(new WatchFaceStyle.Builder(WatchFaceService.this)
                    .setShowSystemUiTime(true)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setViewProtection(WatchFaceStyle.PROTECT_STATUS_BAR
                            | WatchFaceStyle.PROTECT_HOTWORD_INDICATOR)
                    .build());
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                invalidate();
            }
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            if (!isVisible()) {
                return;
            }
            if (isInAmbientMode()) {
                canvas.drawColor(Color.BLACK);
                mAmbient.setBounds(bounds);
                mAmbient.draw(canvas);
            } else {
                float scaleX = mMovieWidth / bounds.width();
                float scaleY = mMovieHeight / bounds.height();
                if (scaleX == scaleY) {
                    canvas.scale(scaleX, scaleY, mMoviePivotX, mMoviePivotY);
                } else if (scaleX < scaleY) {
                    canvas.scale(scaleY, scaleY, mMoviePivotX, mMoviePivotY);
                } else {
                    canvas.scale(scaleX, scaleX, mMoviePivotX, mMoviePivotY);
                }
                mMovie.setTime((int) (System.currentTimeMillis() % mMovie.duration()));
                mMovie.draw(canvas, 0, 0);
                invalidate();
            }
        }
    }
}
