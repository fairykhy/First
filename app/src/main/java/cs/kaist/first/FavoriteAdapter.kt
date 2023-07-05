import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import cs.kaist.first.ContactModel
import cs.kaist.first.R

class FavoriteAdapter(private val data: ArrayList<ContactModel>, private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favoriteImageView.clipToOutline = true
        holder.favoriteImageView.setImageURI(data[position].thumnail?.toUri())
        holder.favoriteName.text = data[position].name
        holder.favoriteNumber.text = data[position].number
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val favoriteFace: ImageView = itemView.findViewById(R.id.favoriteProfileImageView)


            val favoriteImageView = itemView.findViewById<ImageView>(R.id.favoriteProfileImageView)
            val favoriteName = itemView.findViewById<TextView>(R.id.textView2)
            val favoriteNumber = itemView.findViewById<TextView>(R.id.textView3)

    }
}
