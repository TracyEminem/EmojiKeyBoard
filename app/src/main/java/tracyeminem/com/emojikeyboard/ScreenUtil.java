package tracyeminem.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by TracyEminem on 2019/7/3 下午2:16.
 */
public class ScreenUtil {
    public static int getScreenWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }
}
