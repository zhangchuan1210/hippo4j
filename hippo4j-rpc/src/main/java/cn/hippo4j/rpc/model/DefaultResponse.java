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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * default request<br>
 * Use the fully qualified name key of the interface and override equals and hashCode
 */
public class DefaultResponse implements Response {

    String key;
    Class<?> cls;
    transient Object obj;
    Throwable throwable;
    String errMsg;

    public DefaultResponse(String key, Class<?> cls, Object obj, Throwable throwable, String errMsg) {
        this.key = key;
        this.cls = cls;
        this.obj = obj;
        this.throwable = throwable;
        this.errMsg = errMsg;
    }

    public DefaultResponse(String key, Throwable throwable, String errMsg) {
        this(key, null, null, throwable, errMsg);
    }

    public DefaultResponse(String key, Class<?> cls, Object obj) {
        this(key, cls, obj, null, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class<?> getCls() {
        return cls;
    }

    @Override
    public Object getObj() {
        return obj;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public boolean isErr() {
        return throwable != null || errMsg != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DefaultResponse that = (DefaultResponse) o;
        return Objects.equals(key, that.key) && Objects.equals(cls, that.cls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, cls);
    }

    /**
     * Redefine the behavior of serialization, that is, re-acquire the initially serialized
     * data from the stream and re-serialize it. Simple serialization will result in the
     * loss of the field identified by transient.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (obj == null) {
            return;
        }
        // Serialization obj
        s.writeObject(this.obj);
    }

    /**
     * Redefine the deserialization behavior, and sequentially deserialize the data specified during
     * serialization, because there is data that is not deserialized during initial deserialization,
     * such as fields defined by transient
     */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        // Deserialization obj
        if (!isErr()) {
            this.obj = s.readObject();
        }
    }
}
