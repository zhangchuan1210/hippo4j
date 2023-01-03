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

package cn.hippo4j.message.service;

import cn.hippo4j.common.toolkit.CollectionUtil;
import cn.hippo4j.message.api.NotifyConfigBuilder;
import cn.hippo4j.common.config.ApplicationContextHolder;
import cn.hippo4j.message.dto.AlarmControlDTO;
import cn.hippo4j.message.dto.NotifyConfigDTO;
import cn.hippo4j.message.enums.NotifyTypeEnum;
import cn.hippo4j.message.request.AlarmNotifyRequest;
import cn.hippo4j.message.request.ChangeParameterNotifyRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hippo-4j base send message service.
 */
@Slf4j
@RequiredArgsConstructor
public class Hippo4jBaseSendMessageService implements Hippo4jSendMessageService, CommandLineRunner {

    private final NotifyConfigBuilder notifyConfigBuilder;

    private final AlarmControlHandler alarmControlHandler;

    @Getter
    private final Map<String, List<NotifyConfigDTO>> notifyConfigs = new HashMap<>();

    private final Map<String, SendMessageHandler> sendMessageHandlers = new HashMap<>();

    @Override
    public void sendAlarmMessage(NotifyTypeEnum typeEnum, AlarmNotifyRequest alarmNotifyRequest) {
        String threadPoolId = alarmNotifyRequest.getThreadPoolId();
        String buildKey = new StringBuilder()
                .append(threadPoolId)
                .append("+")
                .append("ALARM")
                .toString();
        List<NotifyConfigDTO> notifyList = notifyConfigs.get(buildKey);
        if (CollectionUtil.isEmpty(notifyList)) {
            return;
        }
        notifyList.forEach(each -> {
            try {
                SendMessageHandler messageHandler = sendMessageHandlers.get(each.getPlatform());
                if (messageHandler == null) {
                    log.warn("[{}] Please configure alarm notification on the server.", threadPoolId);
                    return;
                }
                if (isSendAlarm(each.getTpId(), each.getPlatform(), typeEnum)) {
                    alarmNotifyRequest.setNotifyTypeEnum(typeEnum);
                    messageHandler.sendAlarmMessage(each, alarmNotifyRequest);
                }
            } catch (Exception ex) {
                log.warn("Failed to send thread pool alarm notification. key: [{}]", threadPoolId, ex);
            }
        });
    }

    @Override
    public void sendChangeMessage(ChangeParameterNotifyRequest changeParameterNotifyRequest) {
        String threadPoolId = changeParameterNotifyRequest.getThreadPoolId();
        String buildKey = new StringBuilder()
                .append(threadPoolId)
                .append("+")
                .append("CONFIG")
                .toString();
        List<NotifyConfigDTO> notifyList = notifyConfigs.get(buildKey);
        if (CollectionUtil.isEmpty(notifyList)) {
            log.warn("[{}] Please configure alarm notification on the server.", threadPoolId);
            return;
        }
        notifyList.forEach(each -> {
            try {
                SendMessageHandler messageHandler = sendMessageHandlers.get(each.getPlatform());
                if (messageHandler == null) {
                    log.warn("[{}] Please configure alarm notification on the server.", threadPoolId);
                    return;
                }
                messageHandler.sendChangeMessage(each, changeParameterNotifyRequest);
            } catch (Exception ex) {
                log.warn("Failed to send thread pool change notification. key: [{}]", threadPoolId, ex);
            }
        });
    }

    /**
     * Is send alarm.
     *
     * @param threadPoolId thread-pool id
     * @param platform     platform
     * @param typeEnum     type enum
     * @return
     */
    private boolean isSendAlarm(String threadPoolId, String platform, NotifyTypeEnum typeEnum) {
        AlarmControlDTO alarmControl = AlarmControlDTO.builder()
                .threadPool(threadPoolId)
                .platform(platform)
                .typeEnum(typeEnum)
                .build();
        return alarmControlHandler.isSendAlarm(alarmControl);
    }

    /**
     * Put platform.
     *
     * @param notifyConfigs notify configs
     */
    public synchronized void putPlatform(Map<String, List<NotifyConfigDTO>> notifyConfigs) {
        this.notifyConfigs.putAll(notifyConfigs);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, SendMessageHandler> sendMessageHandlerMap =
                ApplicationContextHolder.getBeansOfType(SendMessageHandler.class);
        sendMessageHandlerMap.values().forEach(each -> sendMessageHandlers.put(each.getType(), each));
        Map<String, List<NotifyConfigDTO>> buildNotify = notifyConfigBuilder.buildNotify();
        notifyConfigs.putAll(buildNotify);
    }
}
