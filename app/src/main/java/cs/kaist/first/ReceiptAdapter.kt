package cs.kaist.first

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ReceiptAdapter(context: Context, data: ArrayList<ReceiptModel>) : ArrayAdapter<ReceiptModel>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false)

        val model = getItem(position)
        //println(model)
        val companyNameTextView = view.findViewById<TextView>(R.id.companyNameTextView)
        val typeTextView = view.findViewById<TextView>(R.id.typeTextView)
        val priceTextView = view.findViewById<TextView>(R.id.priceTextView)

        companyNameTextView.text = model?.companyName
        typeTextView.text = model?.type
        priceTextView.text = model?.price.toString()

        return view
    }
}