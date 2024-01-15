### 极光推送
#### PushManager
- contextReference 持有 Context ，用于注册以及取消别名参数使用
- registerEmitter 注册别名生成的发射器，用于回调别名注册成功状态
- unregisterEmitter 删除别名生成的发射器，用于回调删除别名成功状态
- pushEmitter 推送发射器，当主程序订阅后，通过 Emitter 接受推送信息
 
- Init 初始化 Push 操作
- setAlias 设置别名
	- Observable<Boolean>
- deleteAlias 删除别名
	- Observable<Boolean> 
- updateAlisa 更新别名状态
	- 当 alisa 不为空则注册成功，否则 alisa 删除成功
- observePushEvent 订阅推送信息，主程序通过调用该方法获取推送消息
- receiverPush 接受推送，当`PushReceiver`接受到推送消息，则调用调用该方法传递推送信息
	- 当 pushEmitter 处于活跃状态，则通过 pushEmitter 传递推送消息
	- 当 pushEmitter 处于不活跃，则直接调用`Notifications.showNotification`显示推送信息

#### PushEntity
- 推送扩展信息实体类，自行与后台协商内容

#### Notifications
- showNotification 显示通知常规处理
- getNotifyIntent 生成一个 Intent，用于处于通知点击处理，指定主程序中 NotificationReceiver 为接收器，相关的逻辑自行定义
- getIcon 通知显示的 icon ，默认 ic_launcher，实际项目自行更换 
- clearAllNotification 清除所有通知

#### JPushReceiver
- onAliasOperatorResult 用于更新别名注册状态

#### PushReceiver  自定义推送接收
- onReceive 用于接受推送信息
