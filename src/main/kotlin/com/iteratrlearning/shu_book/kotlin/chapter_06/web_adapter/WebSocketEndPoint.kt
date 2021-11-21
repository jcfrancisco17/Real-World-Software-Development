package com.iteratrlearning.shu_book.kotlin.chapter_06.web_adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.iteratrlearning.shu_book.kotlin.chapter_06.*
import org.java_websocket.WebSocket

class WebSocketEndPoint(private val twootr: Twootr, private val webSocket: WebSocket) : ReceiverEndpoint {

    private var senderEndPoint: SenderEndPoint? = null

    companion object {
        private val mapper = ObjectMapper()
        private const val CMD = "cmd"
    }

    override fun onTwoot(twoot: Twoot) {
        webSocket.send(
            """
            {"cmd":"twoot", "user":"${twoot.senderId}", "msg":"${twoot.content}"}
        """.trimIndent()
        )
    }

    fun onMessage(message: String) {
        val json = mapper.readTree(message)
        when (json.get(CMD).asText()) {
            "logon" -> {
                val userName = json.get("userName").asText()
                val password = json.get("password").asText()
                val endPoint = twootr.onLogon(userName, password, this)
                endPoint?.run { senderEndPoint = endPoint } ?: run {
                    senderEndPoint = null
                    webSocket.close()
                }
            }

            "follow" -> {
                val userName = json.get("userName").asText()
                sendStatusUpdate(senderEndPoint!!.onFollow(userName).toString())
            }

            "sendTwoot" -> {
                val id = json.get("id").asText()
                val content = json.get("content").asText()
                sendPosition(senderEndPoint!!.onSendTwoot(id, content))
            }

            "deleteTwoot" -> {
                val id = json.get("id").asText()
                sendStatusUpdate(senderEndPoint!!.onDeleteTwoot(id).toString())
            }
        }

    }

    private fun sendPosition(position: Position) {
        webSocket.send(
            """
            {"cmd":"sent", "position":"${position.value}"}
        """.trimIndent()
        )
    }

    private fun sendStatusUpdate(status: String) {
        webSocket.send(
            """
                {"cmd":"statusUpdate", "status":"$status"}
            """.trimIndent()
        )
    }
}