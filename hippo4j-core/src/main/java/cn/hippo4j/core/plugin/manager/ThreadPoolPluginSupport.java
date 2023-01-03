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

package cn.hippo4j.core.plugin.manager;

import cn.hippo4j.core.plugin.ExecuteAwarePlugin;
import cn.hippo4j.core.plugin.RejectedAwarePlugin;
import cn.hippo4j.core.plugin.ShutdownAwarePlugin;
import cn.hippo4j.core.plugin.TaskAwarePlugin;
import cn.hippo4j.core.plugin.ThreadPoolPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Used to support the binding of {@link ThreadPoolPluginManager} and {@link ThreadPoolExecutor}.
 */
public interface ThreadPoolPluginSupport extends ThreadPoolPluginManager {

    /**
     * Get thread pool action aware registry.
     *
     * @return {@link ThreadPoolPluginManager}
     */
    @NonNull
    ThreadPoolPluginManager getThreadPoolPluginManager();

    /**
     * Get thread-pool id
     *
     * @return thread-pool id
     */
    String getThreadPoolId();

    /**
     * Get thread-pool executor.
     *
     * @return thread-pool executor
     */
    ThreadPoolExecutor getThreadPoolExecutor();

    // ======================== delegate methods ========================

    /**
     * Clear all.
     */
    @Override
    default void clear() {
        getThreadPoolPluginManager().clear();
    }

    /**
     * Register a {@link ThreadPoolPlugin}
     *
     * @param plugin aware
     */
    @Override
    default void register(ThreadPoolPlugin plugin) {
        getThreadPoolPluginManager().register(plugin);
    }

    /**
     * Register plugin if it's not registered.
     *
     * @param plugin plugin
     * @return return true if successful register new plugin, false otherwise
     */
    @Override
    default boolean tryRegister(ThreadPoolPlugin plugin) {
        return getThreadPoolPluginManager().tryRegister(plugin);
    }

    /**
     * Whether the {@link ThreadPoolPlugin} has been registered.
     *
     * @param pluginId name
     * @return ture if target has been registered, false otherwise
     */
    @Override
    default boolean isRegistered(String pluginId) {
        return getThreadPoolPluginManager().isRegistered(pluginId);
    }

    /**
     * Unregister {@link ThreadPoolPlugin}.
     *
     * @param pluginId name
     */
    @Override
    default void unregister(String pluginId) {
        getThreadPoolPluginManager().unregister(pluginId);
    }

    /**
     * Get all registered plugins.
     *
     * @return plugins
     */
    @Override
    default Collection<ThreadPoolPlugin> getAllPlugins() {
        return getThreadPoolPluginManager().getAllPlugins();
    }

    /**
     * Get {@link ThreadPoolPlugin}.
     *
     * @param pluginId target name
     * @return {@link ThreadPoolPlugin}, null if unregister
     * @throws ClassCastException thrown when the object obtained by name cannot be converted to target type
     */
    @Override
    default <A extends ThreadPoolPlugin> Optional<A> getPlugin(String pluginId) {
        return getThreadPoolPluginManager().getPlugin(pluginId);
    }

    /**
     * Get id of disabled plugins.
     *
     * @return id of disabled plugins
     */
    @Override
    default Set<String> getAllDisabledPluginIds() {
        return getThreadPoolPluginManager().getAllDisabledPluginIds();
    }

    /**
     * Whether the plugin has been disabled.
     *
     * @param pluginId plugin id
     * @return true if plugin has been disabled, false otherwise
     */
    @Override
    default boolean isDisabled(String pluginId) {
        return getThreadPoolPluginManager().isDisabled(pluginId);
    }

    /**
     * Enable plugin, make plugins will allow access through quick indexes.
     *
     * @param pluginId plugin id
     * @return true if plugin already registered or enabled, false otherwise
     */
    @Override
    default boolean enable(String pluginId) {
        return getThreadPoolPluginManager().enable(pluginId);
    }

    /**
     * Disable plugin, make plugins will not allow access through quick indexes.
     *
     * @param pluginId plugin id
     * @return true if plugin already registered or disabled, false otherwise
     */
    @Override
    default boolean disable(String pluginId) {
        return getThreadPoolPluginManager().disable(pluginId);
    }

    /**
     * Get all enabled {@link ExecuteAwarePlugin}.
     *
     * @return {@link ExecuteAwarePlugin}
     * @see #enable
     * @see #disable
     */
    @Override
    default Collection<ExecuteAwarePlugin> getExecuteAwarePluginList() {
        return getThreadPoolPluginManager().getExecuteAwarePluginList();
    }

    /**
     * Get all enabled {@link RejectedAwarePlugin}.
     *
     * @return {@link RejectedAwarePlugin}
     * @see #enable
     * @see #disable
     */
    @Override
    default Collection<RejectedAwarePlugin> getRejectedAwarePluginList() {
        return getThreadPoolPluginManager().getRejectedAwarePluginList();
    }

    /**
     * Get all enabled {@link ShutdownAwarePlugin}.
     *
     * @return {@link ShutdownAwarePlugin}
     * @see #enable
     * @see #disable
     */
    @Override
    default Collection<ShutdownAwarePlugin> getShutdownAwarePluginList() {
        return getThreadPoolPluginManager().getShutdownAwarePluginList();
    }

    /**
     * Get all enabled {@link TaskAwarePlugin}.
     *
     * @return {@link TaskAwarePlugin}
     * @see #enable
     * @see #disable
     */
    @Override
    default Collection<TaskAwarePlugin> getTaskAwarePluginList() {
        return getThreadPoolPluginManager().getTaskAwarePluginList();
    }
}
