package com.shail.sixtdemo.messages;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;

import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.utils.Print;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessagePumpEngine implements Callback {
    private static final String LOG = MessagePumpEngine.class.getSimpleName();
    private final List<Set<Handler>> mMessageHandlerArray;
    @Nullable
    private Handler mDelayedMessageHandler = null;

    /**
     * All the message id's supported for sending messages.
     */
    public enum MessageID {
        USER_LOCATION_CHANGED,
        USER_LOCATION_SETTINGS_CHANGED, NM_WIFI_DISABLED;

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
        if (Print.PRINT) {
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
     * Adding message handler in application context for the id
     *
     * @param handler - handler to get notification
     * @param id      - interested id to listen for
     */
    public static void addAppMessageHandler(final Handler handler, final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        if (pen != null) {
            pen.addMessageHandler(handler, id);
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
     * Remove message handler from application context for the specified id
     *
     * @param handler - handler registered to get notification
     * @param id      -id registered
     */
    public static void removeAppMessageHandler(final Handler handler, final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        if (pen != null) {
            pen.removeMessageHandler(handler, id);
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
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified
     *
     * @param id  - id of message
     * @param obj - object to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessage(final MessageID id, final Object obj) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessage(id.ordinal(), obj, 0, 0);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified
     *
     * @param id   - id of message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessage(final MessageID id, final int arg1, final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessage(id.ordinal(), null, arg1, arg2);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified
     *
     * @param id   - id of message
     * @param obj  - object to send in message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessage(final MessageID id, final Object obj, final int arg1, final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessage(id.ordinal(), obj, arg1, arg2);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id - id of message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtFrontOfQueue(final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtFrontOfQueue(id.ordinal(), null, 0, 0);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id  - id of message
     * @param obj - object to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtFrontOfQueue(final MessageID id, final Object obj) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtFrontOfQueue(id.ordinal(), obj, 0, 0);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id   - id of message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtFrontOfQueue(final MessageID id, final int arg1, final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtFrontOfQueue(id.ordinal(), null, arg1, arg2);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id   - id of message
     * @param obj  - object to send in message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtFrontOfQueue(final MessageID id, final Object obj, final int arg1,
                                                       final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtFrontOfQueue(id.ordinal(), obj, arg1, arg2);
    }

    /**
     * Sending the message to application context at the time specified. All the
     * handlers registered for the message 'id' will be notified Message will be
     * put in front of the queue when the time is reached
     *
     * @param uptimeMillis - time at which message has to be sent
     * @param id           - id of message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtTime(final long uptimeMillis, final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtTime(uptimeMillis, id, null, 0, 0);
    }

    /**
     * Sending the message to application context at the time specified. All the
     * handlers registered for the message 'id' will be notified Message will be
     * put in front of the queue when the time is reached
     *
     * @param uptimeMillis - time at which message has to be sent
     * @param id           - id of message
     * @param obj          - object to send in message
     * @param arg1         - arg1 to send in message
     * @param arg2         - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageAtTime(final long uptimeMillis, final MessageID id, final Object obj,
                                               final int arg1, final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageAtTime(uptimeMillis, id, obj, arg1, arg2);
    }

    /**
     * Sending the message to application context after the delay specified. All
     * the handlers registered for the message 'id' will be notified Message
     * will be put in front of the queue when the time is reached
     *
     * @param delayMillis - time at which message has to be sent
     * @param id          - id of message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageDelayed(final long delayMillis, final MessageID id) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageDelayed(delayMillis, id, null, 0, 0);
    }

    /**
     * Sending the message to application context after the delay specified. All
     * the handlers registered for the message 'id' will be notified Message
     * will be put in front of the queue when the time is reached
     *
     * @param delayMillis - time at which message has to be sent
     * @param id          - id of message
     * @param obj         - object to send in message
     * @param arg1        - arg1 to send in message
     * @param arg2        - arg2 to send in message
     * @return true on success, false otherwise
     */
    public static boolean sendAppMessageDelayed(final long delayMillis, final MessageID id, final Object obj,
                                                final int arg1, final int arg2) {
        final MessagePumpEngine pen = SixtApplication.getPumpEngine();
        return (pen != null) && pen.sendMessageDelayed(delayMillis, id, obj, arg1, arg2);
    }

    public void init() {
        mDelayedMessageHandler = new Handler(this);
    }

    public void deinit() {
        mDelayedMessageHandler = null;
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

    /**
     * Sending the message. All the handlers registered for the message 'id'
     * will be notified
     *
     * @param id - id of message
     * @return true on success, false otherwise
     */
    public boolean sendMessage(final MessageID id) {
        return sendMessage(id.ordinal(), null, 0, 0);
    }

    /**
     * Sending the message. All the handlers registered for the message 'id'
     * will be notified
     *
     * @param id  - id of message
     * @param obj - object to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessage(final MessageID id, final Object obj) {
        return sendMessage(id.ordinal(), obj, 0, 0);
    }

    /**
     * Sending the message. All the handlers registered for the message 'id'
     * will be notified
     *
     * @param id   - id of message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessage(final MessageID id, final int arg1, final int arg2) {
        return sendMessage(id.ordinal(), null, arg1, arg2);
    }

    /**
     * Sending the message. All the handlers registered for the message 'id'
     * will be notified
     *
     * @param id   - id of message
     * @param obj  - object to send in message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessage(final MessageID id, final Object obj, final int arg1, final int arg2) {
        return sendMessage(id.ordinal(), obj, arg1, arg2);
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

    /**
     * Sending the message. All the handlers registered for the message 'id'
     * will be notified Message will be put in front of the queue
     *
     * @param id - id of message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtFrontOfQueue(final MessageID id) {
        return sendMessageAtFrontOfQueue(id.ordinal(), null, 0, 0);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id  - id of message
     * @param obj - object to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtFrontOfQueue(final MessageID id, final Object obj) {
        return sendMessageAtFrontOfQueue(id.ordinal(), obj, 0, 0);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id   - id of message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtFrontOfQueue(final MessageID id, final int arg1, final int arg2) {
        return sendMessageAtFrontOfQueue(id.ordinal(), null, arg1, arg2);
    }

    /**
     * Sending the message to application context. All the handlers registered
     * for the message 'id' will be notified Message will be put in front of the
     * queue
     *
     * @param id   - id of message
     * @param obj  - object to send in message
     * @param arg1 - arg1 to send in message
     * @param arg2 - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtFrontOfQueue(final MessageID id, final Object obj, final int arg1, final int arg2) {
        return sendMessageAtFrontOfQueue(id.ordinal(), obj, arg1, arg2);
    }

    private boolean sendMessageAtFrontOfQueue(final int what, final Object obj, final int arg1, final int arg2) {
        boolean ret = false;
        synchronized (mMessageHandlerArray) {
            final Set<Handler> handlerSet = mMessageHandlerArray.get(what);
            if (handlerSet != null) {
                for (final Handler handler : handlerSet) {
                    final Message message = Message.obtain(handler, what, arg1, arg2, obj);
                    ret = handler.sendMessageAtFrontOfQueue(message);
                }
            } else {
                Print.e("AtFrontOfQueue:Could not notify " + MessageID.getID(what));
            }
        }
        return ret;
    }

    /**
     * Sending the message to application context at the time specified. All the
     * handlers registered for the message 'id' will be notified Message will be
     * put in front of the queue when the time is reached
     *
     * @param uptimeMillis - time at which message has to be sent
     * @param id           - id of message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtTime(final long uptimeMillis, final MessageID id) {
        return sendMessageAtTime(uptimeMillis, id, null, 0, 0);
    }

    /**
     * Sending the message to application context at the time specified. All the
     * handlers registered for the message 'id' will be notified Message will be
     * put in front of the queue when the time is reached
     *
     * @param uptimeMillis - time at which message has to be sent
     * @param id           - id of message
     * @param obj          - object to send in message
     * @param arg1         - arg1 to send in message
     * @param arg2         - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessageAtTime(final long uptimeMillis, final MessageID id, final Object obj, final int arg1,
                                     final int arg2) {
        if (mDelayedMessageHandler != null) {
            final Message message = Message.obtain(mDelayedMessageHandler, id.ordinal(), arg1, arg2, obj);
            return mDelayedMessageHandler.sendMessageAtTime(message, uptimeMillis);
        }
        return false;
    }

    /**
     * Sending the message to application context after the delay specified. All
     * the handlers registered for the message 'id' will be notified Message
     * will be put in front of the queue when the time is reached
     *
     * @param delayMillis - time at which message has to be sent
     * @param id          - id of message
     * @return true on success, false otherwise
     */
    public boolean sendMessageDelayed(final long delayMillis, final MessageID id) {
        return sendMessageDelayed(delayMillis, id, null, 0, 0);
    }

    /**
     * Sending the message to application context after the delay specified. All
     * the handlers registered for the message 'id' will be notified Message
     * will be put in front of the queue when the time is reached
     *
     * @param delayMillis - time at which message has to be sent
     * @param id          - id of message
     * @param obj         - object to send in message
     * @param arg1        - arg1 to send in message
     * @param arg2        - arg2 to send in message
     * @return true on success, false otherwise
     */
    public boolean sendMessageDelayed(final long delayMillis, final MessageID id, final Object obj, final int arg1,
                                      final int arg2) {
        if (mDelayedMessageHandler != null) {
            final Message message = Message.obtain(mDelayedMessageHandler, id.ordinal(), arg1, arg2, obj);
            return mDelayedMessageHandler.sendMessageDelayed(message, delayMillis);
        }
        return false;
    }

    @Override
    public boolean handleMessage(final Message arg0) {
        return sendMessageAtFrontOfQueue(arg0.what, arg0.obj, arg0.arg1, arg0.arg2);
    }
}
