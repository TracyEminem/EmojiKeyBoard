package tracyeminem.com.emojikeyboard

import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_chat.*
import me.drakeet.multitype.MultiTypeAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity(){


    lateinit var items:MutableList<Any>
    var screenHeight = 0
    var statusBarHeight = 0
    var heightKeyBoard = 760
    var shouldDown = true  //是否需要把键盘下沉
    var realHeigh = 0

    var funcHeight = 0
    var funcWidth = 0
    var realFuncHeight = 0

    lateinit var funcItems : MutableList<Any>

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action == MotionEvent.ACTION_DOWN){
            var v = currentFocus
            if(isShouldHideKeyBoard(v,ev)){
                hideKeyBoard(v.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyBoard(token: IBinder){
        if(token != null){
            var im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
            if(!shouldDown && rv_func.visibility == View.VISIBLE){
                rv_func.visibility = View.GONE
                rv.translationY = 0.0f
                ll_edit.translationY = 0.0f
                if(realHeigh == 0)
                    shouldDown = true
            }
        }
    }

    fun isShouldHideKeyBoard(v: View, event: MotionEvent):Boolean{
        if(v != null && (v is EditText)){
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)

            var left = l[0]
            var top = l[1]
            var bottom = top + v.height
            var right = ScreenUtil.getScreenWidth(this)

//            if(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom){
            if(event.getY() > top){
                return false
                //点击区域是TextView键盘区域
            }else{
                return true
            }
        }
        //如果焦点不是Edittext则忽略
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_test)
        screenHeight = ScreenUtil.getScreenHeight(this)
        statusBarHeight = getStatusBarHeight(this)

        funcHeight = heightKeyBoard / 4
        funcWidth = ScreenUtil.getScreenWidth(this) / 4

        if(funcWidth < funcHeight)
            realFuncHeight = funcHeight
        else
            realFuncHeight = funcWidth

        KeyBoardWindow(this).init().setHeightListener(object : KeyBoardWindow.HeightListener{
            override fun onHeightChanged(height: Int) {
                realHeigh = height
                if(!shouldDown)
                    return
                ll_edit.translationY = (-height).toFloat()
                if (height != 0)
                    heightKeyBoard = height
                if ((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard) < rv.computeVerticalScrollRange() && rv.computeVerticalScrollRange() < (screenHeight - statusBarHeight - ll_edit.height - tv_top.height)) {
                    Log.e("EEEE","---CHANGE--1")
                    rv.scrollToPosition(items.size - 1)
                    if (height == 0) {
                        rv.translationY = height.toFloat() //位置回正
                    } else {
                        rv.translationY =
                            (-(rv.computeVerticalScrollRange() - (screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard))).toFloat()
                    }
                } else if ((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - height) < rv.computeVerticalScrollRange()) {
                    Log.e("EEEE","---CHANGE--2")
                    rv.scrollToPosition(items.size - 1)
                    rv.translationY = (-height).toFloat()   //当聊天内容较少时列表不能往上顶。
                }

                if(height == 0 && rv_func.visibility == View.VISIBLE ){
                    rv_func.visibility = View.GONE
                    rv.translationY = 0.0f
                    ll_edit.translationY = 0.0f
                }

            }
        })


        var height = (heightKeyBoard - 40) / 2

        funcItems = ArrayList()
        var funAdapter = MultiTypeAdapter()
        funAdapter.register(KeyBoardFuncAdapter(realFuncHeight,height))
        rv_func.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        rv_func.adapter = funAdapter

        for (i in 0..6){
            funcItems.add(Simple("----"))
        }
        funAdapter.items =  funcItems
        funAdapter.notifyDataSetChanged()


        items = ArrayList()
        var mAdapter = MultiTypeAdapter()
        mAdapter.register(ChatDemoAdapter())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = mAdapter

        for (i in 0..1){
            items.add(Simple("data --"+ i + "---chating----"+i))
        }
        mAdapter.items = items
        mAdapter.notifyDataSetChanged()

        var handler = Handler().postDelayed(Runnable {

            Log.e("EEEEEE","---height---"+rv.height)

        },500)

//        Runnable {
//            Log.e("EEEE","---measure   height---"+rv.height)
//        }




        Log.e("EEEE","---SET--1")
        btn_send.setOnClickListener { v ->

            items.add(Simple(et_edit.text.toString()) )
            mAdapter.items = items
            mAdapter.notifyItemInserted(items.size -1)
            scroll(heightKeyBoard)
//            rv.scrollToPosition(items.size - 1)
            et_edit.setText("")
        }



//        rv.setOnTouchListener(object : View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                Log.e("EEEE","--RECYCLERVIEW---touch--")
//                return true
//            }
//
//        })

        Log.e("EEEE","---SET--2");
        btn_change.setOnClickListener { v ->

            shouldDown = !shouldDown

            if(!shouldDown) {
                var layoutParams = rv_func.layoutParams
                layoutParams.height = heightKeyBoard
                rv_func.layoutParams = layoutParams
                var v = currentFocus
                hideKeyBoard(v.windowToken)
                rv_func.visibility = View.VISIBLE

                ll_edit.translationY = (-heightKeyBoard).toFloat()
                if((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard) < rv.computeVerticalScrollRange() && rv.computeVerticalScrollRange() < (screenHeight - statusBarHeight - ll_edit.height - tv_top.height) ){
                    Log.e("EEEE","---CHANGE--3")
                    rv.scrollToPosition(items.size -1)
                    rv.translationY = (-(rv.computeVerticalScrollRange() - (screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard))).toFloat()

                }else if((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard) < rv.computeVerticalScrollRange()){
                    Log.e("EEEE","---CHANGE--4")
                    rv.scrollToPosition(items.size-1)
                    rv.translationY =  (-heightKeyBoard).toFloat()   //当聊天内容较少时不能往上顶。
                }
            }else{
                var manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (manager != null) manager.showSoftInput(et_edit, 0);
            }
        }

    }

    fun scroll(height:Int){

//        Log.e("EEEEE","--height--"+rv.layoutManager!!.getChildAt(0)!!.height)

        //(150 + rv.computeVerticalScrollRange()) > (screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard)
//        if((rv.computeVerticalScrollRange() + 150) > (screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard)){
//            rv
//        }

        Log.e("EEEEE","--height--is"+(screenHeight - statusBarHeight - ll_edit.height - tv_top.height))

        if ((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard) < rv.computeVerticalScrollRange() && rv.computeVerticalScrollRange() < (screenHeight - statusBarHeight - ll_edit.height - tv_top.height)) {
            Log.e("EEEEE","----IF")
            rv.scrollToPosition(items.size - 1)
            rv.translationY =
                    (-(rv.computeVerticalScrollRange() - (screenHeight - statusBarHeight - ll_edit.height - tv_top.height - heightKeyBoard))).toFloat()

        } else if ((screenHeight - statusBarHeight - ll_edit.height - tv_top.height - height) < rv.computeVerticalScrollRange()) {
            Log.e("EEEEE","--ELSE--IF")
            rv.scrollToPosition(items.size - 1)
            rv.translationY = (-height).toFloat()   //当聊天内容较少时列表不能往上顶。
        }else{
            Log.e("EEEEE","----ELSE")
        }

    }


}
