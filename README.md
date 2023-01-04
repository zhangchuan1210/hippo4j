
# 动态可观测线程池

<img align="right" width="320" alt="image" src="https://user-images.githubusercontent.com/77398366/181906454-b46f6a14-7c2c-4b8f-8b0a-40432521bed8.png">

通过对 JDK 线程池的增强，以及扩展三方框架底层线程池等功能，为业务系统提高线上运行保障能力。

[![GitHub stars](https://img.shields.io/github/stars/opengoofy/hippo4j.svg?style=for-the-badge&label=Stars&logo=github)](https://github.com/opengoofy/hippo4j) [![Contributors](https://img.shields.io/github/contributors/opengoofy/hippo4j.svg?style=for-the-badge&label=Contributors&logo=appveyor)](https://github.com/opengoofy/hippo4j) 

[![Gitee](https://gitee.com/magestack/hippo4j/badge/star.svg?theme=gvp)](https://gitee.com/magestack/hippo4j) [![Docker Pulls](https://img.shields.io/docker/pulls/hippo4j/hippo4j-server.svg?label=docker%20pulls&color=fac858)](https://store.docker.com/community/images/hippo4j/hippo4j-server) [![codecov](https://codecov.io/gh/opengoofy/hippo4j/branch/develop/graph/badge.svg?token=WBUVJN107I)](https://codecov.io/gh/opengoofy/hippo4j) [![EN doc](https://img.shields.io/badge/readme-English-orange.svg)](https://github.com/opengoofy/hippo4j/blob/develop/README-EN.md)

-------

## 线程池痛点

线程池是一种基于池化思想管理线程的工具，使用线程池可以减少创建销毁线程的开销，避免线程过多导致系统资源耗尽。在高并发以及大批量的任务处理场景，线程池的使用是必不可少的。

如果有在项目中实际使用线程池，相信你可能会遇到以下痛点：

- 线程池随便定义，线程资源过多，造成服务器高负载。

- 线程池参数不易评估，随着业务的并发提升，业务面临出现故障的风险。
- 线程池任务执行时间超过平均执行周期，开发人员无法感知。
- 线程池任务堆积，触发拒绝策略，影响既有业务正常运行。
- 当业务出现超时、熔断等问题时，因为没有监控，无法确定是不是线程池引起。
- 原生线程池不支持运行时变量的传递，比如 MDC 上下文遇到线程池就 GG。
- 无法执行优雅关闭，当项目关闭时，大量正在运行的线程池任务被丢弃。
- 线程池运行中，任务执行停止，怀疑发生死锁或执行耗时操作，但是无从下手。

## 什么是 Hippo4j

提供以下功能支持：

- 全局管控 - 管理应用线程池实例。

- 动态变更 - 应用运行时动态变更线程池参数，包括但不限于：核心、最大线程数、阻塞队列容量、拒绝策略等。
- 通知报警 - 内置四种报警通知策略，线程池活跃度、容量水位、拒绝策略以及任务执行时间超长。
- 数据采集 - 支持多种方式采集线程池数据，包括但不限于：日志、内置采集、Prometheus、InfluxDB、ElasticSearch 等。
- 运行监控 - 实时查看线程池运行时数据，自定义时间内线程池运行数据图表展示。
- 功能扩展 - 支持线程池任务传递上下文；项目关闭时，支持等待线程池在指定时间内完成任务。
- 多种模式 - 内置两种使用模式：[依赖配置中心](https://hippo4j.cn/docs/user_docs/getting_started/config/hippo4j-config-start) 和 [无中间件依赖](https://hippo4j.cn/docs/user_docs/getting_started/server/hippo4j-server-start)。
- 容器管理 - Tomcat、Jetty、Undertow 容器线程池运行时查看和线程数变更。
- 框架适配 - Dubbo、Hystrix、RabbitMQ、RocketMQ 等消费线程池运行时数据查看和线程数变更。
- 变更审核 - 提供多种用户角色，普通用户变更线程池参数需要 Admin 用户审核方可生效。
- 动态化插件 - 内置多种线程池插件，支持用户自定义插件以及运行时扩展。
- 多版本适配 - 经过实际测试，已支持客户端 SpringBoot 1.5.x => 2.7.5 版本（更高版本未测试）。

## 快速开始

对于本地演示目的，请参阅 [Quick start](https://hippo4j.cn/docs/user_docs/user_guide/quick-start)

演示环境： [http://console.hippo4j.cn/index.html](http://console.hippo4j.cn/index.html)

## 接入登记

更多接入的公司，欢迎在 [登记地址](https://github.com/opengoofy/hippo4j/issues/13) 登记，登记仅仅为了产品推广。

## 联系我

![](https://user-images.githubusercontent.com/77398366/185774220-c11951f9-e130-4d60-8204-afb5c51d4401.png)

扫码添加微信，备注：hippo4j，邀您加入群聊。若图片加载不出来，访问 [官网站点](https://hippo4j.cn/docs/user_docs/other/group)。

## 深入原理

如果您公司没有使用 Hippo4j 场景的话，我也建议去阅读下项目的底层原理，主要有以下几个原因：

- 为了提高代码质量以及后续的扩展行为，运用多种设计模式实现高内聚、低耦合。

- 框架底层依赖 Spring 框架运行，并在源码中大量使用 Spring 相关功能。
- 运用 JUC 并发包下多种工具保障多线程运行安全，通过实际场景理解并发编程。
- 借鉴主流开源框架 Nacos、Eureka 实现轻量级配置中心和注册中心功能。
- 自定义 RPC 框架实现，封装 Netty 完成客户端/服务端网络通信优化。
- 通过 CheckStyle、Spotless 等插件规范代码编写，保障高质量代码行为和代码样式。

## 友情链接

- [[ Sa-Token ]](https://github.com/dromara/sa-token)：一个轻量级 java 权限认证框架，让鉴权变得简单、优雅！  

- [[ HertzBeat ]](https://github.com/dromara/hertzbeat)：易用友好的云监控系统, 无需 Agent, 强大自定义监控能力。   
- [[ JavaGuide ]](https://github.com/Snailclimb/JavaGuide)：一份涵盖大部分 Java 程序员所需要掌握的核心知识。
- [[ toBeBetterJavaer ]](https://github.com/itwanger/toBeBetterJavaer)：一份通俗易懂、风趣幽默的 Java 学习指南。
- [[ Jpom ]](https://gitee.com/dromara/Jpom)：简而轻的低侵入式在线构建、自动部署、日常运维、项目监控软件。

## 知识星球

👉 [工作经验不满五年程序员，如何通过项目提高个人技术能力？](https://www.yuque.com/magestack/public/knowledge-planet)

## 贡献者

感谢所有为项目作出贡献的开发者。如果有意贡献，参考 [good first issue](https://github.com/opengoofy/hippo4j/issues?q=is%3Aopen+is%3Aissue+label%3A%22good+first+issue%22)。

<!-- readme: contributors -start -->

<!-- readme: contributors -end -->

## 鸣谢

Hippo4j 社区收到 Jetbrains 多份 Licenses，并已分配项目 [活跃开发者](https://hippo4j.cn/team)，非常感谢 Jetbrains 对开源社区的支持。

![JetBrains Logo (Main) logo](https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.svg)
