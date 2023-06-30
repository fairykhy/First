package cs.kaist.first

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val ContactFragment = ContactFragment()
        val GalleryFragment = GalleryFragment()
        val MusicFragment = MusicFragment()

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
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, MusicFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}