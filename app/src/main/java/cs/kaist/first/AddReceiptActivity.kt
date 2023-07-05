package cs.kaist.first

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip


class AddReceiptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)


        val chip = findViewById<Chip>(R.id.chip4)

        chip.setOnClickListener {
            val color = getResources().getColor(R.color.chip)
            val colorStateList = ColorStateList.valueOf(color)

            chip.setTextColor(color)
            chip.chipStrokeColor = colorStateList
        }
    }
}