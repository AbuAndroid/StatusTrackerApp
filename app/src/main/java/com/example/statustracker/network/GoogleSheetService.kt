package com.example.statustracker.network

import com.example.statustracker.model.SlackInputModel
import com.example.statustracker.model.WorkStatusListModel
import retrofit2.Response
import retrofit2.http.*

interface GoogleSheetService {

    @GET("macros/s/AKfycbzVwS1crqQ0eEw6dP7mkVVgQL_0WJyXlV5t19l05QRFuiXYzR6HorygCzrLWle_6xN1Gw/exec")
    suspend fun getWorkStatusList(@Query("action") action: String): Response<WorkStatusListModel>

    @POST
    suspend fun sendTaskListToSlack(
        @Url url:String,
        @Body slackInputModel: SlackInputModel,
    )
}