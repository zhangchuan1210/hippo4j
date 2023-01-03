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

package cn.hippo4j.common.toolkit;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JSONUtilTest {

    private static final Foo EXPECTED_FOO = new Foo(1, "foo1", new Foo(2, "foo2", null));

    private static final List<Foo> EXPECTED_FOO_ARRAY = Arrays.asList(EXPECTED_FOO, EXPECTED_FOO);

    private static final String EXPECTED_FOO_JSON = "{\"id\":1,\"name\":\"foo1\",\"foo\":{\"id\":2,\"name\":\"foo2\"}}";

    private static final String EXPECTED_FOO_JSON_ARRAY = "[" + EXPECTED_FOO_JSON + "," + EXPECTED_FOO_JSON + "]";

    @Test
    public void assertToJSONString() {
        Assert.assertNull(JSONUtil.toJSONString(null));
        Assert.assertEquals(EXPECTED_FOO_JSON, JSONUtil.toJSONString(EXPECTED_FOO));
    }

    @Test
    public void assertParseObject() {
        Assert.assertNull(JSONUtil.parseObject(null, Foo.class));
        Assert.assertNull(JSONUtil.parseObject(" ", Foo.class));
        Assert.assertEquals(EXPECTED_FOO, JSONUtil.parseObject(EXPECTED_FOO_JSON, Foo.class));
    }

    @Test
    public void assertParseObjectTypeReference() {
        Assert.assertNull(JSONUtil.parseObject(null, new TypeReference<List<Foo>>() {
        }));
        Assert.assertNull(JSONUtil.parseObject(" ", new TypeReference<List<Foo>>() {
        }));
        Assert.assertEquals(
                EXPECTED_FOO_ARRAY,
                JSONUtil.parseObject(EXPECTED_FOO_JSON_ARRAY, new TypeReference<List<Foo>>() {
                }));
    }

    @Test
    public void assertParseArray() {
        Assert.assertEquals(Collections.emptyList(), JSONUtil.parseArray(null, Foo.class));
        Assert.assertEquals(Collections.emptyList(), JSONUtil.parseArray("  ", Foo.class));
        Assert.assertEquals(
                EXPECTED_FOO_ARRAY,
                JSONUtil.parseArray(EXPECTED_FOO_JSON_ARRAY, Foo.class));
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Foo {

        private Integer id;

        private String name;

        private Foo foo;
    }
}
