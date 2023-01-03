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

package cn.hippo4j.config.service.biz.impl;

import cn.hippo4j.common.constant.ConfigModifyTypeConstants;
import cn.hippo4j.common.toolkit.StringUtil;
import cn.hippo4j.common.toolkit.http.HttpUtil;
import cn.hippo4j.config.model.biz.threadpool.ConfigModifyVerifyReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Adapter thread pool config modification verify service impl.
 */
@Slf4j
@Service
public class AdapterThreadPoolConfigModificationVerifyServiceImpl extends AbstractConfigModificationVerifyService {

    @Override
    public Integer type() {
        return ConfigModifyTypeConstants.ADAPTER_THREAD_POOL;
    }

    @Override
    protected void updateThreadPoolParameter(ConfigModifyVerifyReqDTO reqDTO) {
        for (String each : getClientAddress(reqDTO)) {
            String urlString = StringUtil.newBuilder("http://", each, "/adapter/thread-pool/update");
            HttpUtil.post(urlString, reqDTO);
        }
    }
}
