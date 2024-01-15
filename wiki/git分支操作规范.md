
#### 一、tag命名规范

- 1、阶段版本：stage_版本_日期(备注).例如：stage_v1.xx_0608(完成用户端全部UI)
- 2、测试版本：beta_版本_日期. 例如：beta_v1.xx_0608
- 3、上线版本：release_版本_日期. 例如：release_v1.xx_0608

#### 二、MR处理规范
##### a、MR说明:
+ 1、-> 代表分支合并方向。
+ 2、正常情况，一个项目，一天至少提交2~3次MR，提交功能跨度，最大不要超过一个页面。

##### b、MR提交时机:
- 1、完成单个页面/功能UI. 由feature_xxx->develop1.xx
- 2、完成单个Api/整个页面Api对接. 由feature_xxx_api->develop1.xx
- 3、修复完bug. 由bug分支->开出bug修复代码的分支
- 4、某一个阶段版本完成. 由develop1.xx->develop
- 5、版本提交应用市场审核通过. 由develop1.xx->develop，再由经理从develop->master

##### c、MR问题处理流程：
+ 1、看到修改意见后，回复表情👌

![image](uploads/2f486ca1dc26473246744a6c2c70cc01/image.png)

+ 2、代码修改完成，并提交代码后，请勾选下面按钮
  + 如出现需要讨论，回复多条时，则勾选对方回复中，除最后一条消息以外的按钮，且最后一条回复，需要按正常流程1、2处理。

![image](uploads/2d54c7e7273c72f2691fbb8f7b702a5e/image.png)


#### 三、git分支创建、使用规范

##### a、规范  
- 1、严禁在master、develop、develop_xx以外分支，直接发包
- 2、某个阶段版本，功能完成后，及时提交MR合并到develop主分支
- 3、分支用完即删，不要保留一堆用不到的分支
- 4、分支命名一定要清晰、且可读性高
- 5、分支目录结构，创建命名，请参考👇目录结构说明

```

├── master 交付给客户的代码.一般交付代码时，由部门经理合并develop到该分支.
├── develop 主开发分支，只能从version_xx目录下的develop分支，提交MR到该分支。提交MR的时机为，对应develop_xx分支开发完成，并上线.(不能直接push)
├── version1.0 应用v1.0版本的所有开发分支.
│   ├── develop1.0  V1.0版本，develop开发主分支，可以从对应版本的feature和bug目录，提交MR到该分支.(不能直接push)
├── ├── feature  V1.0版本，功能开发分支目录.
│   ├── ├── feature_login
│   ├── └── feature_register
├── ├── bug V1.0版本，bug修复分支.只能从对应版本的developxx分支，开出新分支，修复代码后，提交MR到开出代码的developxx分支.
│   ├── ├── fix_login_error（不需要通过issue流程来处理的bug）
│   ├── └── issue_xxx(对应的issue序号)
│   ├── └── feedback_register_error(客户直接反馈的问题)
├── version_xxx 后续迭代版本分支目录.

```


