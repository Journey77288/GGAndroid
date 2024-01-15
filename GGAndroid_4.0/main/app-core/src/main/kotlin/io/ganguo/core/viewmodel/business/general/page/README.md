#### ViewModel层目录结构简单说明，只列出以下模块做参考
└── viewmodel
    ├── general - 复用/通用模块
    │   ├── activity   界面viewmodel
    │   ├── dialog  弹窗viewmodel
    │   ├── item 复用的Item选项，一般是列表中的item viewmodel
    │   ├── page 一般为ViewPager中嵌套的页面viewmodel
    │   └── window 一般为popupWindow viewmodel
    └── mine  -   个人中心模块
        ├── activity
        ├── dialog
        ├── item
        ├── page
        └── window
   └── xxx  -   xxx模块
        ├── activity
        ├── dialog
        ├── item
        ├── page
        └── window

