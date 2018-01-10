package android.wkp;

import java.io.Serializable;

/**
 * 消息类
 * <p>
 *     消息类用于数据携带，为其他线程与当前线程通信介质。
 *     消息需要处理时的时刻{@link #when}
 *     其他线程发送到当前线程的任务{@link #callBack}
 *     发送消息工具Handler{@link #target}
 *     校验标记{@link #what}
 *     整型数据{@link #arg1}
 *     整型数据{@link #arg2}
 *     任意数据{@link #obj}
 * </p>
 */
public final class Message implements Serializable {
    private static final int FLAG_IN_USE = 1;
    int flags;
    //消息需要处理时的时刻
    long when;
    //其他线程发送到当前线程的任务
    Runnable callBack;
    //发送消息工具Handler
    Handler target;
    //链表结构
    Message next;
    //校验标记
    public int what;
    //整型数据1
    public int arg1;
    //整型数据2
    public int arg2;
    //任意数据
    public Object obj;

    private Message() {
    }

    public static Message obtain(Handler handler) {
        Message msg = new Message();
        msg.target = handler;
        return msg;
    }

    static Message obtain(Handler handler, Runnable runnable) {
        Message msg = new Message();
        msg.target = handler;
        msg.callBack = runnable;
        return msg;
    }

    public static Message obtain(Handler handler, int what, int arg1, int arg2, Object obj) {
        Message msg = new Message();
        msg.target = handler;
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        return msg;
    }

    /**
     * 发送该消息
     */
    public void sendToTarget() {
        target.sendMessage(this);
    }

    /**
     * 回收该消息
     * @return
     */
    public boolean recycle() {
        if (isInUse()) {
            return false;
        }
        flags = 0;
        when = 0;
        callBack = null;
        target = null;
        next = null;
        what = 0;
        arg1 = 0;
        arg2 = 0;
        obj = null;
        return true;
    }

    /**
     * 标记该消息正在使用
     */
    void markInUse() {
        flags |= FLAG_IN_USE;
    }

    /**
     * 判断该消息是否正在使用
     * @return
     */
    boolean isInUse() {
        return (flags & FLAG_IN_USE) == FLAG_IN_USE;
    }
}
