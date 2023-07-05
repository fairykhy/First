package cs.kaist.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ReceiptFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_receipt, container, false)
        val info_arr = arguments?.getStringArray("result") // index 0: 매장이름, 1: 결제일자, 2: 금액, 3: 결제방법
        val text = view?.findViewById<TextView>(R.id.textView4)
//        println(info_arr?.get(0))
//        println(info_arr?.get(1))
//        println(info_arr?.get(2))
//        println(info_arr?.get(3))

        return view
    }
}