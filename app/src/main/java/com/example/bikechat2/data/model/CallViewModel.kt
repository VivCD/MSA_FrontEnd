package com.example.bikechat2.data.model

import androidx.lifecycle.ViewModel
import com.example.bikechat2.data.CallLogic.CallRepository
import com.example.bikechat2.data.api.WebSocketApi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class CallViewModel: ViewModel() {
    private val webSocketApi: WebSocketApi = WebSocketApi()
    private val callRepository = CallRepository(webSocketApi)

    fun connectToRoom(roomId: String, userId: String, onMessageReceived: (String) -> Unit){
        callRepository.connectToRoom(roomId, userId, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                println("WebSocket connected!")
            }
            override fun onMessage(webSocket: WebSocket, text: String){
                super.onMessage(webSocket, text)
                onMessageReceived(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                println("WebSocket error: $:{t.message}")
            }
        })
    }

    fun sendMessage(message: String){
        callRepository.sendMessage(message)
    }
    fun disconnect(){
        callRepository.disconnect()
    }
}