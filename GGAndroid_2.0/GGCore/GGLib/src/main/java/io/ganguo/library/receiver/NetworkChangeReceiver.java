package io.ganguo.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.ganguo.utils.util.Networks;
import io.ganguo.utils.util.log.Logger;

/**
 * 网络改变 BroadcastReceiver
 * <p/>
 * Created by Tony on 10/6/15.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // check status
            boolean isConnected = Networks.isConnected(context);
            Networks.Type type = Networks.getMobileType(context);

            // notify event
            //EventHub.post(new OnNetworkEvent(isConnected, type));

            Logger.i("isConnected: " + isConnected + " type:" + type);
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

}
