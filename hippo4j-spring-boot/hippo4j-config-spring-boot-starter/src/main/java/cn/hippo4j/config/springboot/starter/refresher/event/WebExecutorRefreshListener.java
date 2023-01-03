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

package cn.hippo4j.config.springboot.starter.refresher.event;

import cn.hippo4j.adapter.web.WebThreadPoolHandlerChoose;
import cn.hippo4j.adapter.web.WebThreadPoolService;
import cn.hippo4j.common.config.ApplicationContextHolder;
import cn.hippo4j.common.model.ThreadPoolParameter;
import cn.hippo4j.common.model.ThreadPoolParameterInfo;
import cn.hippo4j.config.springboot.starter.config.BootstrapConfigProperties;
import cn.hippo4j.config.springboot.starter.config.WebThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.util.Objects;

import static cn.hippo4j.config.springboot.starter.refresher.event.Hippo4jConfigDynamicRefreshEventOrder.WEB_EXECUTOR_LISTENER;

/**
 * Web executor refresh listener.
 */
@Slf4j
@Order(WEB_EXECUTOR_LISTENER)
public class WebExecutorRefreshListener extends AbstractRefreshListener<WebThreadPoolProperties> {

    @Override
    public String getNodes(WebThreadPoolProperties properties) {
        return properties.getNodes();
    }

    @Override
    public void onApplicationEvent(Hippo4jConfigDynamicRefreshEvent threadPoolDynamicRefreshEvent) {
        BootstrapConfigProperties bindableCoreProperties = threadPoolDynamicRefreshEvent.getBootstrapConfigProperties();
        boolean isNullFlag = bindableCoreProperties.getJetty() == null
                && bindableCoreProperties.getUndertow() == null
                && bindableCoreProperties.getTomcat() == null;
        if (isNullFlag) {
            return;
        }
        try {
            ThreadPoolParameterInfo nowParameter = buildWebPoolParameter(bindableCoreProperties);
            if (nowParameter != null) {
                WebThreadPoolHandlerChoose webThreadPoolHandlerChoose = ApplicationContextHolder.getBean(WebThreadPoolHandlerChoose.class);
                WebThreadPoolService webThreadPoolService = webThreadPoolHandlerChoose.choose();
                ThreadPoolParameter beforeParameter = webThreadPoolService.getWebThreadPoolParameter();
                if (!Objects.equals(beforeParameter.getCoreSize(), nowParameter.getCoreSize())
                        || !Objects.equals(beforeParameter.getMaxSize(), nowParameter.getMaxSize())
                        || !Objects.equals(beforeParameter.getKeepAliveTime(), nowParameter.getKeepAliveTime())) {
                    webThreadPoolService.updateWebThreadPool(nowParameter);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to modify web thread pool.", ex);
        }
    }

    private ThreadPoolParameterInfo buildWebPoolParameter(BootstrapConfigProperties bindableCoreProperties) {
        ThreadPoolParameterInfo threadPoolParameterInfo = null;
        WebThreadPoolProperties webThreadPoolProperties = null;
        if (bindableCoreProperties.getTomcat() != null) {
            webThreadPoolProperties = bindableCoreProperties.getTomcat();
        } else if (bindableCoreProperties.getUndertow() != null) {
            webThreadPoolProperties = bindableCoreProperties.getUndertow();
        } else if (bindableCoreProperties.getJetty() != null) {
            webThreadPoolProperties = bindableCoreProperties.getJetty();
        }
        if (webThreadPoolProperties != null && webThreadPoolProperties.getEnable() && match(webThreadPoolProperties)) {
            threadPoolParameterInfo = ThreadPoolParameterInfo.builder()
                    .coreSize(webThreadPoolProperties.getCorePoolSize())
                    .maximumPoolSize(webThreadPoolProperties.getMaximumPoolSize())
                    .keepAliveTime(webThreadPoolProperties.getKeepAliveTime())
                    .build();
        }
        return threadPoolParameterInfo;
    }
}
