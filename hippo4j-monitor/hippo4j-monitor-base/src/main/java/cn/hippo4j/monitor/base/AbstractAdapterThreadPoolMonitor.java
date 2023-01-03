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

package cn.hippo4j.monitor.base;

import cn.hippo4j.adapter.base.ThreadPoolAdapter;
import cn.hippo4j.adapter.base.ThreadPoolAdapterState;
import cn.hippo4j.common.config.ApplicationContextHolder;

import java.util.Map;

/**
 * Abstract adapter thread-pool monitor.
 */
public abstract class AbstractAdapterThreadPoolMonitor implements AdapterThreadPoolMonitor {

    /**
     * Execute collection thread pool running data.
     *
     * @param threadPoolAdapterState thread-pool adapter state
     */
    protected abstract void execute(ThreadPoolAdapterState threadPoolAdapterState);

    @Override
    public void collect() {
        Map<String, ThreadPoolAdapter> threadPoolAdapterMap = ApplicationContextHolder.getBeansOfType(ThreadPoolAdapter.class);
        threadPoolAdapterMap.forEach((beanName, bean) -> bean.getThreadPoolStates().forEach(each -> execute(each)));
    }
}
