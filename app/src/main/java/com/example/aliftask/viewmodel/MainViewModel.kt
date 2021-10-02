package com.example.aliftask.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.aliftask.database.AppDatabase
import com.example.aliftask.database.Data
import com.example.aliftask.network.GuidesApi
import kotlinx.coroutines.*

enum class ApiStatus { LOADING, SUCCESS, ERROR }
class MainViewModel(val database: AppDatabase, application: Application) :
    AndroidViewModel(application) {

    private var _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _data = MutableLiveData<List<Data>>()
    val data: LiveData<List<Data>>
        get() = _data

    private var splittingData = MutableLiveData<List<List<Data>>>()

    private val allData: ArrayList<Data> = arrayListOf()

    var i = 0

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
                    splittingData.value = splitData(data.body()?.data as ArrayList<Data>)
                    setData()
                } else {
                    _apiStatus.value = ApiStatus.ERROR
                }
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
            }
        }
    }

    private fun splitData(list: ArrayList<Data>): List<List<Data>> {

        return list.withIndex().groupBy {
            it.index / 6
        }.map {
            it.value.map {
                it.value
            }
        }

    }

    fun setData() {
        if (i < splittingData.value?.size!!) {
            splittingData.value!![i].forEach {
                allData.add(it)
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        database.dataDao.insertData(it)
                    }
                }

            }
            _data.value = allData
        }
        i++
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class ViewModelFactory(
        private val dataSource: AppDatabase,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(
                    dataSource, application
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}