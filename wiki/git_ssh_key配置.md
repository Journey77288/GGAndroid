# git ssh Key配置文档

## 一、说明 
> 进行git配置前，请确保已安装git环境，如没有安装请点击[link](https://git-scm.com/downloads)进行下载安装。

- a、必配平台
  - [甘果gitlab](https://gitlab.cngump.com)

  - [github](https://github.com/)

- b、选配平台
  - [gitlab](https://github.com/)
  - [gitee](https://gitee.com/)

#### 二、配置步骤
#### 1、先检查电脑是否生成过ssh key

- a、打开iTerm，电脑如果没有安装iTerm，请先安装。在iTerm窗口输入cd ~/.ssh命令，键盘回车确认，
如果提示目录不存在，请直接执行第2步

- b、如果直接进入了目录，说明之前已经配置过SSH key，直接输入open ./命令，键盘回车打开Finder，
删除.ssh文件夹下，无用文件及目录。`删除操作仅适用于入职环境配置阶段，后期如需增删SSH key文件，
需要确认其中的的公钥和私钥，是否还在使用`

#### 2、生成SSH key`update`

- a、手动创建.ssh目录并进入该目录下
- b、在iTerm窗口输入ssh-keygen -t rsa -C "title"(title为对应平台、账号
标识，可以根据实际情况取名，建议用平台名称+账号，例如：github_8888@gmail.com)命令后，回车。
- c、在窗口输入文件名（文件名建议取“id_平台名称+账号”，例如
github平台则是github_8888@gmail.com），连按两次回车，直到提示SSH key生成完毕。

                                                                                                                                                                                                                                                                                               
#### 3、将公钥配置到对应平台
- 在iTerm窗口输入cd ~/.ssh命令，键盘回车打开.ssh目录，找到之前生成的
SSH key公钥文件（.pub结尾的），拖到sublime text 中打开，复制粘贴到相应的平
台的SSH key配置下即可。

**提示：不同平台可以共用一个ssh key，但同一台电脑，在同一平台，不同账号，不能共用相同的ssh key，需要使用不同的ssh key，且需要额外配置。如遇此种情况，请参考第6点配置**`update`

#### 4、多ssh key配置文件

- a、iTerm窗口输入cd ~/.ssh命令进入该目录下，然后执行open ./命令，打开finder，
在finder中新建config文件

- b、将以下内容粘贴到config文件，并保存(注意将fileName和title，替换成你生成
ssh key时，配置的信息)

```
    # gitlab.com
    Host gitlab.com
        HostName gitlab.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/fileName
        User title

    # gitlab.ganguomob.com
    Host gitlab.ganguomob.com
        HostName gitlab.ganguomob.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/fileName
        User title

    # github.com
    Host github.com
        HostName github.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/fileName
        User title

```


#### 5、测试SSH key是否配置成功,以下分别为不同平台测试命令

- ssh -T git@gitlab.ganguomob.com

- ssh -T git@github.com

- ssh -T git@gitlab.com


如果配置成功，则会提示以下内容，三句分别对应不同平台


- Welcome to GitLab, username!

- Hi username! You've successfully authenticated, but GitHub does not provide shell access.

- Welcome to GitLab, username!


#### 6、同一台设备，针对同一个平台，多账号ssh key配置`new`
> 适用场景：同一平台下，部分项目需要用私人账号提交代码，但部分项目又需要用团队/其他账号提交代码的情况

- a、针对平台调整对应配置，使用不用的Host和ssk key进行区分

```
    # gitee.com 团队账号配置
    Host giteeTeam
        HostName gitee.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/fileName_team
        User title

    # gitee.com 私人账号配置
    Host giteeUser
        HostName gitee.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/fileName_user
        User title

```
- b、测试
  - 由原来的**ssh -T git@gitee.com**改为**ssh -T git@giteeTeam**和**ssh -T git@giteeUser**，确保都返回第5点中的提示，则配置成功


- c、项目git配置更新
  - 需要修改本地项目，git配置中的remote url，才能进行代码提交、拉取等操作
  - 配置参数文件所在路径：项目根目录 -> .git -> config文件中。使用soureTree则可以直接在:仓库 -> 远程仓库，菜单中修改

```
修改前：git@gitee.com:yxb900211/xxdt_android.git
修改后：git@giteeTeam:yxb900211/xxdt_android.git
```


