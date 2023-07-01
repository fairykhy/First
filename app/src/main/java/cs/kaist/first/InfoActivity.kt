package cs.kaist.first

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import org.w3c.dom.Text

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val infoIntent = intent

        val name = infoIntent.getStringExtra("name")
        val number = infoIntent.getStringExtra("number")
        val email = infoIntent.getStringExtra("email")
        val group = infoIntent.getStringExtra("group")
        val id = infoIntent.getStringExtra("id")
        val thumnailId = infoIntent.getStringExtra("thumnail")
        val nameInfoTextView = findViewById<TextView>(R.id.nameInfoTextView)
        val numberInfoTextView = findViewById<TextView>(R.id.numberInfoTextView)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val shareTextView = findViewById<TextView>(R.id.shareTextView)


        if(thumnailId!=null){
            profileImageView.clipToOutline = true
            profileImageView.setImageURI(thumnailId.toUri())
        }
        else {
            profileImageView.clipToOutline = true
            profileImageView.setImageResource(R.drawable.user)
        }

        val callImageView = findViewById<ImageView>(R.id.callImageView)
        val messengetImageView = findViewById<ImageView>(R.id.MessengerImageView)
        val deleteTextView = findViewById<TextView>(R.id.deleteTextView)


        callImageView.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:"+number)
            startActivity(dialIntent)
        }

        messengetImageView.setOnClickListener{
            val messengertIntent = Intent(Intent.ACTION_SENDTO)
            messengertIntent.data = Uri.parse( "smsto:"+number)
            startActivity(messengertIntent)
        }

        shareTextView.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val text = "친구에"

            shareIntent.putExtra(Intent.EXTRA_TEXT,text)
            val chooser = Intent.createChooser(shareIntent,"친구에게 공유하기")
            startActivity(chooser)
        }

        deleteTextView.setOnClickListener {
            contentResolver.delete(ContactsContract.RawContacts.CONTENT_URI,"${ContactsContract.RawContacts.CONTACT_ID}=?", arrayOf(id.toString()))

        }

        nameInfoTextView.text = name
        numberInfoTextView.text = number


    }
}
