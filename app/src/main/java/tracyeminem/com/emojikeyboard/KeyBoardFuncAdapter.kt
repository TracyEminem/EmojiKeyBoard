package tracyeminem.com.emojikeyboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat_func.view.*
import me.drakeet.multitype.ItemViewBinder


/**
 * Created by TracyEminem on 2019/7/9 下午2:10.
 */
class KeyBoardFuncAdapter(var width:Int,var height:Int) : ItemViewBinder<Simple, KeyBoardFuncAdapter.ViewHolder>() {

    lateinit var context:Context
//    var  = 0;




    override fun onBindViewHolder(holder: ViewHolder, item: Simple) {

        var params = holder.itemView.csl_func.layoutParams
        params.height = height
        params.width = width
        holder.itemView.csl_func.layoutParams = params


        var picParams = holder.itemView.sdv_func.layoutParams
        picParams.width = width - 80
        picParams.height = height - 80
        holder.itemView.sdv_func.layoutParams = picParams


        holder.itemView.setOnClickListener{
            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        context = parent.context
        return ViewHolder(inflater.inflate(R.layout.item_chat_func,parent,false))
    }


    open class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
}