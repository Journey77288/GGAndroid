package io.ganguo.pay.core;

/**
 * Created by Roger on 05/07/2017.
 */

public interface IProvider {
    IPayService newService();
}
