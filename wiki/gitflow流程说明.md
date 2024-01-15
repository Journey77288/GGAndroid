# GitFlow 工作流程规范 `实验性流程`

## 前言

GitFlow可以很方便的进行并行开发、版本发布、紧急bug修复等流程，可以更高效的进行开发、测试，发布版本以及减少代码冲突。并且SourceTree可以直接进行GitFlow流程操作，学习使用成本极低。以下流程在GitFlow的流程基础上进行一些调整处理，以适用于我们现有开发流程。`目前为实验探索阶段，一般项目上暂时不使用，待确定最终流程后，再正式应用于开发`

### Tag

- A-branch：**长期分支，始终存在。**
- B-branch：**一般分支，一般情况始终保留该分支，在合适的时候，删除该分支。**
- C-branch：**功能性、Bug修复或者临时分支，一般完成开发，测试没有问题后，合并到其他相关分支后，可以立即删除。**


### 分支构成说明

- **master : 生产环境分支。** `A-branch`

```
分支说明：保持正式发布版本代码。
合并关系：允许release/hotfix分支的合并。

```


- **develop : 开发分支。** `A-branch`

```
分支说明：保持最新的开发代码，且一般情况只从其他分支合并，解决冲突等情况才会commit到该分支。
合并关系：允许feature/release/hotfix分支的合并。
```



- **release : 预发布版本分支。** `B-branch`

```
分支说明：从develop最新commit中创建，一般用于测试，测试完成，修复bug，确认没有问题后，合并到mastere分支进行版本发布。
合并关系：允许feature/release/hotfix分支的合并。
分支命名：根据版本命名，参考示例：release-v1.0
```


- **feature : 新功能分支。** `C-branch`

```
分支说明：独立新功能所在分支，一般处理一个新功能则建立一个feature分支，feature分支的粒度尽量小，不要一次性包含一整个大模块，完成该功能后，合并到develop分支，并删除对应的feature分支。
合并关系：一般情况下只允许hotfix合并到该分支。
分支命名：参考示例：login-ui，使用SourceTree创建时，会自动将该分支创建到feature目录下。

```


- **hotfix : 修复bug分支。** `C-branch`

```
分支说明：用于修复已发布、预发布版本BUG分支。
合并关系：不允许任何分支合并到该分支。
注意事项：修复bug，合并到master/release分支。
分支命名：参考示例：fix-login-error
```


### 开始GitFlow工作流

- 项目负责人构建项目，提交生成master分支。

- 项目建立develop分支，提交到git仓库。

- 各成员clone项目，在Terminal中执行git flow init 命令，执行该命令需要先配置安装git flow，[安装配置文档](https://github.com/petervanderdoes/gitflow-avh)，建议使用SourceTree执行flow init操作，git flow安装配置这一步都可以略过。

- 接下来就可以根据具体场景，使用以下操作了。

```
feature
release
hotfix

```

要求：`新手建议直接使用SourceTree，开始GitFlow流程，以减少初期出错几率。熟悉具体流程后，不做限制，可根据个人喜好，使用SoruceTree或者git命令操作。` **SourceTree实现GitFlow流程请阅读`相关资料`对应文档。**

### 相关资料

+ [GitFlow流程简介](https://danielkummer.github.io/git-flow-cheatsheet/index.zh_CN.html)

+ [SourceTree实现GitFlow流程参考文档](https://segmentfault.com/a/1190000013810338#articleHeader0)

+ [深入理解学习Git工作流](https://segmentfault.com/a/1190000006724816)



