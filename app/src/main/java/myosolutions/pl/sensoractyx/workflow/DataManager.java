package myosolutions.pl.sensoractyx.workflow;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;

import myosolutions.pl.sensoractyx.websocket.WebSocketHelper;
import myosolutions.pl.sensoractyx.workflow.secondscreen.IWebSocketCallback;
import myosolutions.pl.sensoractyx.workflow.secondscreen.data.SensorMessage;


public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();

    private WebSocketHelper wsHelper;
    private Handler mHandler;
    private Runnable mRunnable;
    private IPrepareSensorMessageCallback sensorMessageCallback;

    public interface IPrepareSensorMessageCallback {
        SensorMessage prepareMessage();
    }

    public void createWebsocketConnection(IWebSocketCallback webSocketCallback, IPrepareSensorMessageCallback sensorMessageCallback){
        wsHelper = new WebSocketHelper(webSocketCallback);
        this.sensorMessageCallback = sensorMessageCallback;

    }


    public void openWebsocketConnection(){
        if(wsHelper!=null) {
            wsHelper.openWebsocketConnection();
        }else{
           Log.d(TAG, "Cannot open websocket connection");
        }
    }


    public void startSendingSensorValues(){
        mHandler = new Handler();
        final int delay = 1000; //milliseconds
        mRunnable = new Runnable() {
            @Override
            public void run() {
                sendSensorValues(sensorMessageCallback.prepareMessage());
                mHandler.postDelayed(this, delay);
            }
        };

        mHandler.postDelayed(mRunnable, delay);

    }

    public void stopSendingSensorValues(){
        mHandler.removeCallbacks(mRunnable);
    }

    public void closeWebsocketConnection(){
        if(wsHelper!=null){
            wsHelper.disconnectWebsocket();
        }
    }

    public void sendSensorValues(SensorMessage message){
        Gson gson = new Gson();

        WebSocketClient client = wsHelper.getWebSocketClient();

        if(client!=null && client.getConnection().isOpen()){
            client.send(gson.toJson(message));
        }
    }


}
