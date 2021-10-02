package com.example.aliftask.database

import androidx.room.Entity

@Entity(tableName = "cash")
class Data {
    var endDate: String? = null
    var icon: String? = null
    var name: String? = null
    var url: String? = null
    var progress = false

    constructor(progress: Boolean) {
        this.progress = progress
    }

    constructor(name: String, icon: String, endDate: String, url: String) {
        this.icon = icon
        this.endDate = endDate
        this.name = name
        this.url = url
    }

    constructor(name: String, icon: String, endDate: String, url: String, section: Boolean) {
        this.url = url
        this.name = name
        this.endDate = endDate
        this.icon = icon
        this.progress = section
    }

}
