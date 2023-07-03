package cs.kaist.first

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val intent = intent
        val check = intent.getIntExtra("check", 0)
        if(check == 10){
            val idx = intent.getIntExtra("index", -1)
            if(idx!=-1){
                val bundle = Bundle()
                bundle.putInt("index", idx)
//                GalleryFragment().arguments = bundle
//                println(GalleryFragment().arguments)
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, GalleryFragment().apply{
                    arguments = bundle
                }).commit()

            }
            else{
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, GalleryFragment()).commit()
            }

            return
        }
        supportFragmentManager.beginTransaction().add(R.id.main_frame, ContactFragment()).commit()

        bottomNavigationView.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.contactItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, ContactFragment()).commit()
                    true
                }

                R.id.galleryItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, GalleryFragment()).commit()
                    true
                }

                R.id.musicItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, ReceiptFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        ContactFragment().refreshAdapter()
    }
}