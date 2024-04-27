package com.example.mysampleproject.network.response

import ListViewResponseItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListViewResponse : ArrayList<ListViewResponseItem>()