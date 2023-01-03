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

package cn.hippo4j.rpc.model;

import java.io.Serializable;

/**
 * Response
 */
public interface Response extends Serializable {

    /**
     * The unique identity of the current Response
     */
    String getKey();

    /**
     * The class of the current Response, The target of deserialization
     */
    Class<?> getCls();

    /**
     * The results of this request can be obtained, The source of deserialization
     */
    Object getObj();

    /**
     * The Throwable of the current Response
     */
    Throwable getThrowable();

    /**
     * the error message
     */
    String getErrMsg();

    /**
     * Whether the current request has an error, <br>
     * If it is true then it cannot be retrieved from obj
     */
    boolean isErr();

}
