package cs.kaist.first

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.util.ArrayList

class SubActivity:  AppCompatActivity(){

//    private val callback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            val context
//            val intent1 = Intent(this, MainActivity::class.java)
//
//
//        }
//    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_photo)
        val secintent = intent
        setContentView(R.layout.slide_background)
        val datas: ArrayList<String> = secintent.getStringArrayListExtra("num") as ArrayList<String>
        val index = datas?.last()?.toInt()
        datas?.removeLast()
        val viewpg: ViewPager = findViewById(R.id.mViewPager);
//        imageView1.setImageURI(Uri.parse(index));
        val adapter = SlideAdapter(datas, viewpg)
        viewpg.adapter = adapter
        if (index != null) {
            viewpg.currentItem = index
        }

        var idx = -1
        val share : ImageView = findViewById<ImageView>(R.id.shareView)
        val bin : ImageView = findViewById<ImageView>(R.id.binView)
        val back: ImageView = findViewById<ImageView>(R.id.back_button)



        share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
//            val context = photo.context
            intent.type = ("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(datas[viewpg.currentItem]))
            val chooser = Intent.createChooser(intent, "Share image")
            startActivity(chooser)
        }

        bin.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putInt("index", viewpg.currentItem)
//            GalleryFragment().arguments = bundle
            idx = viewpg.currentItem
            datas.remove(datas[viewpg.currentItem])
            adapter.notifyDataSetChanged()




//            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply{
//                addCategory(Intent.CATEGORY_OPENABLE)
//                type = "image/*"
//            }
//            val context = photo.context
//            intent.type = ("image/*")
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(list[position]))
//            context.startActivity(intent)
//            DocumentsContract.deleteDocument(context.contentResolver, Uri.parse(list[position]))
//            Toast.makeText(context.applicationContext, "Done deleting an image", Toast.LENGTH_SHORT).show()
//            val path = (Uri.parse(list[position])).path
//            val resolver = context.contentResolver
//            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0x1033)
//            resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA+"=?",
//                arrayOf(path)
//            )
//            photo.setImageResource(0)
//            resolver.delete(uri, null, null)
//            println(path)
//            val file = File(path)
//            file.delete()
//            if(bundle!=null){
//
//                this.onBackPressedDispatcher.addCallback(this, callback)
//            }
        }
        back.setOnClickListener(){

            val intent1 = Intent(this, MainActivity::class.java)
            intent1.putExtra("check", 10)
            intent1.putExtra("index", idx)
            startActivity(intent1)
        }

    }
}