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

import java.util.Objects;

/**
 * Enable enum.
 */
public enum EnableEnum {

    /**
     * True.
     */
    YES("1"),

    /**
     * False.
     */
    NO("0");

    private final String statusCode;

    EnableEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return this.statusCode;
    }

    public Integer getIntCode() {
        return Integer.parseInt(this.statusCode);
    }

    @Override
    public String toString() {
        return statusCode;
    }

    public static boolean getBool(Integer intStatusCode) {
        return Objects.equals(intStatusCode, EnableEnum.YES.getIntCode()) ? true : false;
    }
}
