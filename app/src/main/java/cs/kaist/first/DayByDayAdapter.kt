package cs.kaist.first

import DayByDayModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class DayByDayAdapter(context: Context, data: ArrayList<DayByDayModel>) : ArrayAdapter<DayByDayModel>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.daybyday_item, parent, false)

        val model = getItem(position)
        println(model?.receipts)
        val dateTextView = view.findViewById<TextView>(R.id.dataTextView)
        val totalPriceTextView = view.findViewById<TextView>(R.id.totalPriceTextView)
        val detailListView = view.findViewById<ListView>(R.id.detailListView)

        dateTextView.text = model?.date
        totalPriceTextView.text = model?.total_price.toString()

        detailListView.adapter = ReceiptAdapter(context, model?.receipts ?: arrayListOf())

        return view
    }
}