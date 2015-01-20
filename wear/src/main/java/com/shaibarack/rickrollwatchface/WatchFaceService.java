package com.shaibarack.rickrollwatchface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Rect;
import android.graphics.RectF;
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
        private Matrix mMatrix;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            mMovie = Movie.decodeStream(getResources().openRawResource(R.raw.rickroll));
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
        public void onAmbientModeChanged(boolean inAmbientMode) {
            if (!inAmbientMode) {
                invalidate();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mMatrix = new Matrix();
            RectF surfaceRect = new RectF(0, 0, width, height);
            RectF movieRect = new RectF(0, 0, mMovie.width(), mMovie.height());
            mMatrix.setRectToRect(movieRect, surfaceRect, Matrix.ScaleToFit.CENTER);
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
                canvas.setMatrix(mMatrix);
                mMovie.setTime((int) (System.currentTimeMillis() % mMovie.duration()));
                mMovie.draw(canvas, 0, 0);
                invalidate();
            }
        }
    }
}
