package cs.kaist.first

data class ContactModel(
    var name : String ?= null,
    var number : String ?= null,
    var email : String ?= null,
    var group : String ?= null,
    val thumnail : String ?= null
)