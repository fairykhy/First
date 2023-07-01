package cs.kaist.first

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val thumnailId = infoIntent.getStringExtra("thumnail")
        val nameInfoTextView = findViewById<TextView>(R.id.nameInfoTextView)
        val numberInfoTextView = findViewById<TextView>(R.id.numberInfoTextView)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)

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

        nameInfoTextView.text = name
        numberInfoTextView.text = number


    }
}