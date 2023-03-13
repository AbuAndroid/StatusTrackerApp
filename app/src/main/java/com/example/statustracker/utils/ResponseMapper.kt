package com.example.statustracker.utils

import com.example.statustracker.model.WorkStatusItemModel
import com.example.statustracker.model.WorkStatusListModel
import com.example.statustracker.warehouse.Constants.slack_id
import retrofit2.Response

object ResponseMapper {
    fun workStatusMap(workStatusResponse: Response<WorkStatusListModel>): CustomResponse<List<WorkStatusItemModel?>, LocalException> {
        return if (workStatusResponse.isSuccessful && workStatusResponse.code() == 200) {
            val responseData = workStatusResponse.body()?.progressTaskItem

            responseData?.forEach { workStatusItem ->
                workStatusItem?.palaniSlackId = slack_id[0]
                workStatusItem?.balajiSlackId = slack_id[1]
                workStatusItem?.saranSlackId = slack_id[2]
                workStatusItem?.fazilSlackId = slack_id[3]
                workStatusItem?.maruthuSlackId = slack_id[4]
                workStatusItem?.abdullahSlackId = slack_id[5]
                if (workStatusItem?.palani?.isEmpty() == true) {
                    workStatusItem.palani = "------------"
                }
                if (workStatusItem?.balaji?.isEmpty() == true) {
                    workStatusItem.balaji = "------------"
                }
                if (workStatusItem?.saran?.isEmpty() == true) {
                    workStatusItem.saran = "------------"
                }
                if (workStatusItem?.fazil?.isEmpty() == true) {
                    workStatusItem.fazil = "------------"
                }
                if (workStatusItem?.maruthu?.isEmpty() == true) {
                    workStatusItem.maruthu = "------------"
                }
                if (workStatusItem?.abdullah?.isEmpty() == true) {
                    workStatusItem.abdullah = "------------"
                }
            }
            CustomResponse.Success(responseData ?: arrayListOf())
        } else {
            CustomResponse.Failure(LocalException("Error Server"))
        }
    }
}