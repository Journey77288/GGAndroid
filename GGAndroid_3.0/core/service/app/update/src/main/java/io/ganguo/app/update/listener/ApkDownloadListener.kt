package io.ganguo.app.update.listener

import java.io.File

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : Download state Listener
 * </pre>
 */

interface ApkDownloadListener {
    fun downloadApkFileProcess(process: Int)
    fun downloadApkFileFail(error: Throwable)
    fun downloadApkFileSuccessful(file: File)
    fun downloadApkFileCancel() {}
}