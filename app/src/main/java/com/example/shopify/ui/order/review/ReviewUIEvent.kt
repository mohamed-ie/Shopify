package com.example.shopify.ui.order.review

sealed interface ReviewUIEvent {
    class SendTitle(val value:String) : ReviewUIEvent
    class SendContent(val value:String) : ReviewUIEvent
    object Submit : ReviewUIEvent
    object Close : ReviewUIEvent
    class View(val orderIndex:Int,val lineIndex:Int) : ReviewUIEvent
    class SendRate(val value:Int) : ReviewUIEvent
}

