package io.ganguo.utils.util.log;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.date.DateTime;

import static io.ganguo.utils.util.log.LogConfig.APP_LOG_PATH;
import static io.ganguo.utils.util.log.LogConfig.DATA_PATH;

/**
 * 文件日志，按级别保存到文件中
 * <p>
 * <p/>
 * Created by Tony on 4/4/15.
 */
public class FileLoggerPrinter extends LoggerPrinter {
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public FileLoggerPrinter(Context context) {
        super(context);
    }

    /**
     * 保存到文件中，可以按日志级别（priority）进行保存
     * 一般只保存ERROR级别，放到SD卡中
     * 每个文件保存200K log1.txt、log2.txt
     *
     * @param priority
     * @param msg
     * @param tr
     * @return
     */
    @Override
    protected void println(int priority, Object msg, Throwable tr) {
        super.println(priority, msg, tr);

        // 保存日志
        if (priority >= LogConfig.FILE_PRIORITY) {
            writeLog(toLogMessage(priority, msg, tr));
        }
    }

    /**
     * 追加日志
     *
     * @param msg
     */
    private void writeLog(String msg) {
        File logFile = getLogFile();
        // 获取日志文件失败
        if (logFile == null) return;

        BufferedWriter writer = null;
        try {
            // 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String toLogMessage(int priority, Object msg, Throwable tr) {
        StringBuffer sb = new StringBuffer();
        sb.append(new DateTime(System.currentTimeMillis()).toDateTime());
        sb.append(" ");
        switch (priority) {
            case Log.VERBOSE:
                sb.append("V/");
                break;
            case Log.DEBUG:
                sb.append("D/");
                break;
            case Log.INFO:
                sb.append("I/");
                break;
            case Log.WARN:
                sb.append("W/");
                break;
            case Log.ERROR:
                sb.append("E/");
                break;
            default:
                sb.append(priority);
                break;
        }
        // TODO: 27/12/2016 需要补充
//        sb.append();
        sb.append(": ");
        sb.append(msg);
        if (tr != null) {
            sb.append("\r\n");
            sb.append(Log.getStackTraceString(tr));
        }
        return sb.toString();
    }

    private File getLogFile() {
        File logPath = getLogPath();
        for (int i = 1; i < 100; i++) {
            File logFile = new File(logPath, "log" + i + ".txt");
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return logFile;
            } else if (logFile.length() < LogConfig.FILE_MAX_LENGTH) {
                return logFile;
            }
        }
        // delete all
        Files.deleteFiles(logPath);
        return new File(logPath + "log1.txt");
    }

    /**
     * 获取程序图片目录
     *
     * @return
     */
    private File getLogPath() {
        return Files.getStorageDirectory(getContext(), DATA_PATH + File.separator + APP_LOG_PATH);
    }
}
