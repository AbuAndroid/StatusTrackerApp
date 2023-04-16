package com.example.statustracker.ui.homescreen

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statustracker.manager.DataStoreManager
import com.example.statustracker.model.AlertOption
import com.example.statustracker.model.SlackInputModel
import com.example.statustracker.repository.WorkStatusRepository
import com.example.statustracker.utils.CustomResponse
import com.example.statustracker.utils.Networkhelper
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val networkHelper: Networkhelper,
    private val repository: WorkStatusRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val viewModelState = MutableStateFlow(ProgressViewModelState())

    val uiState = viewModelState.map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())

    private val statusNotEnteredList = mutableListOf<String>()

    var alertOptions by mutableStateOf(
        listOf(
            AlertOption(
                isSelected = false,
                optionText = "Channel"
            ),
            AlertOption(
                isSelected = false,
                optionText = "Individual"
            )
        )
    )

    init {
        fetchWorkStatus()
    }

    fun refresh() = viewModelScope.launch {
        _isRefreshing.value = true
        delay(2000)
        fetchWorkStatus()
        _isRefreshing.value = false
    }

    private fun fetchWorkStatus() {
        viewModelState.update { it.copy(isLoading = true) }
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                when (val response = repository.getWorkStatusList()) {
                    is CustomResponse.Success -> {
                        val todayWorkStatus = response.data[response.data.size-2]
                        Log.e("date",response?.data[response.data.size-2].toString())
                        statusNotEnteredList.clear()
                        if (todayWorkStatus?.palani?.isEmpty() == true) {
                            todayWorkStatus.palaniSlackId?.let { statusNotEnteredList.add(it) }
                        }
                        if (todayWorkStatus?.balaji?.isEmpty() == true) {
                            todayWorkStatus.balajiSlackId?.let { statusNotEnteredList.add(it) }
                        }
                        if (todayWorkStatus?.saran?.isEmpty() == true) {
                            todayWorkStatus.saranSlackId?.let { statusNotEnteredList.add(it) }
                        }
                        if (todayWorkStatus?.fazil?.isEmpty() == true) {
                            todayWorkStatus.fazilSlackId?.let { statusNotEnteredList.add(it) }
                        }
                        if (todayWorkStatus?.maruthu?.isEmpty() == true) {
                            todayWorkStatus.maruthuSlackId?.let { statusNotEnteredList.add(it) }
                        }
                        if (todayWorkStatus?.abdullah?.isEmpty() == true) {
                            todayWorkStatus.abdullahSlackId?.let { statusNotEnteredList.add(it) }
                        }

                        viewModelState.update {
                            it.copy(
                                palaniSlackId = todayWorkStatus?.palaniSlackId,
                                balajiSlackId = todayWorkStatus?.balajiSlackId,
                                saranSlackId = todayWorkStatus?.saranSlackId,
                                fazilSlackId = todayWorkStatus?.fazilSlackId,
                                maruthuSlackId = todayWorkStatus?.maruthuSlackId,
                                abdullahSlackId = todayWorkStatus?.abdullahSlackId,
                                date = todayWorkStatus?.date,
                                palani = todayWorkStatus?.palani,
                                abdullah = todayWorkStatus?.abdullah,
                                fazil = todayWorkStatus?.fazil,
                                balaji = todayWorkStatus?.balaji,
                                maruthu = todayWorkStatus?.maruthu,
                                saran = todayWorkStatus?.saran,
                                lastUpdate = System.currentTimeMillis(),
                                isLoading = false
                            )
                        }
                    }
                    is CustomResponse.Failure -> {

                    }
                }.also { viewModelState.update { it.copy(isLoading = false) } }
            }
        }
    }

    fun onAlertOptionSelected() {
        viewModelState.update {
            it.copy(
                lastUpdate = System.currentTimeMillis(),
            )
        }
    }

    fun onSubmitTask() {
        viewModelScope.launch {
            viewModelState.value.date?.let { dataStoreManager.saveCurrentDateFromSheet(it) }
            dataStoreManager.getCurrentDate.collect{ dateFromPreference->
                if (dateFromPreference != null) {
                    viewModelState.update {
                        it.copy(isVerified = dateFromPreference.isNotEmpty())
                    }
                }
            }
            repository.updateStatusToSlack(getSlackInputModel())
        }
    }

    private fun getSlackInputModel(isAlert: Boolean = false): SlackInputModel {
        return SlackInputModel(
            pretext = "cc- @Muthuram Saran",
            username = "DigiClass - Mobile Team daily status",
            text = "Palani - ${viewModelState.value.palani}.\n Balaji - ${viewModelState.value.balaji}. \n Saran - ${viewModelState.value.saran}.\n Fazil -  ${viewModelState.value.fazil}.\n Maruthu -  ${viewModelState.value.maruthu}. \n Abdullah -  ${viewModelState.value.abdullah}.",
            channel = if (isAlert) "#test-dailyprogress" else "#mobile"
        )
    }

    fun sendAlertToSlack() {
        alertOptions.forEachIndexed { position, alertOption ->
            if (alertOption.isSelected) {
                if (position == 0) {
                    sendAlertToSlackChannel()
                } else {
                    sendAlertToSlackIndividual()
                }
            }
        }
    }

    private fun sendAlertToSlackChannel() {
        viewModelScope.launch {
            repository.updateStatusToSlack(getSlackInputModel(true))
        }
    }

    private fun sendAlertToSlackIndividual() {
        viewModelScope.launch {
            statusNotEnteredList.forEach { slackId ->
                val response = async {
                    repository.sendAlertMessageIndividual(getSendAlertIndividualModel(slackId))
                }
                response.await()
            }
        }
    }

    private fun getSendAlertIndividualModel(slackId: String): SlackInputModel {
        return SlackInputModel(
            pretext = "cc- @Muthuram Saran",
            username = "DigiClass - Mobile Team daily status",
            text = "Update your daily task at end of the day in sheet! <https://docs.google.com/spreadsheets/d/1xz6MUQVDOeTzZeKHbBlbW0n2wWvxOO6Y-H2Jp2COXcU/edit#gid=0%7CClick here> for update!",
            channel = "@$slackId"
        )
    }
}

data class ProgressViewModelState(
    val slackId: String? = "0",
    val date: String? = "",
    val palani: String? = "",
    val balaji: String? = "",
    val saran: String? = "",
    val fazil: String? = "",
    val maruthu: String? = "",
    val abdullah: String? = "",
    val palaniSlackId: String? = "",
    val balajiSlackId: String? = "",
    val saranSlackId: String? = "",
    val fazilSlackId: String? = "",
    val maruthuSlackId: String? = "",
    val abdullahSlackId: String? = "",
    val isLoading: Boolean = true,
    val lastUpdate: Long = System.currentTimeMillis(),
    val isVerified: Boolean = false
) {
    fun toUiState(): ProgressViewUiModel {
        return ProgressViewUiModel(
            slackId = slackId,
            date = date,
            palani = palani,
            balaji = balaji,
            saran = saran,
            fazil = fazil,
            maruthu = maruthu,
            abdullah = abdullah,
            isLoading = isLoading,
            lastUpdate = lastUpdate,
            palaniSlackId = palaniSlackId,
            balajiSlackId = balajiSlackId,
            saranSlackId = saranSlackId,
            fazilSlackId = fazilSlackId,
            maruthuSlackId = maruthuSlackId,
            abdullahSlackId = abdullahSlackId,
            isVerified = isVerified
        )
    }
}

data class ProgressViewUiModel(
    val slackId: String?,
    val date: String?,
    val palani: String?,
    val balaji: String?,
    val saran: String?,
    val fazil: String?,
    val maruthu: String?,
    val abdullah: String?,
    val isLoading: Boolean,
    val lastUpdate: Long,
    val palaniSlackId: String?,
    val balajiSlackId: String?,
    val saranSlackId: String?,
    val fazilSlackId: String?,
    val maruthuSlackId: String?,
    val abdullahSlackId: String?,
    val isVerified: Boolean?
)