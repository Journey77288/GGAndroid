package io.ganguo.utils.util.log;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Android日志扩展类
 * 1.控制输出级别
 * 2.控制日志输出长度
 * 3.打印JSON时漂亮输出
 * <p/>
 * Created by Tony on 4/4/15.
 */
public class GLog {
    private static final int MAX_LENGTH = 3500;

    public static int v(String tag, Object msg) {
        if (LogConfig.PRIORITY > Log.VERBOSE) return 0;

        String content = toString(msg);
        handleContentToLog(Log.VERBOSE, tag, content, null);
        return 0;
    }

    public static int v(String tag, Object msg, Throwable tr) {
        if (LogConfig.PRIORITY > Log.VERBOSE) return 0;
        if (tr == null) {
            return v(tag, msg);
        }

        String content = toString(msg);
        handleContentToLog(Log.VERBOSE, tag, content, tr);
        return 0;
    }

    public static int d(String tag, Object msg) {
        if (LogConfig.PRIORITY > Log.DEBUG) return 0;

        String content = toString(msg);
        handleContentToLog(Log.DEBUG, tag, content, null);
        return 0;
    }

    public static int d(String tag, Object msg, Throwable tr) {
        if (LogConfig.PRIORITY > Log.DEBUG) return 0;
        if (tr == null) {
            return d(tag, msg);
        }

        String content = toString(msg);
        handleContentToLog(Log.DEBUG, tag, content, tr);
        return 0;
    }

    public static int i(String tag, Object msg) {
        if (LogConfig.PRIORITY > Log.INFO) return 0;

        String content = toString(msg);
        handleContentToLog(Log.INFO, tag, content, null);
        return 0;
    }

    public static int i(String tag, Object msg, Throwable tr) {
        if (LogConfig.PRIORITY > Log.INFO) return 0;
        if (tr == null) {
            return i(tag, msg);
        }

        String content = toString(msg);
        handleContentToLog(Log.INFO, tag, content, tr);
        return 0;
    }

    public static int w(String tag, Object msg) {
        if (LogConfig.PRIORITY > Log.WARN) return 0;

        String content = toString(msg);
        handleContentToLog(Log.WARN, tag, content, null);
        return 0;
    }

    public static int w(String tag, Object msg, Throwable tr) {
        if (LogConfig.PRIORITY > Log.WARN) return 0;
        if (tr == null) {
            return w(tag, msg);
        }

        String content = toString(msg);
        handleContentToLog(Log.WARN, tag, content, tr);
        return 0;
    }

    public static int e(String tag, Object msg) {
        if (LogConfig.PRIORITY > Log.ERROR) return 0;

        String content = toString(msg);
        handleContentToLog(Log.ERROR, tag, content, null);

        return 0;
    }

    public static int e(String tag, Object msg, Throwable tr) {
        if (LogConfig.PRIORITY > Log.ERROR) return 0;
        if (tr == null) {
            return e(tag, msg);
        }

        String content = toString(msg);
        handleContentToLog(Log.ERROR, tag, content, tr);
        return 0;
    }

    private static String toString(Object msg) {
        if (msg == null || TextUtils.isEmpty(msg.toString())) {
            return "Empty/Null log content";
        }
        return toPrettyFormat(msg.toString());
    }

    private static String toPrettyFormat(String json) {
        String message;
        try {
            if (json.startsWith("{\"") && json.endsWith("}")) {
                message = "JSONObject\r\n" + new JSONObject(json).toString(LogConfig.JSON_INDENT);
            } else if (json.startsWith("[{\"") && json.endsWith("}]")) {
                message = "JSONArray\r\n" + new JSONArray(json).toString(LogConfig.JSON_INDENT);
            } else {
                message = json;
            }
        } catch (JSONException e) {
            message = json;
        }

        if (LogConfig.MAX_LENGTH > 0 && message.length() > LogConfig.MAX_LENGTH) {
            message = message.substring(0, LogConfig.MAX_LENGTH);
        }
        return message;
    }

    private static void handleContentToLog(int priority, String tag, String content, Throwable tr) {
        if (content.length() > MAX_LENGTH) {
            String[] contentSplit = handleStringLength(content);
            for (String split : contentSplit) {
                toLog(priority, tag, split, tr);
            }
        } else {
            toLog(priority, tag, content, tr);
        }
    }

    private static String[] handleStringLength(String content) {
        int offset = content.length() % MAX_LENGTH > 0 ? 1 : 0;
        int splitSize = content.length() / MAX_LENGTH + offset;
        String[] splits = new String[splitSize];
        for (int i = 0; i < splitSize; i++) {
            if (i == splitSize - 1) {
                splits[i] = content.substring(i * MAX_LENGTH, content.length());
            } else {
                splits[i] = content.substring(i * MAX_LENGTH, (i + 1) * MAX_LENGTH);
            }
        }
        return splits;
    }

    private static void toLog(int priority, String tag, String content, Throwable tr) {
        switch (priority) {
            case Log.VERBOSE:
                if (tr == null) {
                    Log.v(tag, content);
                } else {
                    Log.v(tag, content, tr);
                }
                break;
            case Log.DEBUG:
                if (tr == null) {
                    Log.d(tag, content);
                } else {
                    Log.d(tag, content, tr);
                }
                break;
            case Log.INFO:
                if (tr == null) {
                    Log.i(tag, content);
                } else {
                    Log.i(tag, content, tr);
                }
                break;
            case Log.ERROR:
                if (tr == null) {
                    Log.e(tag, content);
                } else {
                    Log.e(tag, content, tr);
                }
                break;
            case Log.WARN:
                if (tr == null) {
                    Log.w(tag, content);
                } else {
                    Log.w(tag, content, tr);
                }
                break;
        }
    }
}
