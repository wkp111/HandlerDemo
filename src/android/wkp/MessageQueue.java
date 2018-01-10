package android.wkp;

/**
 * 消息队列
 * <p>
 *     消息队列管理其他线程消息，主要有两个方法{@link #enqueueMessage(Message, long)}和{@link #next()}。
 * </p>
 */
public final class MessageQueue {
    //根消息
    private Message queue;

    /**
     * 消息队列添加消息
     * @param msg   消息
     * @param when  取出消息时刻
     */
    void enqueueMessage(Message msg, long when) {
        synchronized (this) {
            msg.markInUse();
            msg.when = when;
            Message temp = queue;
            if (temp == null || when == 0 || when < temp.when) {
                msg.next = temp;
                queue = msg;
            }else {
                Message pre;
                for (;;) {
                    pre = temp;
                    temp = temp.next;
                    if (temp == null || when < temp.when) {
                        break;
                    }
                }
                msg.next = temp;
                pre.next = msg;
            }
        }
    }

    /**
     * 消息队列取出消息，堵塞线程
     * @return
     */
    Message next() {
        for (;;) {
            synchronized (this) {
                long now = System.currentTimeMillis();
                Message msg = queue;
                if (msg != null) {
                    if (now >= msg.when) {
                        queue = msg.next;
                        msg.next = null;
                        msg.markInUse();
                        return msg;
                    }
                }
            }
        }
    }
}
