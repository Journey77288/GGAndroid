package io.ganguo.app

import io.ganguo.app.update.ApkFiles
import io.ganguo.app.update.listener.ApkDownloadListener
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   :
 * </pre>
 */

class ApkDownloadTask(private val link: String,
                      filePath: String,
                      private val downloadCallback: ApkDownloadListener) {
    private val MAX_RETRY_COUNT = 3
    private var client = OkHttpClient.Builder().build()
    private var call: Call? = null
    private var retryCount = 0
    private var downloadFile = File(filePath)

    /**
     * 开始下载
     */
    fun startDownload() {
        var length = downloadFile.length().toInt()
        var request = Request.Builder()
                .addHeader("RANGE", "bytes=$length-")
                .url(link)
                .build()
        client.newCall(request).also { call = it }.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                downloadCallback.downloadApkFileFail(e)
            }

            override fun onResponse(call: Call, response: Response) {
                var append = response.code == 206
                if (!append) {
                    length = 0
                    ApkFiles.deleteFile(downloadFile)
                }
                var bytes = ByteArray(2048)
                var inputStream: InputStream? = null
                var outputStream: FileOutputStream? = null
                try {
                    inputStream = response.body!!.byteStream()
                    var total = response.body!!.contentLength() + length
                    outputStream = FileOutputStream(downloadFile, append)
                    var sum = length
                    var process = 0
                    while (inputStream.read(bytes).also { length = it } != -1) {
                        outputStream.write(bytes, 0, length)
                        sum += length
                        var newProcess = (sum.toFloat() / total * 100).toInt()
                        if (newProcess != process) {
                            process = newProcess
                            downloadCallback.downloadApkFileProcess(process)
                        }
                    }
                    outputStream.flush()
                    inputStream.close()
                    outputStream.close()
                    downloadCallback.downloadApkFileSuccessful(downloadFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                    when {
                        call.isCanceled() -> {
                            downloadCallback.downloadApkFileCancel()
                        }
                        retryCount < MAX_RETRY_COUNT -> {
                            retryCount++
                            startDownload()
                        }
                        else -> {
                            downloadCallback.downloadApkFileFail(e)
                        }
                    }
                    inputStream?.close()
                    outputStream?.close()
                }
            }
        })
    }

    /**
     * 取消下载
     */
    fun cancelDownload() {
        call?.cancel()
    }
}