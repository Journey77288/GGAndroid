# Commit Message规范

##### a、注意事项
- 1、每次发包测试以及应用发布上架后，都必须打tag后提交
- 2、Commit代码时，一定要附上message，且需要有关键说明

##### b、提交规范  
- 1、提交信息第一行摘要部分末尾不要有标点符号
- 2、如果是修复issue一定要带issue的关联引用。issue关联放提交信息最后一行，并且与上一行要有一个空行。示例中的`More commit info here.More commit info here.`为修复细节信息（细节信息为可选部分）
- 3、issue的关联引用可以是issue页面的地址，也可以是issue页面右下角的Reference
- 4、多个issue，需要分开修复提交，不要一次同时Commit好几个fix
- 5、git commit粒度尽量小，意义鲜明，一般少于50个字符，不能包含多余的文件，可以结合source tree提交

##### c、提交格式

- 一般提交 :
```
feat：新功能（feature）
fix：修补bug
adjust : 对已有功能进行调整
docs：文档（documentation）
style： 格式（不影响代码运行的变动）
refactor：重构（即不是新增功能，也不是修改bug的代码变动）
test：增加测试
chore：构建过程或辅助工具的变动
delete : 删除xx

```

- bug修复:
```
fix: one line commit message

More commit info here.More commit info here.
More commit info here.More commit info here.
More commit info here.More commit info here.

ganguo_all/kjglxt_issue#360（此处既可以是issue右下角的Reference，也可以是issue页面url地址）

```

##### PS: Commit Message 中英都可以，但都请遵循上诉规范

