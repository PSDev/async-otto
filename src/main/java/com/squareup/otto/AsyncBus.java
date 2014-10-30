/*
 * Copyright 2013-2014 Philip Schiffer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.squareup.otto;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import de.psdev.asyncotto.EventBus;

/**
 * Special Bus which handles being called from another thread
 */
public class AsyncBus extends Bus implements EventBus {
    private static final int MESSAGE_POST_EVENT = 1;

    private final Handler mMainThreadHandler;

    public AsyncBus() {
        this(DEFAULT_IDENTIFIER);
    }

    public AsyncBus(final String identifier) {
        this(ThreadEnforcer.MAIN, identifier);
    }

    public AsyncBus(final ThreadEnforcer enforcer) {
        this(enforcer, DEFAULT_IDENTIFIER);
    }

    public AsyncBus(final ThreadEnforcer enforcer, final String identifier) {
        this(enforcer, identifier, HandlerFinder.ANNOTATED);
    }

    public AsyncBus(final ThreadEnforcer enforcer, final String identifier, final HandlerFinder handlerFinder) {
        super(enforcer, identifier, handlerFinder);
        mMainThreadHandler = new Handler(Looper.getMainLooper(), mMainThreadHandlerCallback);
    }

    @Override
    public void post(final Object event) {
        if (Thread.currentThread().equals(mMainThreadHandler.getLooper().getThread())) {
            super.post(event);
        } else {
            mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage(MESSAGE_POST_EVENT, event));
        }
    }

    private final Handler.Callback mMainThreadHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message message) {
            boolean messageHandled = false;
            if (message.what == MESSAGE_POST_EVENT) {
                AsyncBus.super.post(message.obj);
                messageHandled = true;
            }
            return messageHandled;
        }
    };

}
