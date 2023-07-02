package cs.kaist.first

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.util.ArrayList

class SubActivity:  AppCompatActivity(){
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

    }
}