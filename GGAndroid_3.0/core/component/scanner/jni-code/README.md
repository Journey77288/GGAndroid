## 扫码模块 - 使用注意事项
- 1、jni-code目录下为底层C源码，可通过改源码，编译出对应的so包
- 1、底层Zbar C函数调用，与Module包名有关联，不要轻易调整该模块包名。
- 2、如需更换包名，请在jni-code下找到zbarjni.c文件，替换文件中的函数名称包名部分。
  + 例如io.ganguo.scanner替换为io.ganguo.qrcode，则需要将zbarjni.c文件中的io_ganguo_scanner搜索替换为io_ganguo_qrcode，然后重新编译新的so包，替换libs目录下的so包。