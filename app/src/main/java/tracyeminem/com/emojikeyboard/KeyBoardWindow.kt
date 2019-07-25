package tracyeminem.com.emojikeyboard

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow

/**
 * Created by TracyEminem on 2019/7/3 上午11:13.
 */
class KeyBoardWindow(val mActivity:Activity) : PopupWindow(mActivity),ViewTreeObserver.OnGlobalLayoutListener{



    var rootView: View?= null
    var heightListener :HeightListener ?= null
    var heightMax : Int = 0


    init {
        //基础配置
        rootView = View(mActivity)
        contentView =  rootView

        //监听全局Layout变化
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
        setBackgroundDrawable(ColorDrawable(0))

        //设置宽度为0，高度为全屏
        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT

        //设置键盘弹出方式
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED

    }


    fun init() : KeyBoardWindow{

        if(!isShowing){
            var view = mActivity.window.decorView

            view.post(object :Runnable{
                override fun run() {
                    showAtLocation(view,Gravity.NO_GRAVITY,0,0)
                }

            })
        }
        return this
    }

    fun setHeightListener(listener:HeightListener) : KeyBoardWindow{
        this.heightListener = listener
        return this
    }

    interface HeightListener{
        fun onHeightChanged(height:Int)
    }

    override fun onGlobalLayout() {
        var rect = Rect()
        rootView?.getWindowVisibleDisplayFrame(rect)
        if(rect.bottom > heightMax){
            heightMax = rect.bottom
        }

        //两者差距是键盘的高度
        var keyBoardHeight = heightMax - rect.bottom
        if(heightListener != null){
            heightListener?.onHeightChanged(keyBoardHeight)
        }

    }


}