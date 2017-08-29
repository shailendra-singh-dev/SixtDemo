package com.shail.sixtdemo.utils;

import android.support.v4.BuildConfig;
import android.util.Log;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class Print {

    public static boolean PRINT = BuildConfig.DEBUG;

    private static String TAG = "DLOG";

    public static void e(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void e(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void exception(Throwable e) {
        if (PRINT && e != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            if (e != null) {
                Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Exception:" + e.getMessage());
                int lineNumber = e.getStackTrace()[0].getLineNumber();
                Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Exception:L:" + Integer.toString(lineNumber));
            } else {
                Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Exception: Unknown");
            }
        }
    }

    public static void exception(String e) {
        if (PRINT && e != null && !e.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            if (e != null) {
                Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Exception:" + e);
            } else {
                Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Exception: Unknown");
            }
        }
    }

    public static void w(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.w(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void w(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.w(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void i(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.i(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void i(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.i(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void d(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.d(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void d(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.d(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void request(String request) {
        if (PRINT) {
            if (request != null && !request.equals("null") && !request.equals("")) {
                Log.d(TAG + "Server Request", request);
            } else {
                Log.d(TAG + "Server Request", "Invalid");
            }
        }
    }

    public static void request(Object response) {
        if (PRINT) {
            if (response != null && !response.toString().equals("null") && !response.toString().equals("")) {
                Log.d(TAG + "Server Request", response.toString());
            } else {
                Log.d(TAG + "Server Request", "Invalid");
            }
        }
    }

    public static void response(String response) {
        if (PRINT) {
            if (response != null && !response.equals("null") && !response.equals("")) {
                boolean isFirst = true;
                int maxLogStringSize = 4024;
                for (int i = 0; i <= response.length() / maxLogStringSize; i++) {
                    int start = i * maxLogStringSize;
                    int end = (i + 1) * maxLogStringSize;
                    end = end > response.length() ? response.length() : end;
                    if (isFirst) {
                        Log.d(TAG + "Server Response", response.substring(start, end));
                        isFirst = false;
                    } else {
                        Log.d(TAG + "Server Response", response.substring(start, end));
                    }
                }
            } else {
                Log.d(TAG + "Server Response", "Invalid");
            }
        }
    }

    public static void response(Object response) {
        if (PRINT) {
            if (response != null && !response.toString().equals("null") && !response.toString().equals("")) {
                boolean isFirst = true;
                int maxLogStringSize = 4000;
                for (int i = 0; i <= response.toString().length() / maxLogStringSize; i++) {
                    int start = i * maxLogStringSize;
                    int end = (i + 1) * maxLogStringSize;
                    end = end > response.toString().length() ? response.toString().length() : end;
                    if (isFirst) {
                        Log.d(TAG + "Server Response", response.toString().substring(start, end));
                        isFirst = false;
                    } else {
                        Log.d(TAG + "Server Response", response.toString().substring(start, end));
                    }
                }
            } else {
                Log.d(TAG + "Server Response", "Invalid");
            }
        }
    }

    public static void v(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.v(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void v(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.v(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void wtf(String message) {
        if (PRINT && message != null && !message.equals("")) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.wtf(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message);
        }
    }

    public static void wtf(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.wtf(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString());
        }
    }

    public static void check() {
        if (PRINT) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", "Executed");
        }
    }

    public static void check(Object message) {
        if (PRINT && message != null) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.e(TAG + out.getFileName() + out.getMethodName() + out.getLineNumber() + " :", message.toString() + " executed");
        }
    }

    public static void page() {
        if (PRINT) {
            Out out = getInfo(Thread.currentThread().getStackTrace(), 3);
            Log.i("Page", out.getFileName());
        }
    }

    private static Out getInfo(final StackTraceElement e[], final int level) {

        if (e != null && e.length >= level) {
            final StackTraceElement s = e[level];
            if (s != null) {
                Out out = new Out();
                out.setClassName(s.getClassName());
                out.setFileName(s.getFileName());
                out.setMethodName(s.getMethodName());
                out.setLineNumber(Integer.toString(s.getLineNumber()));


                if (!out.getClassName().equals("")) {
                    out.setClassName(out.getClassName() + "");
                }
                if (!out.getFileName().equals("")) {
                    out.setFileName(out.getFileName() + "");
                }
                if (!out.getMethodName().equals("")) {
                    out.setMethodName(" => " + out.getMethodName() + "()");
                }
                if (!out.getLineNumber().equals("")) {
                    out.setLineNumber(" => L-" + out.getLineNumber());
                }
                return out;
            }
        }
        return null;
    }

    static class Out {
        private String className = "";
        private String fileName = "";
        private String methodName = "";
        private String lineNumber = "";

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }
    }
}
