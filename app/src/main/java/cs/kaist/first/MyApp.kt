package cs.kaist.first

import DayByDayModel
import android.app.Application
import java.io.Serializable

class MyApp:Application() {

    var dayByDayData: ArrayList<DayByDayModel> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        dayByDayData = ArrayList<DayByDayModel>().apply {
            add(DayByDayModel().apply {
                date = "7월 1일"
                total_price = 25000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    }
                )
            })

            add(DayByDayModel().apply {
                date = "7월 2일"
                total_price = 30000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 10000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    }
                )
            })
            add(DayByDayModel().apply {
                date = "7월 3일"
                total_price = 30000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 10000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "결제"
                        price = 5000
                    }
                )
            }
                )
        }

    }
}

