package com.bano.pospaymentcasestudy


//"returnCode":1000,
//"returnDesc":"success",
//"receiptMsgCustomer":"beko Campaign/n2018",
//"receiptMsgMerchant":"beko Campaign Merchant/n2018",
//"paymentInfoList":[
//{
//    "paymentProcessorID":67,
//    "paymentActionList":[
//    {
//        "paymentType":3,
//        "amount":100,
//        "currencyID":949,
//        "vatRate":800
//    }
//    ]
//}
//],
//"QRdata":"00020153039495403100800201810200821912-01-2018 12:43:24830481-48608800-100#8712AT00000000018901184034178844secureqrsigniturewillbehereinthenearfuture1="

data class PostPayment (
    val returnCode: Int = 1,
    val returnDesc: String = "success",
    val receiptMsgCustomer: String = "beko Campaign/n2018",
    val receiptMsgMerchant: String = "beko Campaign Merchant/n2018",
//    val paymentInfoList: List<PaymentInfo>,
    val qrData: String,
)

data class PaymentInfo (
    val paymentProcessorID: Int = 67,
    val paymentActionList: List<PaymentAction>
)

data class PaymentAction(
    val paymentType: Int = 3,
    val amount: Int,
    val currencyID: Int = 949,
    val vatRate: Int = 800
)