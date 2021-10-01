package com.example.aliftask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliftask.database.Data
import com.example.aliftask.network.GuidesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, SUCCESS, ERROR }
class MainViewModel : ViewModel() {

    private var _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _data = MutableLiveData<ArrayList<Data>>()
    val data: LiveData<ArrayList<Data>>
        get() = _data

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getData()
    }


    private fun getData() {
        coroutineScope.launch {
            val properties = GuidesApi.retrofitService.getGuidesListAsync()
            try {
                _apiStatus.value = ApiStatus.LOADING
                val data = properties.await()
                if (data.isSuccessful) {
                    _apiStatus.value = ApiStatus.SUCCESS
                    _data.value = data.body()?.data as ArrayList<Data>?
                } else {
                    _apiStatus.value = ApiStatus.ERROR
                }
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}