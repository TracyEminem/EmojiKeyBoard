package tracyeminem.com.emojikeyboard

import android.content.Context
import java.lang.reflect.Field

/**
 * Created by TracyEminem on 2019/7/3 下午10:06.
 */

fun getStatusBarHeight(context: Context): Int {
    var c: Class<*>? = null

    var obj: Any? = null

    var field: Field? = null

    var x = 0
    var sbar = 0

    try {

        c = Class.forName("com.android.internal.R\$dimen")

        obj = c!!.newInstance()

        field = c.getField("status_bar_height")

        x = Integer.parseInt(field!!.get(obj).toString())

        sbar = context.getResources().getDimensionPixelSize(x)

    } catch (e1: Exception) {

        e1.printStackTrace()

    }

    return sbar
}
