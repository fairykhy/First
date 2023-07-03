package cs.kaist.first

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import org.w3c.dom.Text

class InfoActivity : AppCompatActivity() {
    var position = 0
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
        val memo = infoIntent.getStringExtra("memo")
        position = infoIntent.getIntExtra("position",0)
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
        val memoTextView = findViewById<TextView>(R.id.memoEditText)


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

        memoTextView.text = memo
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력이 변경되기 전에 호출됩니다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력이 변경될 때마다 호출됩니다.
            }

            override fun afterTextChanged(s: Editable?) {
                val now_memo = memoTextView.text

                val values = ContentValues()
                values.put(ContactsContract.CommonDataKinds.Note.NOTE,now_memo.toString())
                val selection =
                    "${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
                val selectionArgs = arrayOf(
                    id,
                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE
                )

                contentResolver.update(
                    ContactsContract.Data.CONTENT_URI,
                    values,
                    selection,
                    selectionArgs
                )

                val resultIntent = Intent()
                resultIntent.putExtra("memo2", now_memo)
                resultIntent.putExtra("position2", position)
                setResult(Activity.RESULT_OK, resultIntent)

            }
        }
        memoTextView.addTextChangedListener(textWatcher)

        shareTextView.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val text = "이름 : " + name + "전화번호 : " + number

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
