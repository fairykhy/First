package cs.kaist.first

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.lang.System.load
import java.net.URI
import java.net.URL

class GalleryRecyclerAdapter(val datas: ArrayList<String>, val context: Context) : RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.imglist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgOne.setImageURI(Uri.parse(datas[position]))

        holder.itemView.setOnClickListener({
            val intent = Intent(holder.itemView?.context, SubActivity::class.java)
            datas.add(position.toString())
            intent.putExtra("num", datas)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        })

    }

    override fun getItemCount(): Int = datas.size


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgOne: ImageView = itemView.findViewById(R.id.photo1)
    }

}