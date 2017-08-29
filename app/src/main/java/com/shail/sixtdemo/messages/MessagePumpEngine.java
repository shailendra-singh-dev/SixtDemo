package com.shail.sixtdemo.messages;

import android.os.Handler;
import android.os.Message;

import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.utils.Print;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class MessagePumpEngine {

    private static final String LOG = MessagePumpEngine.class.getSimpleName();
    private final List<Set<Handler>> mMessageHandlerArray;

    /**
     * All the message id's supported for sending messages.
     */
    public enum MessageID {
        INTERNET_NOT_AVAILABLE;

        public static final int CUSTOM_EVENT_START = 1000;

        public static MessageID getID(final int ordinal) {
            return (MessageID.values())[ordinal];
        }
    }

    /**
     * Constructor prepares the variables for message handling
     */
    public MessagePumpEngine() {
        mMessageHandlerArray = new ArrayList<Set<Handler>>(MessageID.values().length);
        for (final MessageID id : MessageID.values()) {
            mMessageHandlerArray.add(id.ordinal(), new HashSet<Handler>());
        }
    }

    /**
     * Getting the application class name for debugging purpose only
     */
    private static String getCallerString(final int level) {
        if (Print.IS_LOGGING_ENABLED) {
            return Thread.currentThread().getStackTrace()[level].getClassName();
        } else {
            return "";
        }
    }

    /**
     * Adding message handler in application context for the specified id's
     *
     * @param handler - handler to get notification
     * @param ids     - array of interested ids to listen for
     */
    public static void addAppMessageHandler(final Handler handler, final MessageID[] ids) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        if (pen != null) {
            pen.addMessageHandler(handler, ids);
        }
    }


    /**
     * Remove message handler from application context for the specified id's
     *
     * @param handler - handler registered to get notification
     * @param ids     - array of interested ids registered
     */
    public static void removeAppMessageHandler(final Handler handler, final MessageID[] ids) {
        Print.i("removeAppMessageHandler(),handler:" + handler + ",ids:" + ids);
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        if (pen != null) {
            pen.removeMessageHandler(handler, ids);
        }
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified
     *
     * @param id - id of message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessage(final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessage(id.ordinal(), null, 0, 0);
    }

    /**
     * Adding message handler for the specified id's
     *
     * @param handler - handler to get notification
     * @param ids     - array of interested ids to listen for
     */
    public void addMessageHandler(final Handler handler, final MessageID[] ids) {
        for (final MessageID id : ids) {
            addMessageHandler(handler, id);
        }
    }

    /**
     * Adding message handler for the id
     *
     * @param handler - handler to get notification
     * @param id      - interested id to listen for
     */
    public void addMessageHandler(final Handler handler, final MessageID id) {
        final String callerClassName = getCallerString(5);
        if (handler == null) {
            Print.e("Handler is null for pen :" + this);
        } else {
            synchronized (mMessageHandlerArray) {
                final Set<Handler> handlerSet = mMessageHandlerArray.get(id.ordinal());
                if (handlerSet != null) {
                    if (handlerSet.add(handler)) {
                        Print.v(callerClassName + " addMessageHandler[" + id + "] " + handler);
                    } else {
                        Print.w(callerClassName + " addMessageHandler - handle already present for id " + id);
                    }
                } else {
                    Print.e(callerClassName + " addMessageHandler FAILED for id " + id);
                }
            }
        }
    }

    /**
     * Remove message handler for the specified id's
     *
     * @param handler - handler registered to get notification
     * @param ids     - array of interested ids registered
     */
    public void removeMessageHandler(final Handler handler, final MessageID[] ids) {
        for (final MessageID id : ids) {
            removeMessageHandler(handler, id);
        }
    }

    /**
     * Remove message handler for the specified id
     *
     * @param handler - handler registered to get notification
     * @param id      -id registered
     */
    public void removeMessageHandler(final Handler handler, final MessageID id) {
        final String callerClassName = getCallerString(5);
        if (handler == null) {
            Print.e("Handler is null for pen:" + this);
        } else {
            synchronized (mMessageHandlerArray) {
                handler.removeMessages(id.ordinal());
                final Set<Handler> handlerSet = mMessageHandlerArray.get(id.ordinal());
                if (handlerSet != null) {
                    if (handlerSet.remove(handler)) {
                        Print.v(callerClassName + " removeMessageHandler[" + id + "] " + handler);
                    }
                } else {
                    Print.e(callerClassName + " removeMessageHandler FAILED for id " + id);
                }
            }
        }
    }

    private boolean sendMessage(final int what, final Object obj, final int arg1, final int arg2) {
        boolean ret = false;
        synchronized (mMessageHandlerArray) {
            final Set<Handler> handlerSet = mMessageHandlerArray.get(what);
            if (handlerSet != null) {
                for (final Handler handler : handlerSet) {
                    final Message message = Message.obtain(handler, what, arg1, arg2, obj);
                    ret = handler.sendMessage(message);
                }
            } else {
                Print.e("Could not notify " + MessageID.getID(what));
            }
        }
        return ret;
    }


}
