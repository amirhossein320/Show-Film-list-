package com.amir.testapp.data.model

import com.google.gson.annotations.SerializedName
data class Request(val request: RequestRaw) {


    data class RequestRaw(
        @SerializedName("RequestType") var requestType: Int = 2,
        @SerializedName("RequestId") var requestId: Int? = null,
        @SerializedName("PageSize") var pageSize: Int = 10,
        @SerializedName("PageIndex") var pageIndex: Int? = null,
        @SerializedName("OrderBy") var orderBy: String = "createdate",
        @SerializedName("Order") var order: String = "desc"
    )
}

