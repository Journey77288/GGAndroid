package io.ganguo.pay.core

import io.ganguo.factory.IProvider

/**
 * Created by Roger on 05/07/2017.
 */
interface IPayProvider<Service : PayService<*, *>> : IProvider<Service>
