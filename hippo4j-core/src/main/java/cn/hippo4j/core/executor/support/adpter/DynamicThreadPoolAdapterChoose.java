/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hippo4j.core.executor.support.adpter;

import cn.hippo4j.core.executor.DynamicThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * Dynamic thread pool adapter choose.
 */
public class DynamicThreadPoolAdapterChoose {

    private static List<DynamicThreadPoolAdapter> DYNAMIC_THREAD_POOL_ADAPTERS = new ArrayList<>();

    static {
        DYNAMIC_THREAD_POOL_ADAPTERS.add(new TransmittableThreadLocalExecutorAdapter());
        DYNAMIC_THREAD_POOL_ADAPTERS.add(new TransmittableThreadLocalExecutorServiceAdapter());
        DYNAMIC_THREAD_POOL_ADAPTERS.add(new ThreadPoolTaskExecutorAdapter());
    }

    /**
     * Check if the object contains thread pool information.
     *
     * @param executor objects where there may be instances
     *                 of dynamic thread pools
     * @return matching results
     */
    public static boolean match(Object executor) {
        return DYNAMIC_THREAD_POOL_ADAPTERS.stream().anyMatch(each -> each.match(executor));
    }

    /**
     * Get the dynamic thread pool reference in the object.
     *
     * @param executor objects where there may be instances
     *                 of dynamic thread pools
     * @return get the real dynamic thread pool instance
     */
    public static DynamicThreadPoolExecutor unwrap(Object executor) {
        Optional<DynamicThreadPoolAdapter> dynamicThreadPoolAdapterOptional = DYNAMIC_THREAD_POOL_ADAPTERS.stream().filter(each -> each.match(executor)).findFirst();
        return dynamicThreadPoolAdapterOptional.map(each -> each.unwrap(executor)).orElse(null);
    }

    /**
     * If the {@link DynamicThreadPoolAdapter#match(Object)} conditions are met,
     * the thread pool is replaced with a dynamic thread pool.
     *
     * @param executor                  objects where there may be instances
     *                                  of dynamic thread pools
     * @param dynamicThreadPoolExecutor dynamic thread-pool executor
     */
    public static void replace(Object executor, Executor dynamicThreadPoolExecutor) {
        Optional<DynamicThreadPoolAdapter> dynamicThreadPoolAdapterOptional = DYNAMIC_THREAD_POOL_ADAPTERS.stream().filter(each -> each.match(executor)).findFirst();
        if (dynamicThreadPoolAdapterOptional.isPresent()) {
            dynamicThreadPoolAdapterOptional.get().replace(executor, dynamicThreadPoolExecutor);
        }
    }
}
