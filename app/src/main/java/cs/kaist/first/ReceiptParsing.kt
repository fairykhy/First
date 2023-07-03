package cs.kaist.first

fun ReceiptParsing (text: String) {
    //매장명
    val namearr = arrayListOf<String>("매장", "매장명", "상호", "상호명", "가맹점", "가맹점명")
    for(x: String in namearr){
        if(text.contains(x)){
            val idx = text.indexOf(x)
            val store_name = text[idx+1].toString()

        }
    }

}