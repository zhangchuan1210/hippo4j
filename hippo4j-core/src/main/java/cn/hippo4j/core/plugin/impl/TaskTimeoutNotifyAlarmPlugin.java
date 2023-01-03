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

package cn.hippo4j.core.plugin.impl;

import cn.hippo4j.common.api.ThreadPoolCheckAlarm;
import cn.hippo4j.common.config.ApplicationContextHolder;
import cn.hippo4j.core.plugin.PluginRuntime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Send alarm notification when the execution time exceeds the threshold.
 */
@AllArgsConstructor
public class TaskTimeoutNotifyAlarmPlugin extends AbstractTaskTimerPlugin {

    public static final String PLUGIN_NAME = TaskTimeoutNotifyAlarmPlugin.class.getSimpleName();

    /**
     * Thread-pool id
     */
    private final String threadPoolId;

    /**
     * Execute time-out
     */
    @Getter
    @Setter
    private Long executeTimeOut;

    /**
     * Thread-pool executor
     */
    private final ThreadPoolExecutor threadPoolExecutor;

    /**
     * Thread pool check alarm
     * TODO Complete dependency injection through the thread plugin registrar
     */
    @NonNull
    private final ThreadPoolCheckAlarm threadPoolCheckAlarm;

    /**
     * Create a {@link TaskTimeoutNotifyAlarmPlugin}.
     *
     * @param threadPoolId thread pool id
     * @param executeTimeOut execute time out
     * @param threadPoolExecutor thread pool executor
     */
    public TaskTimeoutNotifyAlarmPlugin(String threadPoolId, Long executeTimeOut, ThreadPoolExecutor threadPoolExecutor) {
        this(
                threadPoolId, executeTimeOut, threadPoolExecutor,
                Optional.ofNullable(ApplicationContextHolder.getInstance())
                        .map(context -> context.getBean(ThreadPoolCheckAlarm.class))
                        .orElseGet(ThreadPoolCheckAlarm::none));
    }

    /**
     * Get plugin runtime info.
     *
     * @return plugin runtime info
     */
    @Override
    public PluginRuntime getPluginRuntime() {
        return new PluginRuntime(getId())
                .addInfo("executeTimeOut", executeTimeOut + "ms");
    }

    /**
     * Check whether the task execution time exceeds {@link #executeTimeOut},
     * if it exceeds this time, send an alarm notification.
     *
     * @param taskExecuteTime execute time of task
     */
    @Override
    protected void processTaskTime(long taskExecuteTime) {
        if (taskExecuteTime <= executeTimeOut) {
            return;
        }
        threadPoolCheckAlarm.asyncSendExecuteTimeOutAlarm(threadPoolId, taskExecuteTime, executeTimeOut, threadPoolExecutor);
    }
}
