package cs.kaist.first

import DayByDayModel
import android.app.Application
import java.io.Serializable

class MyApp:Application() {

    var dayByDayData: ArrayList<DayByDayModel> = ArrayList()
    var test : ArrayList<DayByDayModel> = ArrayList()
    var count = 0
    override fun onCreate() {
        super.onCreate()

        dayByDayData = ArrayList<DayByDayModel>().apply {
            add(DayByDayModel().apply {
                date = "7월 1일"
                total_price = 28150
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "탐앤탐스"
                        type = "지출"
                        price = -2450
                    },
                    ReceiptModel().apply {
                        companyName = "우리체크캐시백"
                        type = "수입"
                        price = 2000
                    },
                    ReceiptModel().apply {
                        companyName = "주식회사 티머니"
                        type = "지출"
                        price = -7700
                    },
                    ReceiptModel().apply {
                        companyName = "우아한 형제들"
                        type = "지출"
                        price = -17000
                    }
                )
            })

            add(DayByDayModel().apply {
                date = "7월 2일"
                total_price = 30000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "페이코"
                        type = "이체"
                        price = 20000
                    },
                    ReceiptModel().apply {
                        companyName = "쿠팡 주식회사"
                        type = "지출"
                        price = -27130
                    },
                    ReceiptModel().apply {
                        companyName = "한국과학기술원"
                        type = "수입"
                        price = 145000
                    },
                    ReceiptModel().apply {
                        companyName = "다이소"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "투썸플레이스"
                        type = "지출"
                        price = -5300
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
                        companyName = "페이코"
                        type = "이체"
                        price = 10000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    }
                )
            }
            )
        }




        test = ArrayList<DayByDayModel>().apply {
            add(DayByDayModel().apply {
                date = "7월 1일"
                total_price = 28150
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 5000
                    },
                    ReceiptModel().apply {
                        companyName = "탐앤탐스"
                        type = "지출"
                        price = -2450
                    },
                    ReceiptModel().apply {
                        companyName = "우리체크캐시백"
                        type = "수입"
                        price = 2000
                    },
                    ReceiptModel().apply {
                        companyName = "주식회사 티머니"
                        type = "지출"
                        price = -7700
                    },
                    ReceiptModel().apply {
                        companyName = "우아한 형제들"
                        type = "지출"
                        price = -17000
                    }
                )
            })

            add(DayByDayModel().apply {
                date = "7월 2일"
                total_price = 30000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "페이코"
                        type = "이체"
                        price = 20000
                    },
                    ReceiptModel().apply {
                        companyName = "쿠팡 주식회사"
                        type = "지출"
                        price = -27130
                    },
                    ReceiptModel().apply {
                        companyName = "한국과학기술원"
                        type = "수입"
                        price = 145000
                    },
                    ReceiptModel().apply {
                        companyName = "다이소"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "투썸플레이스"
                        type = "지출"
                        price = -5300
                    }

                )
            })
            add(DayByDayModel().apply {
                date = "7월 3일"
                total_price = -30000
                receipts = arrayListOf(
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "이체"
                        price = 10000
                    },
                    ReceiptModel().apply {
                        companyName = "페이코"
                        type = "이체"
                        price = 10000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "카카오페이"
                        type = "지출"
                        price = -5000
                    },
                    ReceiptModel().apply {
                        companyName = "IT융합센터매점"
                        type = "지출"
                        price = -1300
                    }

                )
            }
            )


        }
    }
}

