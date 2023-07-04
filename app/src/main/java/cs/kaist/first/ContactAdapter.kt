package cs.kaist.first

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import org.w3c.dom.Text

class ContactAdapter(
    val data: ArrayList<ContactModel>,
    val context: Context
): RecyclerView.Adapter<ContactAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return Holder(itemview)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder.firstTextview?.text  = data[position]?.name?.get(0).toString()
        //holder.nameTextView?.text = data[position]?.name
        val position = holder.getAdapterPosition()
        if (data[position].thumnail != null) {
            val thumbnailUri = Uri.parse(data[position].thumnail)
            Glide.with(holder.itemView.context)
                .load(thumbnailUri)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                    ) {
                        resource.setBounds(0, 0, 50, 50)  // 이곳에 원하는 크기를 입력하세요
                        holder.firstTextview.setCompoundDrawables(null, resource, null, null)
                        println(position)
                        holder.firstTextview.text = ""
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Optional: Implement if needed
                    }
                })
        } else {
            holder.firstTextview.text = data[position].name?.get(0).toString()
        }

        holder.nameTextView.text = data[position].name
        holder.numberTextView.text = data[position].number.toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, InfoActivity::class.java)
            intent.putExtra("name",data[position].name.toString())
            intent.putExtra("number",data[position].number.toString())
            intent.putExtra("email",data[position].email.toString())
            intent.putExtra("group",data[position].group.toString())
            intent.putExtra("thumnail",data[position].thumnail)
            intent.putExtra("id",data[position].id.toString())
            intent.putExtra("memo",data[position].memo.toString())
            intent.putExtra("position",position.toInt())
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }
    }

    fun updateMemo(position: Int, memo: String) {
        data[position].memo = memo
        notifyItemChanged(position)
    }
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val firstTextview = itemView.findViewById<TextView>(R.id.firstTextView)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val numberTextView = itemView.findViewById<TextView>(R.id.numberTextView)

    }



}