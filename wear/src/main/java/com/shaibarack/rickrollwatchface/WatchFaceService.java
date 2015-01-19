package com.shaibarack.rickrollwatchface;

import android.graphics.Movie;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;

public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {

        private Movie mMovie;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            //mMovie = Movie.decodeStream(getResources().openRawResource(R));
        }
    }
}
