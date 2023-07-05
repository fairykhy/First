import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import cs.kaist.first.R
import cs.kaist.first.ReceiptModel
import java.text.NumberFormat
import java.util.Locale

class ReceiptExpandableListAdapter(
    private val context: Context,
    private val dayByDayList: List<DayByDayModel>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = dayByDayList.size

    override fun getChildrenCount(groupPosition: Int): Int = dayByDayList[groupPosition].receipts.size

    override fun getGroup(groupPosition: Int): Any = dayByDayList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any = dayByDayList[groupPosition].receipts[childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.daybyday_item, parent, false)
        val model = getGroup(groupPosition) as DayByDayModel

        val dateTextView = view.findViewById<TextView>(R.id.dataTextView)
        val totalPriceTextView = view.findViewById<TextView>(R.id.totalPriceTextView)

        dateTextView.text = model.date

        // 쉼표 처리와 원 표시 추가
        val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(model.total_price)
        totalPriceTextView.text = "${formattedPrice}원"

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false)
        val model = getChild(groupPosition, childPosition) as ReceiptModel

        val companyNameTextView = view.findViewById<TextView>(R.id.companyNameTextView)
        val typeTextView = view.findViewById<TextView>(R.id.typeTextView)
        val priceTextView = view.findViewById<TextView>(R.id.priceTextView)

        companyNameTextView.text = model.companyName
        typeTextView.text = model.type

        // 쉼표 처리, 원 표시, +,- 기호 추가
        val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(model.price)
        val sign = if (model.price!! >= 0) "+" else "-"
        priceTextView.text = "$sign${formattedPrice}원"

        return view
    }


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}
