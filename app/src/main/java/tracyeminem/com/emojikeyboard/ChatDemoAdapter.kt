package tracyeminem.com.emojikeyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat_demo.view.*
import me.drakeet.multitype.ItemViewBinder


/**
 * Created by TracyEminem on 2019/7/3 下午3:30.
 */
open class ChatDemoAdapter : ItemViewBinder<Simple, ChatDemoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_chat_demo,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Simple) {
            holder.itemView.tv_cout.setText(item.value)
            holder.itemView.tv_cout.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {

                }

            })
    }


    open class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}