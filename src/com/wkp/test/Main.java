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
