package io.ganguo.log.gg

import android.util.Log

/**
 * 日志配置文件
 *
 *
 * Created by Tony on 4/4/15.
 */
object GLogConfig {

    /**
     * 最低日记输出级别
     */
    var PRIORITY = Log.VERBOSE

    /**
     * 保存文件日志的级别
     */
    var FILE_PRIORITY = Log.ERROR

    /**
     * 打印栈信息的最低级别
     */
    var STACK_PRIORITY = Log.DEBUG

    /**
     * 单个日志文件大小 200K
     */
    var FILE_MAX_LENGTH = 204800

    /**
     * 日志长度
     */
    var MAX_LENGTH = -1

    /**
     * Json 打印时缩进
     */
    var JSON_INDENT = 4

    /**
     * TAG 前缀，方便区别系统日志
     */
    var TAG_PREFIX = "GLog"

    /**
     * 数据目录
     */
    var DATA_PATH = "GGDemo"

    /**
     * 日志文件目录名称
     */
    var APP_LOG_PATH = "logs"

    /**
     * 打印栈的个数
     */
    var STACK_OFFSET = 2

    /**
     * 是否打印栈信息
     */
    var PRINT_STACK_INFO = false
}