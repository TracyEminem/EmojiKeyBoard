package tracyeminem.com.emojikeyboard;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by TracyEminem on 2019-07-18 16:18.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
