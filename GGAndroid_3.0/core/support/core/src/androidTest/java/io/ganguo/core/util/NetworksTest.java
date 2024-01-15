package io.ganguo.core.util;

import io.ganguo.core.ApplicationTest;
import io.ganguo.utils.util.Networks;
import io.ganguo.log.Logger;

/**
 * Created by Tony on 10/4/15.
 */
public class NetworksTest extends ApplicationTest {


    public void testNetworkInfo() {
        Logger.d("isConnected: " + Networks.isConnected(getContext()));
        Logger.d("type: " + Networks.getType(getContext()));
        Logger.d("isWifi: " + Networks.isWifi(getContext()));
        Logger.d("isMobile: " + Networks.isMobile(getContext()));
        Logger.d("mobileType: " + Networks.getMobileType(getContext()));
        Logger.d("ip: " + Networks.getWifiIp(getContext()));
        Logger.d("mac: " + Networks.getWifiMac(getContext()));
    }

}
