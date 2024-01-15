## 简介
项目中经常会遇到使用WebView加载Html富文本的场景，电商项目使用更为频繁，但富文本不同于html网页，富文本中返回的style样式可能不全，导致一些奇奇怪怪的问题。

## 常见问题
+ 字体大小在不同机型上，大小显示不一致，有的机型显示正常，有的机型则显示的字体非常小。
+ 图片无法等比缩放铺满全屏

## 解决方案1
+ 大部分问题几乎都是html样式未做适配、部分css样式是单文件无法和html文本一起下发等原因导致的，最简单粗暴的方式就是让前端把页面写成单页面，做好移动端样式适配，返回html链接即可。一般app内的介绍、关于我们、资讯详情之类的页面，都建议前端做单页面，返回链接。适配和体验，远远优于加载Html文本的方式。


+ 还有一种场景就是WebView在列表中间，用RecyclerView嵌套实现，那么使用加载链接的方案可能就不合适了，WebView滑出屏幕，再滑入屏幕会重新加载链接，导致比较严重的闪烁，所以此种情况只能加载html文本了。此时方案2就登场了。

## 解决方案2
+ 解决方案的本质，实际上还是对html样式做适配，只不过是移动端加载Html时，加上默认的样式，避免图片、字体大小问题。

+ 但移动端设置默认Html样式，仅仅限于html段落中标签未设置style的情况，因为默认的style优先级小于标签中style，例如标签中style重新设置了字体大小，那么移动端默认设置的字体大小就对该标签无效了。此时则需要前端和移动端，对字体大小都使用em单位来适配。一般是移动端定一个基准的尺寸大小，然后前端设置字体大小时，以基准大小来做计算。字体大小em=设计稿字体大小px/移动端基准字体大小px。例如：设计稿字体大小尺寸为30px，移动端基准字体大小为15px，那么html标签中返回的字体大小则是 30px/15px=2em。

+ 综上所述，拼接样式后的html文本为以下内容

```
<!doctype html>
<html class="no-js" lang="">

<head>
  <meta charset="utf-8">
  <style>
    * {
      font-size: 2em //所有标签默认字体大小 12px*2em=24px
    }
    {
      color: #212121
    }
    html {
      font-size: 12px //基准字体大小
    }
    img {
      max-width: 100%;
      width: 100%;
      height: auto;
      padding: 0
      border: 0;
      margin: 0;
      vertical-align: bottom;
    }
    div{
      padding: 0
      border: 0;
      margin: 0;
    }
    body {
      padding: 0;
      margin: 0;
    }
  </style>
</head>

<body>
<p>我是html文本内容（将该内容替换为api返回的html文本即可）</p>
<br>
</body>
</html>

```

## 参考资料
+ [Html-简单粗暴的移动端适配方案](https://imweb.io/topic/5a523cc0a192c3b460fce3a5)
+ [em与px换算](https://www.runoob.com/tags/ref-pxtoemconversion.html)


