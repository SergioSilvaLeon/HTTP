package com.nearsoft.demo

import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

class ApiFetchApplication

fun main(args: Array<String>) {

    val httpClient: CloseableHttpClient = HttpClients.createDefault()
    try {
        val httpGet = HttpGet("https://api.github.com/repos/octokit/octokit.rb")
        httpGet.addHeader("Accept", "application/vnd.github.v3+json")
        println("Executing request ${httpGet.requestLine}")

        val responseHandler = ResponseHandler<String> {
            val status = it.statusLine.statusCode
            if (status >= 200 && status < 300) {
                val entity = it.entity
                return@ResponseHandler EntityUtils.toString(entity)
            } else {
                throw ClientProtocolException("Unexpected response status: $status")
            }
        }
        val responseBody = httpClient.execute(httpGet, responseHandler)
        println("------------------------------------")
        println(responseBody)
    } finally {
        httpClient.close()
    }

}