package cs.kaist.first

data class ReceiptModel(
    var companyName : String ?= null,
    var type : String ?= null,
    var price : Int ?= null
)