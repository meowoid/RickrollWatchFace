package com.shaibarack.rickrollwatchface;

import android.graphics.Canvas;
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
        private Drawable mAmbient;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            mMovie = Movie.decodeStream(getResources().openRawResource(R.raw.rickroll));
            setWatchFaceStyle(new WatchFaceStyle.Builder(WatchFaceService.this)
                    .setShowSystemUiTime(true)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .build());
            mAmbient = getResources().getDrawable(R.drawable.ambient);
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            if (isInAmbientMode()) {
                mAmbient.setBounds(bounds);
                mAmbient.draw(canvas);
            } else {
                mMovie.setTime((int) (System.currentTimeMillis() % mMovie.duration()));
                mMovie.draw(canvas, 0, 0);
                invalidate();
            }
        }
    }
}
