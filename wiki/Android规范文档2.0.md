# Android部门开发规范


## 简介
请大家在开发时，遵循以下编程规范
+ 文档版本：V2.0
+ 修订日期：2020年07月23号
+ 修订人员：Leo
+ 联系邮箱：leo@ganguo.hk


## 一、注释规范
##### 1.1 类注释规范【强制】
+ a.说明：所有class文件头部必须有：功能名称说明、类创建日期、创建人
+ b.示例:

```java
/**
 * <pre>
 *     author : leo
 *     time   : 2018/11/01
 *     desc   : Apk信息展示
 * </pre>
 */
public class ApkInfoViewModel{
}
```
c.顶部注释设置模板，可在as中统一设置
```
/**
 * <pre>
 *     author : ${USER}
 *     time   : ${YEAR}/${MONTH}/${DAY}
 *     desc   :
 * </pre>
 */
```

##### 1.2 函数注释规范【强制】
+ a.说明：除Entity中的get、set函数，其他自定义函数都必须加上注释、变量说明(根据场景决定，最好是都加)、是否有返回值
+ b.示例：

```java
/**
 * bitmap 转 byteArr
 *
 * @param bitmap bitmap 对象
 * @param format 格式
 * @return 字节数组 （建议注释，不强制）
 */
public static byte[] bitmap2Bytes(Bitmap bitmap, CompressFormat format) {
    if (bitmap == null) return null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(format, 100, baos);
    return baos.toByteArray();
}

```
##### 1.3 块注释规范【强制】
```java
/*
 * This is
 * okay.
 */

// And so
// is this.

/* Or you can
* even do this. */

// TODO: 17/3/14 需要实现，但目前还未实现的功能的说明
// FIXME: 17/3/14 需要修正，甚至代码是错误的，不能工作，需要修复的说明

```

## 二、资源命名
### 注意事项
+ 代码中的命名严禁使用拼音、英文混合的方式，更不允许直接使用中文的方式
+ 项目包名请使用com.projectName.app，避免使用com.android、com.google等前缀开头

##### 2.1 常用类型 - 命名规范【强制】

| 类型 | 命名规范 | 示例 |
| --- | --- | --- |
| Activity | 功能名称+Activity | LoginActivity |
| Fragment | 功能名称+Fragment | UserFragment |
| Adapter | 功能名称+Adapter | MovieAdapter |
| Dialog | 功能名称+Dialog | MovieDetailDialog |
| Service | 功能名称+ Service | JpushService |
| BroadcastReceiver | 功能名称+ Receiver | JpushReceiver |
| ... | ... | ... |

#####  2.2 ViewModel - 命名规范【强制】

```
ViewModel类名过长时，ViewModel可简写为VModel
```

| 类型 | 命名规范 | 示例 |
| --- | --- | --- |
| Activity/View/Fragment | 功能名称+类型+ViewModel | FragmentMainVModel |
| Dialog | Dialog+功能名称+ViewModel | DialogShareVModel |
| PopupWindow | PW+功能名称+ViewModel | PWEidtNameVModel |
| list item | item+功能名称+ViewModel | ItemEidtPwdVModel |
| ViewPager View | Page+功能名称+ViewModel | PageHomeLoginVModel |


##### 2.3 Java类、接口、变量等 - 命名规范
+ 【强制】类：大驼峰式，首字母为大写，其他字母均为小写；如果类名称由多个单词组成，则每个单词的首字母均应为大写。例如：UserInfo
+ 【强制】接口：需要以 I 或者 On开头，以Callback、Listener或者Interface结尾，例如`IDownloadCallback`，`OnClickListener`、`ViewInterface`
+ 【强制】变量：小驼峰式，不限定开头字母。例如`user`
+ 【强制】常量：常量的名字应该都大写，如果一个常量名称由多个单词组成，则应该用下划线来分割这些单词，例如：CONFIG_LOGIN_USER
+ 【强制】Key：必须以常量作为key，不能在代码中直接以String、int等数据，直接作为key
  

#####  2.4 xml - 命名规范【强制】

| 类型 | 命名规范 | 示例 |
| --- | --- | --- |
| Activity | 类型_功能名 | activity_login.xml |
| Fragment | 类型_功能名 | fragment_user.xml |
| Dialog | 类型_功能名 | dialog_loading.xml |
| Window | 类型_功能名 | window_buy.xml |
| item | 类型_功能名 | item_tab.xml |
| include | 类型_功能名 | include_goods_detail.xml |

##### 2.5 控件ID - 命名规范【强制】

| 控件 | 命名规范 | 示例 |
| --- | --- | --- |
| RelativeLayout | rly_ | rly_home |
| LinearLayout | lly_ | lly_home |
| FrameLayout | fly_ | fly_home |
| TableLayout | tly_ | tly_type |
| TextView | tv | tv_password |
| ImageView | iv_ | iv_avatar |
| Button | btn_ | btn_delete |
| ImageButton | im_ | im_sure |
| RadioGroup | rb_ | rb_name |
| ProgressBar| pb_ | pb_loading |
| EditText | et_ | et_name |
| ScrollView | sv_ | sv_home |
| CheckBox | cb_ | cb_like |
| RecyclerView | rv_ | rv_home |
| ... | ..._ | ..._home |

##### 2.6 Drawable 资源【强制】

| 类型| 命名规范 | 示例 |
| --- | --- | --- |
| 复用selector（颜色） | selector_HighColor_color | selector_ffffff_000000.xml |
|复用shape | shape_类型_属性_值...(顺序以solid->stroke->corners排列) | shape_solid_color_ffffff_stroke_width_1dp_corners_radius_5dp.xml |
| 模块selector | 类型_模块_功能名称(如有复用样式，可在xml文件类，应用复用样式，例如shape_solid_color_ffffff.xml) | bg_mine_login.xml|
| icon | ic_名称 | ic_home_tab
| 背景 | bg_名称 | bg_avatar


##### 2.7 颜色资源命名规
+ 【强制】1、颜色值直接以color_hex(hex统一小写)颜色值命名，方便使用时查找。示例：color_d0183b
+ 【强制】2、具体引用处则以color_module_name命名，方便后期更换颜色值及迭代实现多主题需求。示例：color_home_title

##### 2.8 字符串资源规范
+ 【强制】1、字符串资源命名规范：直接以str_module_name规范进行(module为模块名称，name为字符串名称`过长可以用简单的缩写`)命名，方便使用时查找。示例：str_home_title
+ 【强制】2、不同页面、模块间字符串不进行复用，避免后期修改导致连锁反应。示例：str_home_user_name，str_mine_user_name

##### 2.9 dimen命名规范
+ 【强制】dp+功能名称，例如：dp_fab_margin
+ 【强制】px+功能名称，例如：px_tab_height 
+ 【强制】sp+功能名称，例如：sp_tab_font_size 

```
一般情况用GGAndroid中已经封装好的dimen单位即可，特殊情况，请按以上规则命名
```

##### 2.10 函数命名
+ 【推荐】设置/获取某个值的方法，应该遵循setV/getV规范
+ 【推荐】返回长度的方法，应该命名为length
+ 【强制】测试某个布尔值的方法，应该命名为isV
+ 【推荐】将对象转换为某个特定类型的方法应该命名为toF
+ 【强制】click点击事件函数，应该命名为action+功能名称，例如：actionHome()
+ 【强制】切换显示状态函数，应该命名为show+功能名称、hide+功能名称，例如：showLoginDialog()、hideLoginDialog()

##### 2.11 常量命名
+ 【强制】代码中相关key，不能直接写字符串在代码中，必须先统一定义管理常量，然后在需要的地方引用。例如PARAM_PAGE

##### 2.12 DataBinding Adapter命名规范
+ 【强制】class：以`Binding+View类型+Adapter`进行命名。例如BindingTextViewAdapter,BindImageViewAdapter
+ 【强制】binding属性：以`android:bind_属性名称`进行命名，使用android:前缀在xml文件中，自定义属性也会有补全提示，以bind_开头可以避免覆盖系统属性
+ 【强制】binding函数以`onBind+功能名称`进行命名

```
//示例

/**
 * <pre>
 *     author : leo
 *     time   : 2018/05/29
 *     desc   : 图片加载DataBinding工具类
 * </pre>
 */
public class BindingImageAdapter extends BindingViewAdapter {


    /**
     * xml绑定本地图片
     *
     * @param imageView
     * @param path      本地图片路径
     */
    @BindingAdapter(value = {"android:bind_image_file_path"})
    public static void onBindImageFilePath(ImageView imageView, String path) {
        if (Strings.isEmpty(path) || !Files.checkFileExists(path)) {
            return;
        }
        PhotoPicassoLoader.displayImage(imageView, new File(path), null, null);
    }
}
```


## 三、UI规范
##### 3.1 AppIcon尺寸规范【参考】
| 名称 | 尺寸 |
| --- | --- |
| LDPI | 36 x 36 |
| MDPI | 48 x 48 |
| MDPI | 64 x 64 |
| MDPI | 72 x 72 |
| XHDPI | 96 x 96 |
| XXHDPI | 144 x 144 |
| XXXHDPI | 192 x 192 |
[APP图标模板规范大全](http://www.25xt.com/iconweb/3781.html), PSD模板可以看附件psd

##### 3.2 尺寸换算关系【参考】
a、 各DPI的换算

| 名称 | 对应DPI | 比例（以mdpi为基数1） | 和px的换算关系 |
| --- | --- | --- | --- |
| lpdi | 120 DPI | 0.75 | 1 dp = 0.75 px |
| mdpi | 160 DPI | 1 | 1 dp = 1 px |
| hdpi | 240 DPI | 1.5 | 1 dp = 1.5 px |
| xhdpi | 320 DPI | 2 | 1 dp = 2 px |
| xxhdpi | 480 DPI | 3 | 1 dp = 3 px |
| xxxhdpi | 640 DPI| 4 | 1 dp = 4 px |

b、 dp 到 px
```
这里以：400 dp x 240 dp 为例，以下表格为换算结果
```

| 名称 | 换算结果(dp转px) |
| --- | --- |
| lpdi | 300 px X 180 px |
| mdpi | 400 px X 240 px |
| hdpi | 600 px X 360 px |
| xhdpi | 800 px X 480 px |
| xxhdpi | 1200 px X 720 px |
| xxxhdpi | 600 px X 960 px |

c、 px 到 dp

```
从 px 换算成 dp 要知道它是以什么 dpi 标准来设计的，根据换算关系表可得到相应的 dp 。 
例如： 以 xxhdpi 标准设计的 UI，其中一个切图的分辨率是 600 px * 360 px ，根据换
算关系表可知，在 xxhdpi 标准下，1 dp = 3 px ,则其对应的 dp 是 200 dp * 120 
dp 
```

##### 3.3 切图规范
+ 【强制】切图统一切一份xxhdpi尺寸icon
+ 【强制】启动页面图片需要hdpi、xhdpi、xxhdpi、xxxhdpi分别各切一份图片，并且需要针对全面屏进行适配

##### 3.4 UI布局规范
+ 【参考】布局中要减少ViewGroup多层嵌套，如不得不使用ViewGroup多层嵌套时，请使用RelativeLayout、ConstraintLayout来减少布局嵌套层级，优化布局时测量、渲染所带来的耗时过多及性能损耗
+ 【推荐】设计xml布局时，不建议多次为子view和父view设置同样的背景，造成页面过度绘制。推荐将不需要显示的布局进行及时隐藏
+ 【推荐】能使用RecyclerView替代的场景就尽量使用RecyclerView
+ 【强制】禁止在非UI线程进行View相关操作，在子线程如果要操作UI，应使用Handler切换到UI线程
+ 【参考】尽量不要使用 AnimationDrawable，它在初始化的时候就将所有图片加载到内存中，特别占内存，并且还不能释放，释放之后下次进入再次加载时会报错，如必须使用，请使用[优化方案](https://blog.csdn.net/wanmeilang123/article/details/53929484)
+ 【强制】布局标签在没有嵌套View的情况下及时关闭标签，例如TextView、ImageView等不属于ViewGroup一类的控件，在使用时要直接闭合标签，提高代码整洁度
```java 
// 反例：
   <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@drawable/ripple_default_boundaries"
            android:gravity="center"
            android:onClick="@{data.onReviewApkInfoClick()}"
            android:text="apk信息">
            
        </TextView>        
//正例：
   <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@drawable/ripple_default_boundaries"
            android:gravity="center"
            android:onClick="@{data.onReviewApkInfoClick()}"
            android:text="apk信息"/> 
```

## 四、日常开发注意事项
##### 4.1 注解使用
+ 【推荐】平常要善用@ColorInt、 @ColorRes、@Dimension、@DimenRes、@DrawableRes等等注解，进行参数限定，避免传参出错，导致出现一些低级的bug
+ 【推荐】要善用自定义注解，进行参数限定，同时可以替代枚举，优化内存
+ 自定义注解示例：

```java
/**
* Type
* 获取图片的方式
*/
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {ChooseMode.CAMERA, ChooseMode.GALLERY})
public @interface ChooseMode {
    int CAMERA = 100;//通过相机
    int GALLERY = 200;//通过系统相册/文件夹
}
```
##### 4.2 类库选型
+ 【参考】不常用第三方类库选型时，要挑选热度最高，且在持续维护的开源项目
+ 【强制】选用第三方类库时，需要先下载对应的Demo进行查看，并且针对不同的机型进行测试，确认没有问题时，才确认选用
+ 【强制】选用从未使用过的第三方类库时，必须征得开发经理同意，避免出现不合适，反复更换的情况

##### 4.3 代码编写规范
+ 【强制】一个函数中不能包含多个模块的逻辑代码块，`不允许一个函数出现上百行代码的情况`，应按逻辑封装成对应模块函数，进行调用
+ 【参考】函数命名尽量简单易读，函数名过长时可以用简单缩写，例如：getPsdEditView()
+ 【参考】设计类及函数时，要考虑未来的变动，提高代码的可扩展性。而不是改一处，就全部重写一次
+ 【参考】设计代码的原则，低耦合、高复用
+ 【参考】要针对接口编程，而不是针对具体的实现，善用设计模式
+ 【强制】控件或对象引用，控件引用名称过长时，应封装成get方法

```java
   
   //例如 ：getView().getBinding().etPassword，应封装成以下函数，再调用.

   /**
     * function：get password EditText
     *
     * @return
     */
    public EditText getPsdEditView() {
        return getView().getBinding().etPassword;
    }

```

##### 4.4 app测试与优化
+ 【推荐】开发要多自测，避免出现低级的UI错误
+ 【强制】项目必须引入leakcanary，进行内存及性能检测
+ 【强制】项目必须集成bugly或者其他类似平台产品（默认bugly，客户如特殊要求，则用客户要求的平台），进行异常捕获，排查问题
+ 【强制】项目中必须包含一个apk包版本信息入口页面



## 五、app打包规范
+ 打包前，检查keystore签名文件是否有更新为客户提供的，理论上打第一个测试包时，就需要更换
+ 检查项目中是否有ApkInfoActivity，没有则在设置页面中添加`以下信息`或者新增ApkInfoActivity，并在设置中添加对应页面菜单入口。`只针对测试包，提供入口`，[示例页面](uploads/f9b3669ef3b77e4d8529d9c7d38ae9e9/image.png)
  + 打包环境
  + BaseUrl
  + 打包时间
  + 设备SDK版本
  + 系统版本
  + 设备型号
  + 版本名称
  + 版本号
  + ...根据项目情况增加
+ 确认VersionCode、VersionName、minSdkVersion是否需要更新
+ 特殊版本，请在打完包的apk名称中添加tag
+ 打包安装测试，确定没有问题，上传到蒲公英
+ 上传完成最后一步注意，设置安装密码，并且不需要同步应用市场信息
```
安装密码命名规范：git项目名称前缀，例如日本电商项目，git项目名称为jpeshop_android，则安装密码为jpeshop
```

## 六、app上架及流程规范

```
需要确认以下资料和配置完全正确，方可提交上架
```
+ 必须同项目经理确认所打的包，是否已完全满足上线条件，有无需要隐藏功能
+ 必须检查是否有更换keystore文件为客户方提供的文件
+ 必须检查确认apk支持mini sdk版本是否为合同标注版本
+ 必须检查所提供的包是否为production包
+ 必须检查相关平台key是否已更换为正式环境key
+ 必须检查api BaseUrl是否为正式环境url
+ 检查上架说明、资料是否齐全

## 七、项目交付流程
+ 准备好交付文档，以markdown格式书写，放入代码根目录README.md文件下
+ 提交完整的代码，并添加tag
+ 通知部门经理已经做好交付准备，由部门经理发送交付代码及文档给项目部门

