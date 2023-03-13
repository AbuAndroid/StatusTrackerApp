package com.example.statustracker.repository

import com.example.statustracker.model.SlackInputModel
import com.example.statustracker.model.WorkStatusItemModel
import com.example.statustracker.network.GoogleSheetService
import com.example.statustracker.utils.CustomResponse
import com.example.statustracker.utils.LocalException
import com.example.statustracker.utils.ResponseMapper
import com.example.statustracker.warehouse.Constants.SLACK_URL

class WorkStatusRepository(private val googleSheetService: GoogleSheetService) {

    suspend fun getWorkStatusList(): CustomResponse<List<WorkStatusItemModel?>, LocalException> =
        ResponseMapper.workStatusMap(
            googleSheetService.getWorkStatusList("get")
        )

    suspend fun updateStatusToSlack(slackInputModel: SlackInputModel)  {
       googleSheetService.sendTaskListToSlack(
           url = SLACK_URL,
           slackInputModel = slackInputModel
       )
    }

    suspend fun sendAlertMessageIndividual(slackIndividualMessageModel: SlackInputModel){
        googleSheetService.sendTaskListToSlack(
            url = SLACK_URL,
            slackInputModel = slackIndividualMessageModel
        )
    }
}