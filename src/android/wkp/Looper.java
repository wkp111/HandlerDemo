package android.wkp;

/**
 * 消息队列循环管理类
 * <p>
 *      消息队列循环管理类，每个线程最多只能有一个{@link Looper}，Looper的创建方法{@link #prepare()}或{@link #prepareMainLooper()}，该方法需在
 *      {@link Handler}创建之前调用；Looper循环处理消息方法{@link #loop()}，该方法会堵塞线程，需在最后调用。
 * </p>
 */
public final class Looper {
    //当前线程Looper存储
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    //主线程Looper
    private static Looper sMainLooper;
    //当前线程Looper管理的消息队列
    final MessageQueue mQueue;
    //当前线程
    final Thread mThread;

    private Looper() {
        mQueue = new MessageQueue();
        mThread = Thread.currentThread();
    }

    /**
     * 循环准备，需在handler创建之前调用
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    /**
     * 若当前线程为主线程，则采用该循环准备
     */
    public static void prepareMainLooper() {
        prepare();
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    /**
     * 获取主线程Looper
     * @return
     */
    public static Looper getMainLooper() {
        synchronized (Looper.class) {
            return sMainLooper;
        }
    }

    /**
     * 获取当前线程Looper
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 循环处理消息队列，堵塞线程
     */
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        for (;;) {
            Message msg = me.mQueue.next();
            if (msg == null) {
                //表示消息队列已退出（不具备退出标记）
                return;
            }
            msg.target.dispatchMessage(msg);
        }
    }
}
