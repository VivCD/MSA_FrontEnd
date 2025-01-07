package com.example.bikechat2.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class WebSocketApi(){
    private var webSocket: WebSocket? = null;
    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    fun connect(roomId: String, username: String, listener: WebSocketListener){
        val baseUrl = RetrofitInstance.BASE_URL.replace("https://", "wss://")
        val request = Request.Builder()
            .url("$baseUrl/signaling?roomId=$roomId&username=$username")
            .build()

        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String){
        webSocket?.send(message)
    }

    fun disconnect(){
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }
}