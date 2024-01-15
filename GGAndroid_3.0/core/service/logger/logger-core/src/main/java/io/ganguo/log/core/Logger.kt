package io.ganguo.log.core

/**
 * 常规日志，输出到Console中
 *
 * Created by Tony on 4/4/15.
 */
object Logger : Printer by LoggerConfig.printer {

}