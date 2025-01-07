package com.example.bikechat2.data.CallLogic

import com.example.bikechat2.data.api.WebSocketApi
import okhttp3.WebSocketListener

class CallRepository(private val webSocketApi: WebSocketApi) {
    fun connectToRoom(roomId: String, username: String, listener: WebSocketListener){
        webSocketApi.connect(roomId, username, listener)
    }

    fun sendMessage(message: String){
        webSocketApi.sendMessage(message)
    }
    fun disconnect(){
        webSocketApi.disconnect()
    }
}