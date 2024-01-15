package io.ganguo.pay.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 05/07/2017.
 */

public class GGPay {
    private static List<IPayService> services = null;

    /**
     * 方法总线
     */
    public static Map<Class, Object> sServiceClassMap = new HashMap<>();

    public static void registerPayMethod(Object o) {
        Class[] interfaces = o.getClass().getInterfaces();
        for (Class c : interfaces) {
            if (sServiceClassMap.containsKey(c)) {
                throw new IllegalStateException("duplicate register PayService");
            }
            sServiceClassMap.put(c, o);
        }
    }

    @SuppressWarnings("unchecked cast")
    public static <T> T getPayMethod(Class<?> c) {
        T payService = (T) sServiceClassMap.get(c);
        if (payService == null) {
            throw new IllegalArgumentException("PayMethod " + c.getName() + "not register yet");
        }
        return payService;
    }

    private GGPay() {

    }

    public static IPayService newService(IProvider p) {
        IPayService service = p.newService();
        if (services == null) {
            services = new ArrayList<>();
        }
        services.add(service);
        return service;
    }

    /**
     * Remove a service and release resources
     */
    public static boolean remove(IPayService IPayService) {
        if (services != null && services.size() > 0) {
            if (IPayService != null) {
                if (!IPayService.isRelease()) {
                    IPayService.release();
                }
                services.remove(IPayService);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void clear() {
        if (services != null && services.size() > 0) {
            for (IPayService service : services) {
                if (service != null && !service.isRelease()) {
                    service.release();
                }
            }
            services.clear();
        }
    }

}
