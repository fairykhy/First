import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cs.kaist.first.ContactModel
import cs.kaist.first.R

class FavoriteAdapter(private val data: ArrayList<ContactModel>, private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactModel = data[position]
        holder.bind(contactModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favoriteFace: ImageView = itemView.findViewById(R.id.favoriteProfileImageView)

        fun bind(contactModel: ContactModel) {
            favoriteFace.setImageResource(R.drawable.face1)
        }
    }
}
