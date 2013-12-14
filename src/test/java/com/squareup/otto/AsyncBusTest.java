package com.squareup.otto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AsyncBusTest {

    private AsyncBus mAsyncBus;

    @Before
    public void setUp() throws Exception {
        mAsyncBus = new AsyncBus();
    }

    @Test
    public void testPostFromMainThread() throws Exception {
        final Object event = new Object();
        final Object subscriber = new Object() {
            @Subscribe
            public void onEventPosted(final Object receivedEvent) {
                Assert.assertSame(event, receivedEvent);
            }
        };
        mAsyncBus.register(subscriber);
        mAsyncBus.post(event);
    }
}
