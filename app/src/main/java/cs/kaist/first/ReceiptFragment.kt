package cs.kaist.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ReceiptFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val info_arr = arguments?.getStringArrayList("result") // index 0: 매장이름, 1: 결제일자, 2: 금액, 3: 결제방법

        return view
    }
}