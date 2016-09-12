package com.wstrong.mygank.utils;

public class LogUtil {

    /**
     * you can config params in the application
     */
    protected static String TAG = "LogUtil";

    protected static final int DEBUG = 0;// 调试版本
    protected static final int BETA = 1;// 公开测试版
    protected static final int RELEASE = 2;// 发布版本

    private static int CURRENT_VERSION = DEBUG;// 当前版本

    private LogUtil() {

    }

    public static void setDebugMode(boolean flag){
        if (flag){
            CURRENT_VERSION = DEBUG;
        }else{
            CURRENT_VERSION = RELEASE;
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String tag, String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(tag, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void v(String msg, Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(msg, buildMessage(msg), thr);
                break;

            default:
                break;
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void d(String msg, Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(msg, buildMessage(msg), thr);
                break;

            default:
                break;
        }
    }

    public static void d(String tag, String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(tag, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void i(String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    public static void i(String tag, String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(tag, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void i(String msg, Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg), thr);
                break;

            default:
                break;
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void e(String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    public static void e(String tag, String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(tag, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void e(String msg, Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg), thr);
                break;

            default:
                break;
        }
    }

    /**
     * Send a WARN log message
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void w(String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    public static void w(String tag, String msg) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(tag, buildMessage(msg));
                break;

            default:
                break;
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void w(String msg, Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(msg), thr);
                break;

            default:
                break;
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr
     *            An exception to log
     */
    public static void w(Throwable thr) {
        switch (CURRENT_VERSION) {
            case DEBUG:
                android.util.Log.e(TAG, buildMessage(""), thr);
                break;

            default:
                break;
        }
    }

    /**
     * Building Message
     *
     * @param msg
     *            The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        // StackTraceElement caller = new
        // Throwable().fillInStackTrace().getStackTrace()[2];

        // return new
        // StringBuilder().append(caller.getClassName()).append(".").append(caller.getMethodName()).append("().").append(caller.getLineNumber()).append(": ").append(msg).toString();
        return msg;
    }

}
