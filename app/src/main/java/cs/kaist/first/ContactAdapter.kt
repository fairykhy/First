package cs.kaist.first

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ContactAdapter(val data: ArrayList<ContactModel>, val context: Context): RecyclerView.Adapter<ContactAdapter.Holder>(){

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
        holder.firstTextview.text = data[position].name?.get(0).toString()
        holder.nameTextView.text = data[position].name
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, InfoActivity::class.java)
            intent.putExtra("name",data[position].name.toString())
            intent.putExtra("number",data[position].number.toString())
            intent.putExtra("email",data[position].email.toString())
            intent.putExtra("group",data[position].group.toString())
            intent.putExtra("thumnail",data[position].thumnail)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val firstTextview = itemView.findViewById<TextView>(R.id.firstTextView)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)

    }
}