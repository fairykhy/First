package cs.kaist.first

import DayByDayModel
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.chip.Chip


class AddReceiptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)
        var companyname = ""
        var type = ""
        val chip1 = findViewById<Chip>(R.id.chip4)
        //val date = "7월 3일"
        val button = findViewById<Button>(R.id.myButton)
        val price = intent.getStringExtra("price")
        val company = intent.getStringExtra("company")

        val money = findViewById<EditText>(R.id.money)
        val place = findViewById<EditText>(R.id.place)

        money.setText(price)
        place.setText(company)
        button.setOnClickListener {
            //val money = findViewById<EditText>(R.id.money)
            //val place = findViewById<EditText>(R.id.place)
            //price = Integer.parseInt(money.text.toString())
            //companyname = place.text.toString()
            val myApp = this.applicationContext as MyApp
            myApp.count ++
            finish()
        }

            chip1.setOnClickListener {
                val color = getResources().getColor(R.color.chip)
                val colorStateList = ColorStateList.valueOf(color)
                type = "지출"
                chip1.setTextColor(color)
                chip1.chipStrokeColor = colorStateList
            }
        }

}