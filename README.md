# HandlerDemo
对Android中Handler-Message机制总结，并用Java简单实现其原理（非严谨代码）。
### 程序代码示例
```java
package com.wkp.test;

import android.wkp.Handler;
import android.wkp.Looper;
import android.wkp.Message;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        //主线程Looper准备
        Looper.prepareMainLooper();
        //创建Handler
        final MainHandler handler = new MainHandler();
        //开启线程池
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int position = i + 1;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * position);
                        //子线程传任务到主线程
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName() + " : " + position);
                            }
                        },1000);

                        Thread thread = Thread.currentThread();
                        //子线程传消息到主线程
                        Message.obtain(handler,position,position,position,"主线程，你好！我是线程："+thread.getName()).sendToTarget();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //消息队列循环
        Looper.loop();
    }

    /**
     * 主线程处理子线程传回的消息
     */
    private static class MainHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WhatConstants.WHAT_ONE:
                    String obj1 = (String) msg.obj;
                    System.out.println(obj1);
                    break;
                case WhatConstants.WHAT_TWO:
                    String obj2 = (String) msg.obj;
                    System.out.println(obj2);
                    break;
                case WhatConstants.WHAT_THREE:
                    String obj3 = (String) msg.obj;
                    System.out.println(obj3);
                    break;
                case WhatConstants.WHAT_FOUR:
                    String obj4 = (String) msg.obj;
                    System.out.println(obj4);
                    break;
                case WhatConstants.WHAT_FIVE:
                    String obj5 = (String) msg.obj;
                    System.out.println(obj5);
                    break;
                case WhatConstants.WHAT_SIX:
                    String obj6 = (String) msg.obj;
                    System.out.println(obj6);
                    break;
                case WhatConstants.WHAT_SEVEN:
                    String obj7 = (String) msg.obj;
                    System.out.println(obj7);
                    break;
                case WhatConstants.WHAT_EIGHT:
                    String obj8 = (String) msg.obj;
                    System.out.println(obj8);
                    break;
                case WhatConstants.WHAT_NINE:
                    String obj9 = (String) msg.obj;
                    System.out.println(obj9);
                    break;
                case WhatConstants.WHAT_TEN:
                    String obj10 = (String) msg.obj;
                    System.out.println(obj10);
                    break;
            }
        }
    }
}

```
Note：该Handler-Message机制代码非Android中代码，请查看Demo中源码分析。
### 运行结果展示
```java
主线程，你好！我是线程：pool-1-thread-1
main : 1
主线程，你好！我是线程：pool-1-thread-2
main : 2
主线程，你好！我是线程：pool-1-thread-3
main : 3
主线程，你好！我是线程：pool-1-thread-4
主线程，你好！我是线程：pool-1-thread-5
main : 4
main : 5
主线程，你好！我是线程：pool-1-thread-6
主线程，你好！我是线程：pool-1-thread-7
main : 6
main : 7
主线程，你好！我是线程：pool-1-thread-8
main : 8
主线程，你好！我是线程：pool-1-thread-9
主线程，你好！我是线程：pool-1-thread-10
main : 9
main : 10
```
