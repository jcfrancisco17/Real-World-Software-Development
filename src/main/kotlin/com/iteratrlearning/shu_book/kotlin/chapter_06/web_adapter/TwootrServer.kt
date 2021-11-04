package com.iteratrlearning.shu_book.kotlin.chapter_06.web_adapter

import com.iteratrlearning.shu_book.chapter_06.web_adapter.TwootrServer
import com.iteratrlearning.shu_book.kotlin.chapter_06.Twootr
import com.iteratrlearning.shu_book.kotlin.chapter_06.database.DatabaseTwootRepository
import com.iteratrlearning.shu_book.kotlin.chapter_06.database.DatabaseUserRepository
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.IOException
import java.net.InetSocketAddress

// {"cmd": "logon", "userName": "Joe", "password": "ahc5ez2aiV" }
// {"cmd": "logon", "userName": "John", "password": "ahc5ez2aiV" }
// {"cmd": "follow", "userName": "John" }
// {"cmd": "sendTwoot", "content": "Hello World" }

class TwootrServer(address: InetSocketAddress) : WebSocketServer(address, 1) {


    companion object {
        const val STATIC_PORT = 8000
        const val WEBSOCKET_PORT = 9000

        private const val USER_NAME = "Joe"
        private const val PASSWORD = "ahc5ez2aiV"
        private const val OTHER_USER_NAME = "John"

        @JvmStatic
        fun main(args: Array<String>) {
            val webSocketAddress = InetSocketAddress("localhost", WEBSOCKET_PORT)
            val twootrServer = TwootrServer(webSocketAddress)
            twootrServer.start()

            System.setProperty("org.eclipse.jetty.LEVEL", "INFO")

            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            context.resourceBase = "${System.getProperty("user.dir")}/src/main/webapp"
            context.contextPath = "/"

            val staticContentServlet = ServletHolder("staticContentServlet", DefaultServlet::class.java)
            staticContentServlet.setInitParameter("dirAllowed", "true")
            context.addServlet(staticContentServlet, "/")

            val jettyServer = Server(STATIC_PORT)
            jettyServer.handler = context
            jettyServer.start()
            jettyServer.dumpStdErr()
            jettyServer.join()
        }
    }

    private val twootRepository = DatabaseTwootRepository()
    private val twootr = Twootr(twootRepository, DatabaseUserRepository())
    private val socketToEndPoint = mutableMapOf<WebSocket, WebSockerEndPoint>()

    init {
        twootr.onRegisterUser(USER_NAME, PASSWORD)
        twootr.onRegisterUser(OTHER_USER_NAME, PASSWORD)
    }

    override fun onOpen(webSocket: WebSocket?, clientHandshake: ClientHandshake?) {
        socketToEndPoint[webSocket!!] = WebSockerEndPoint(twootr, webSocket)
    }

    override fun onClose(webSocket: WebSocket?, i: Int, s: String?, b: Boolean) {
        socketToEndPoint.remove(webSocket!!)
    }

    override fun onMessage(webSocket: WebSocket?, message: String?) {
        val endPoint = socketToEndPoint[webSocket]
        try {
            endPoint!!.onMessage(message!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onError(webSocket: WebSocket?, e: Exception?) {
        e?.printStackTrace()
    }
}