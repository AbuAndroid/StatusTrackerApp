package com.example.statustracker.model

import com.google.gson.annotations.SerializedName

data class WorkStatusListModel(
    @SerializedName("items")
    val progressTaskItem:List<WorkStatusItemModel?>,

)

data class WorkStatusItemModel(
    val isLoading:Boolean=true,
    var palaniSlackId:String?,
    var balajiSlackId:String?,
    var saranSlackId:String?,
    var fazilSlackId:String?,
    var maruthuSlackId:String?,
    var abdullahSlackId:String?,
    val date:String?,
    var palani:String?,
    var balaji:String?,
    var saran:String?,
    var fazil:String?,
    var maruthu:String?,
    val lastUpdate:Long?,
    var abdullah:String?
)

data class AlertOption(
    var isSelected:Boolean,
    val optionText:String
)

data class SlackInputModel(
    val pretext:String?,
    val username:String?,
    val text:String?,
    val channel:String?
)

