<img align="center" width="300" alt="image" src="https://user-images.githubusercontent.com/77398366/181906454-b46f6a14-7c2c-4b8f-8b0a-40432521bed8.png">

# Dynamic and observable thread pool framework

[![Gitee](https://gitee.com/magegoofy/hippo4j/badge/star.svg?theme=gvp)](https://gitee.com/magegoofy/hippo4j) [![GitHub](https://img.shields.io/github/stars/opengoofy/hippo4j?color=5470c6)](https://github.com/opengoofy/hippo4j) [![Contributors](https://img.shields.io/github/contributors/opengoofy/hippo4j?color=3ba272)](https://github.com/opengoofy/hippo4j/graphs/contributors) [![Docker Pulls](https://img.shields.io/docker/pulls/hippo4j/hippo4j-server.svg?label=docker%20pulls&color=fac858)](https://store.docker.com/community/images/hippo4j/hippo4j-server) [![codecov](https://codecov.io/gh/opengoofy/hippo4j/branch/develop/graph/badge.svg?token=WBUVJN107I)](https://codecov.io/gh/opengoofy/hippo4j)

-------

## Thread pool pain points

A thread pool is a tool for managing threads based on the idea of pooling.

Using a thread pool reduces the overhead of creating and destroying threads and avoids running out of system resources due to too many threads.

The use of thread pools is essential in highly concurrent and high-volume task processing scenarios.

If you have actually used thread pools in your projects, I believe you may have encountered the following pain points:

- Thread pools are defined randomly, with too many thread resources, causing high server load.

- The thread pool parameters are not easily evaluated and the business is at risk of failure.
- Thread pool task execution time exceeds the average execution cycle and developers are not informed.
- Thread pool tasks pile up and affect business operations.
- Wireless process pool monitoring when the service has timeouts, meltdowns, and other problems.
- Thread pools do not support the passing of runtime variables, such as MDC contexts.
- When a project is closed, a large number of running thread pool tasks are discarded.
- Thread pool running, task execution stopped, don't know the problem.

## What is Hippo-4J

Hippo-4J through the JDK thread pool enhancements, as well as extending the three-party framework underlying thread pools and other features for business systems to improve online operational security capabilities.

The following functional support is provided:

- Global Control - Managing Application Thread Pool Instances.

- Dynamic changes - dynamically changing thread pool parameters at application runtime.
- Notify alarms - Four built-in alarm notification policies.
- Run Monitoring - Real-time view of thread pool runtime data.
- Feature extensions - support for thread pooling task passing contexts, etc.
- Multiple Modes - Two built-in usage modes: Configuration Center Mode and No Middleware Mode.
- Container Management - Tomcat, Jetty, Undertow container thread pool runtime view and thread count changes.
- Framework adaptation - Dubbo, Hystrix, Polaris, RabbitMQ, RocketMQ and other consumer thread pool runtime data view and thread count changes.

## Quick Start

For local presentation purposes, see [Quick start](https://hippo4j.cn/docs/user_docs/user_guide/quick-start).

Demo Environment: [http://console.hippo4j.cn/index.html](http://console.hippo4j.cn/index.html).

## Who is using

More companies with access are welcome to register at [registration address](https://github.com/opengoofy/hippo4j/issues/13), registration is only for product promotion.

## Contributors
Thanks to all the developers who contributed to the project. If interested in contributing, refer to [good first issue](https://github.com/opengoofy/hippo4j/issues?q=is%3Aopen+is%3Aissue+label%3A%22good+first+issue%22).

<a href="https://github.com/opengoofy/hippo4j/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=opengoofy/hippo4j" />
</a>
