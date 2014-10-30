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

package de.psdev.asyncotto;

public interface EventBus {

    /**
     * Register given object with the event bus.
     *
     * @param object instance of object to register
     */
    void register(Object object);

    /**
     * Unregister given object with the event bus.
     *
     * @param object instance of object to unregister
     */
    void unregister(Object object);

    /**
     * Post an event object to the bus.
     *
     * @param object event object to post
     */
    void post(Object object);

}
