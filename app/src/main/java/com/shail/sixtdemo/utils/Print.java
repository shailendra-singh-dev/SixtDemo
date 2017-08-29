package com.shail.sixtdemo.utils;

import android.util.Log;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class Print {

    private static final String MATHOD_NAME_PREFIX = " => ";
    private static final String LINE_NUMBER_PREFIX = " => L-";
    private static final String CURLY_BRACES = "()";
    public static boolean IS_LOGGING_ENABLED = true;
    private static String TAG = "DLOG";

    public static void e(String message) {
        if (IS_LOGGING_ENABLED && message != null && !message.isEmpty()) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void e(Object message) {
        if (IS_LOGGING_ENABLED && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void w(String message) {
        if (IS_LOGGING_ENABLED && message != null && !message.isEmpty()) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.w(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void w(Object message) {
        if (IS_LOGGING_ENABLED && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.w(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void i(String message) {
        if (IS_LOGGING_ENABLED && message != null && !message.isEmpty()) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.i(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void i(Object message) {
        if (IS_LOGGING_ENABLED && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.i(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void d(String message) {
        if (IS_LOGGING_ENABLED && message != null && !message.isEmpty()) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.d(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void d(Object message) {
        if (IS_LOGGING_ENABLED && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.d(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void v(String message) {
        if (IS_LOGGING_ENABLED && message != null && !message.isEmpty()) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.v(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void v(Object message) {
        if (IS_LOGGING_ENABLED && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace());
            Log.v(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    private static Out getInfo(final StackTraceElement e[]) {
        if (e != null && e.length >= 0) {
            for (final StackTraceElement s : e) {
                if (s != null) {
                    Out out = new Out();
                    out.setClassName(s.getClassName());
                    out.setFileName(s.getFileName());
                    out.setMethodName(s.getMethodName());
                    out.setLineNumber(AppUtils.getStringFromNumber(s.getLineNumber()));

                    if (null != out.getClassName() && !out.getClassName().isEmpty()) {
                        out.setClassName(out.getClassName() + "");
                    }
                    if (null != out.getFileName() && !out.getFileName().isEmpty()) {
                        out.setFileName(out.getFileName() + "");
                    }
                    if (null != out.getMethodName() && !out.getMethodName().isEmpty()) {
                        out.setMethodName(MATHOD_NAME_PREFIX + out.getMethodName() + CURLY_BRACES);
                    }
                    if (null != out.getLineNumber() && !out.getLineNumber().isEmpty()) {
                        out.setLineNumber(LINE_NUMBER_PREFIX + out.getLineNumber());
                    }
                    return out;
                }
            }
        }
        return null;
    }

    private static final class Out {
        private String mClassName;
        private String mFileName;
        private String mMethodName;
        private String mLineNumber;

        public String getClassName() {
            return mClassName;
        }

        public void setClassName(String mClassName) {
            this.mClassName = mClassName;
        }

        public String getFileName() {
            return mFileName;
        }

        public void setFileName(String mFileName) {
            this.mFileName = mFileName;
        }

        public String getMethodName() {
            return mMethodName;
        }

        public void setMethodName(String mMethodName) {
            this.mMethodName = mMethodName;
        }

        public String getLineNumber() {
            return mLineNumber;
        }

        public void setLineNumber(String mLineNumber) {
            this.mLineNumber = mLineNumber;
        }
    }
}
