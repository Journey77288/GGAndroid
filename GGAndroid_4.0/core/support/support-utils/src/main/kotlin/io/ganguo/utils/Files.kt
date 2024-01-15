@file:JvmName("Files")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/21
 *     desc   : 文件操作工具
 * </pre>
 */

package io.ganguo.utils

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import android.util.Log
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.UriUtils
import io.ganguo.utils.FileSizeType.*
import java.io.*
import java.nio.charset.Charset
import java.text.DecimalFormat
import kotlin.jvm.Throws


private const val ANDROID_ASSET = "file:///android_asset/"
private const val ANDROID_RES = "file:///android_res/"


/**
 * 文件单位大小
 * @property TYPE_B 获取文件大小单位为B的double值
 * @property TYPE_KB 获取文件大小单位为KB的double值
 * @property TYPE_MB 获取文件大小单位为MB的double值
 * @property TYPE_GB 获取文件大小单位为GB的double值
 * @constructor
 */
enum class FileSizeType(var value: Int) {
    TYPE_B(1),
    TYPE_KB(2),
    TYPE_MB(3),
    TYPE_GB(4);
}

/**
 * 获取asset文件名，应用于WebView/Glide
 *
 * @param name
 * @return
 */
fun getAssetFile(name: String): File {
    return File(ANDROID_ASSET + name)
}

/**
 * 获取res文件名，应用于WebView/Glide
 *
 * @param name
 * @return
 */
fun getResFile(name: String): File {
    return File(ANDROID_RES + name)
}

/**
 * 判断文件是否不存在
 * @return Boolean
 */
fun File?.isNotExists(): Boolean {
    return when {
        this == null || this.path.isNullOrEmpty() -> {
            true
        }
        else -> {
            !exists()
        }
    }
}

/**
 *  File conversion ByteArray
 * @receiver File?
 * @return ByteArray?
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.readToByteArray(): ByteArray? {
    // 判断文件是否存在
    if (isNotExists()) {
        throw FileNotFoundException()
    }
    val bytes = ByteArray(this!!.length().toInt())
    val ins = FileInputStream(this)
    ins.use { it ->
        it.read(bytes)
    }
    return bytes
}


/**
 * 读取文件内容
 * @receiver File?
 * @param charset Charset?
 * @return String?
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.readToString(charset: Charset = Charset.defaultCharset()): String {
    return readToByteArray()?.let {
        String(it, charset)
    }.orEmpty()
}

/**
 * 写入文件数据到ByteArray
 * @receiver File?
 * @param toBytes ByteArray?
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.writeToByteArray(toBytes: ByteArray): Boolean {
    return writeToByteArray(toBytes, false)
}


/**
 * 写入文件数据到ByteArray
 * @receiver File?
 * @param bytes ByteArray
 * @param append Boolean
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.writeToByteArray(bytes: ByteArray, append: Boolean): Boolean {
    if (isNotExists()) {
        return false
    }
    val out = FileOutputStream(this, append)
    return out.use {
        it.write(bytes)
        it.flush()
        bytes.isNotEmpty()
    }

}


/**
 * 移动文件
 * @receiver File?
 * @param toDirectory File
 * @return Boolean
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.move(toDirectory: File): Boolean {
    return when {
        isNotExists() || this!!.renameTo(toDirectory) -> {
            return false
        }
        else -> {
            copy(toDirectory)
            delete()
        }
    }
}

/**
 * 复制文件
 * @receiver File?
 * @param toDirectory File
 * @throws IOException
 */
@Throws(IOException::class)
fun File?.copy(toDirectory: File): Boolean {
    val ins: InputStream = FileInputStream(this)
    return ins.use {
        it.writeToFile(toDirectory, false)
    }
}


/**
 * 将输入流写入到文件
 * @receiver InputStream?
 * @param to File
 * @return Boolean
 */
fun InputStream?.writeToFile(to: File, append: Boolean): Boolean {
    if (this == null) {
        return false
    }
    val out: OutputStream = FileOutputStream(to, append)
    return out.use { it ->
        val data = ByteArray(1024)
        var length: Int
        while (read(data).also { length = it } != -1) {
            it.write(data, 0, length)
        }
        it.flush()
        true
    }
}


/**
 * 检查文件是否存在
 * @receiver String?
 * @return Boolean
 */
fun String?.checkFilePathExists(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    return File(this.orEmpty()).checkFileExists()
}

/**
 * 检查文件是否存在
 * @receiver File?
 * @return Boolean
 */
fun File?.checkFileExists(): Boolean {
    if (isNotExists()) {
        return false
    }
    return this!!.exists() && isFile
}

/**
 * 检查文件是否存在
 * @receiver String?
 * @return Boolean
 */
fun String?.checkFileExists(): Boolean {
    return File(this.orEmpty()).exists()
}


/**
 * 检查目录是否存在
 * @receiver String?
 * @return Boolean
 */
fun String?.checkFolderExists(): Boolean {
    return File(this.orEmpty()).checkFolderExists()
}

/**
 * 检查目录是否存在
 * @receiver File?
 * @return Boolean
 */
fun File?.checkFolderExists(): Boolean {
    if (isNotExists()) {
        return false
    }
    return this!!.exists() && isDirectory
}

/**
 * 创建目录
 * @receiver String?
 * @return Boolean
 */
fun String?.makeDirs(): Boolean {
    return if (checkFilePathExists()) {
        true
    } else {
        File(this.orEmpty()).mkdirs()
    }
}


/**
 * 对文件进行删除操作
 * @receiver File?
 * @return Boolean
 */
fun File?.deleteFile(): Boolean {
    if (isNotExists()) {
        return true
    }
    if (this!!.isDirectory) {
        deleteFiles(listFiles())
    }
    return delete()
}

/**
 * 删除目录中的所有文件
 *
 * @param files
 */
fun deleteFiles(files: Array<File>?) {
    if (files == null) {
        return
    }
    for (child in files) {
        if (child.isDirectory) {
            child.deleteFile()
        } else {
            child.delete()
        }
    }
}


/**
 * 获取指定文件或文件夹指定单位的大小
 * @receiver String?
 * @param sizeType FileSizeType
 * @return Double
 */
fun String?.folderOrFileSize(sizeType: FileSizeType): Double {
    return File(this.orEmpty()).folderOrFileSize(sizeType)
}


/**
 * 获取文件/文件夹大小
 * @receiver File?
 * @param sizeType FileSizeType
 * @return Double
 */
fun File?.folderOrFileSize(sizeType: FileSizeType): Double {
    if (isNotExists()) {
        return 0.0
    }
    var blockSize: Long = 0
    try {
        blockSize = if (this!!.isDirectory) {
            this.folderSize()
        } else {
            this.size()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return blockSize.formatFileSize(sizeType)
}

/**
 * 调用此方法自动计算指定文件或指定文件夹的大小
 * @param filePath 文件路径
 * @return 计算好的带B、KB、MB、GB的字符串
 */
fun String?.autoFolderOrFileSize(filePath: String?): String? {
    return File(filePath.orEmpty()).autoFolderOrFileSize()
}

/**
 * 调用此方法自动计算指定文件或指定文件夹的大小
 * @return 计算好的带B、KB、MB、GB的字符串
 */
fun File?.autoFolderOrFileSize(): String? {
    val file = this
    return try {
        when {
            isNotExists() -> {
                0
            }
            file!!.isDirectory -> {
                file.folderSize()
            }
            else -> {
                file.size()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        0L
    }.formatFileSize()
}

/**
 * 获取指定文件的大小
 * @param file
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun File?.size(): Long {
    if (isNotExists()) {
        return 0L
    }
    var size: Long = 0
    if (this!!.exists()) {
        var fis: FileInputStream?
        fis = FileInputStream(this)
        size = fis.available().toLong()
        fis.close()
    } else {
        createNewFile()
    }
    return size
}

/**
 * 获取文件夹大小
 * @receiver File?
 * @return Long
 * @throws Exception
 */
@Throws(Exception::class)
fun File?.folderSize(): Long {
    var size: Long = 0
    val fist = this?.listFiles().orEmpty()
    for (i in fist.indices) {
        size = if (fist[i].isDirectory) {
            size + fist[i].folderSize()
        } else {
            size + fist[i].size()
        }
    }
    return size
}

/**
 * 将[Long]数值转换为文件单位大小
 * @return
 */
fun Long?.formatFileSize(): String? {
    val df = DecimalFormat("#.00")
    val wrongSize = "0B"
    val fileSize = this
    if (fileSize == null || fileSize == 0L) {
        return wrongSize
    }
    return when {
        fileSize < 1024 -> {
            df.format(fileSize.toDouble()) + "B"
        }
        fileSize < 1048576 -> {
            df.format(fileSize.toDouble() / 1024) + "KB"
        }
        fileSize < 1073741824 -> {
            df.format(fileSize.toDouble() / 1048576) + "MB"
        }
        else -> {
            df.format(fileSize.toDouble() / 1073741824) + "GB"
        }
    }
}

/**
 *将[Long]数值转换为文件单位大小,指定转换的类型
 * @param sizeType
 * @return
 */
fun Long.formatFileSize(sizeType: FileSizeType): Double {
    val df = DecimalFormat("#.00")
    val fileSize = this
    return when (sizeType) {
        TYPE_B -> {
            df.format(fileSize.toDouble()).toDouble()
        }
        TYPE_KB -> {
            df.format(fileSize.toDouble() / 1024).toDouble()
        }
        TYPE_MB -> {
            df.format(fileSize.toDouble() / 1048576).toDouble()
        }
        TYPE_GB -> {
            df.format(fileSize.toDouble() / 1073741824).toDouble()
        }
    }
}

/**
 * Gets the extension of a file.
 * @receiver File?
 * @return String?
 */
fun File?.extension(): String? {
    if (isNotExists()) {
        return null
    }
    var ext: String? = null
    val s = this!!.name
    val i = s.lastIndexOf('.')
    if (i > 0 && i < s.length - 1) {
        ext = s.substring(i + 1).toLowerCase()
    }
    return ext
}

/**
 * 检查是否挂载SDCard
 * @return Boolean
 */
fun isSdCardMounted(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

/**
 * File to Bitmap 转换
 * PS：最好cache一下oom问题，图片太大的时候有机会出现oom
 * @receiver File?
 * @return Bitmap?
 */
fun File?.toBitmap(): Bitmap? {
    if (this == null) return null
    return BitmapFactory.decodeFile(absolutePath)
}

/**
 * Bitmap to File 转换
 * @receiver Bitmap?
 * @return File?
 */
fun Bitmap?.toFile(path: String): File? {
    if (this == null) return null
    val imageFile = File(path)
    return try {
        val os = FileOutputStream(imageFile)
        this.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()
        imageFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


/**
 * 取得空闲SD卡空间大小
 *
 * @return bit
 */
fun getSDCardAvailableSize(): Long {
    if (isSdCardMounted()) {
        return 0L
    }
    // 取得sdcard文件路径
    val path = Environment.getExternalStorageDirectory()
    val stat = StatFs(path.path)
    // 获取block的size
    val blockSize: Long
    blockSize = stat.blockSizeLong
    // 空闲的Block的数量
    val availableBlocks: Long
    availableBlocks = stat.availableBlocksLong
    // 返回bit大小值
    return availableBlocks * blockSize
}


/**
 * 获取存储目录
 * @receiver Context?
 * @param dirName String
 * @return File?
 */
fun Context?.getStorageDirectory(dirName: String): File? {
    if (this == null) {
        return null
    }
    val file: File = if (isSdCardMounted()) {
        File(
                Environment.getExternalStorageDirectory()
                        .toString() + File.separator + dirName
        )
    } else {
        File(cacheDir.toString() + File.separator + dirName)
    }
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}


/**
 * 从手机数据库中查询图片文件
 * @receiver Context?
 * @param context Context
 * @return File?
 */
fun Uri?.cursorImageFile(context: Context): File? {
    if (this == null) {
        return null
    }
    val filePathColumn =
            arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(
            this,
            filePathColumn, null, null, null
    )
    cursor!!.moveToFirst()
    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
    val picturePath = cursor.getString(columnIndex)
    cursor.close()
    return if (picturePath.isNullOrEmpty()) {
        return null
    } else {
        File(picturePath)
    }
}


/**
 * 根据传入的时目录，文件格式后缀，以及当前时间戳，创建文件
 * @param filePath String?
 * @param extension String
 * @return File?
 */
fun createNewFile(filePath: String, extension: String): File? {
    val date: String = System.currentTimeMillis().toString()
    val fileName = date + "-" + randomUUID()
    return createNewFile(filePath, fileName, extension)
}


/**
 * 根据传入的时目录，文件格式后缀，以及当前时间戳，创建文件
 * @param filePath String?
 * @param extension String
 * @return File?
 */
fun createNewFile(filePath: String, fileName: String, extension: String): File? {
    return File(filePath, "$fileName.$extension")
}


/**
 * 根据图片类型创建文件
 * @receiver ImageType
 * @param filePath String?
 * @return File?
 */
fun ImageType.createImageFile(filePath: String): File? = createNewFile(filePath, this.value)

/**
 * 根据图片类型创建文件
 * @receiver ImageType
 * @param filePath String?
 * @return File?
 */
fun ImageType.createImageFile(filePath: String, fileName: String): File? = createNewFile(filePath, fileName, this.value)

/**
 * 移动文件到公共Downloads文件夹路径下
 *
 * @param context Context
 * @param callback ((File) -> Unit) 移动成功回调
 */
fun File.moveToExternalDownloads(context: Context, callback: ((File) -> Unit)) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentResolver = context.contentResolver
        try {
            val originUri = UriUtils.file2Uri(this)
            val inputStream = contentResolver.openInputStream(originUri)
            val bis = BufferedInputStream(inputStream)
            val lastUri = queryFileFromExternalDownloadDir(context, name)
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val uri = lastUri ?: contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                val outputStream = contentResolver.openOutputStream(uri)
                outputStream?.let {
                    val bos = BufferedOutputStream(outputStream)
                    val buffer = ByteArray(4096)
                    var bytes = bis.read(buffer)
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes)
                        bos.flush()
                        bytes = bis.read(buffer)
                    }
                    callback.invoke(UriUtils.uri2File(uri))
                    bos.close()
                }
            }
            bis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        val downloadPath = PathUtils.getExternalDownloadsPath()
        val file = File("$downloadPath/$name")
        FileUtils.moveFile(this, file) { srcFile, destFIle ->
            callback.invoke(file)
            true
        }
    }
}

/**
 * 从外部存储Downloads目录中查询文件
 *
 * @param context
 * @param fileName
 * @return Boolean
 */
@TargetApi(Build.VERSION_CODES.Q)
fun queryFileFromExternalDownloadDir(context: Context, fileName: String): Uri? {
    val contentResolver = context.contentResolver
    val projection = arrayOf(MediaStore.MediaColumns._ID)
    val selection = "${MediaStore.MediaColumns.DISPLAY_NAME}=?"
    val selectionArgs = arrayOf(fileName)
    contentResolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null).use { c ->
        if (c != null && c.count > 0) {
            c.moveToFirst().let {
                val id = c.getLong(c.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                return ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id)
            }
        }
    }
    return null
}

