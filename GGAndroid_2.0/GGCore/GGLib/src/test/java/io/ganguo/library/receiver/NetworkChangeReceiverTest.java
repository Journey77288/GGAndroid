package io.ganguo.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.junit.Test;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

/**
 * 单元测试 NetworkChangeReceiver
 * Created by Lynn on 10/28/16.
 */

public class NetworkChangeReceiverTest extends GGLibraryTestBase {
    public int count = 0;

    @Test
    @TestDes("test NetworkChangeReceiver")
    public void testNetworkChangeReceiver() {
        NetworkChangeReceiver receiver = new TextReceiver();
        asNotNull(receiver);

        String filterString = "network change receiver";
        IntentFilter intentFilter = new IntentFilter(filterString);
        getApplicationContext().registerReceiver(receiver, intentFilter);
        //模拟发起广播
        getApplicationContext().sendBroadcast(new Intent(filterString));
        asEquals(count, 1);
        getApplicationContext().sendBroadcast(new Intent("wrong"));
        asEquals(count, 1);
        getApplicationContext().unregisterReceiver(receiver);
        getApplicationContext().sendBroadcast(new Intent(filterString));
        asEquals(count, 1);
    }

    class TextReceiver extends NetworkChangeReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            count++;
        }
    }
}
