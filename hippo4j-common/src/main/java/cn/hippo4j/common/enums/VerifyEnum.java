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

package cn.hippo4j.common.enums;

/**
 * Enumeration of thread pool audit status.
 */
public enum VerifyEnum {

    /**
     * To verify
     */
    TO_VERIFY(0, "待审核"),

    /**
     * Verify accept
     */
    VERIFY_ACCEPT(1, "审核通过"),

    /**
     * Verify reject
     */
    VERIFY_REJECT(2, "审核拒绝"),

    /**
     * Verify invalid
     */
    VERIFY_INVALID(3, "失效");

    /**
     * Verify status
     */
    private final Integer verifyStatus;

    /**
     * Desc
     */
    private final String desc;

    VerifyEnum(Integer verifyStatus, String desc) {
        this.verifyStatus = verifyStatus;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public Integer getVerifyStatus() {
        return this.verifyStatus;
    }
}
