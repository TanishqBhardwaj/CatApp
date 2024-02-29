package com.example.catapp.utils

import com.example.catapp.BuildConfig

object Constants {
    const val BASE_URL = "https://api.thecatapi.com/v1/"
    const val CAT_API_KEY = BuildConfig.CAT_API_KEY
    const val CONNECT_TIMEOUT_IN_SECONDS = 60L
    const val READ_TIMEOUT_IN_SECONDS = 60L
    const val WRITE_TIMEOUT_IN_SECONDS = 60L

    const val PAGE_SIZE = 20
    const val ASC = "ASC"
    const val CAT_ITEM = "CAT_ITEM"
}