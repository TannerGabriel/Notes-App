package com.example.android.noteapp.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val title: String, val detail: String, val imageUrl: String, val id: String): Parcelable{
    constructor(): this("","", "", "")
}