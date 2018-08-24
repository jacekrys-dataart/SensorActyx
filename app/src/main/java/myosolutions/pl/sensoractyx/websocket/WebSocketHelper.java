package myosolutions.pl.sensoractyx.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import myosolutions.pl.sensoractyx.workflow.secondscreen.IWebSocketCallback;

/**
 * Created by Jacek on 2017-03-23.
 */

public class WebSocketHelper {

    private static final String TAG = WebSocketHelper.class.getSimpleName();

    private WebSocketClient client;
    private IWebSocketCallback webSocketCallback;

    public WebSocketHelper(IWebSocketCallback callback) {
        this.webSocketCallback = callback;

    }

    public void openWebsocketConnection(){
        connectWebSocket();
    }

    public void disconnectWebsocket(){
        webSocketCallback = null;
        client.getConnection().closeConnection(CloseFrame.GOING_AWAY, "Closing connection");
        client.close();
    }

    public WebSocketClient getWebSocketClient() {
        return client != null ? client.getConnection().isOpen() ? client : null : null;
    }

    private WebSocketClient connectWebSocket() {

        URI uri;
        try {
            uri = new URI(WebSocketConfig.WS_ADDRESS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        client = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i(TAG, "Opened");
            }

            @Override
            public void onMessage(String message) {
                if(webSocketCallback!=null){
                    webSocketCallback.onMessageReceived(message);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i(TAG, "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i(TAG, "Error " + e.getMessage());
            }
        };
        client.connect();

        return client;

    }

}
