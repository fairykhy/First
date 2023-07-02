package cs.kaist.first

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.io.File
import java.util.ArrayList


class SlideAdapter(val list: ArrayList<String>, val viewpg: ViewPager): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.activity_photo, container, false)
        val photo: ImageView = view.findViewById(R.id.full_photo)
        photo.setImageURI(Uri.parse(list[position]))
        container.addView(view)

        val btn_share = view.findViewById<ImageView>(R.id.shareView)
        val btn_bin = view.findViewById<ImageView>(R.id.binView)

        btn_share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            val context = photo.context
            intent.type = ("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(list[position]))
            val chooser = Intent.createChooser(intent, "Share image")
            context.startActivity(chooser)
        }

        btn_bin.setOnClickListener{

            list.remove(list[position])
            viewpg.removeView(view)
            this.notifyDataSetChanged()

//
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

        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


}