# ViewModel结合DiffUtil使用说明文档

## 简介

> DiffUtil是support-v7:24.2.0中的新工具类，它用来比较两个数据集，寻找出旧数据集->新数据集的最小变化量，RecyclerView Adapter结合DiffUtil可以大大提高列表刷新效率及性能。

## 使用说明
> 1、对于页面UI布局复杂、数据变化频率高、数据量大及性能要求比较高的页面，可以结合DiffUtil使用来提高刷新效率。


> 2、要结合甘果MVVM项目架构使用DiffUtil工具类，对应Item ViewModel必须实现IDiffComparator接口，
并重写实现`isDataNotChange(T t)`和`getDiffCompareObject()`函数，实现对应的代码判断逻辑，
DiffUtil进行数据比较时才有效。

> 3、ViewModelAdapter新增OnNotifyDiffUtilDataChangedListener接口，确保使用时有set对应接口参数，主要用于监听数据是否刷新完成，做一些刷新完成后的操作。例如：刷新完成后，开关LoadMore，避免刷新前开启了LoadMore，导致重复触发LoadMore。

## 使用步骤

- 对应Item ViewModel 实现 IDiffComparator接口。

- 重写IDiffComparator中的函数，并实现对应的函数判断等代码逻辑处理。

- ViewModelAdapter Add ViewMoel后，调用` notifyDiffUtilSetDataChanged()`函数，进行数据刷新。而不是`notifyDataSetChanged()`函数。

## 使用注意事项

- 数据`增、删`都可以直接notifyDiffUtilSetDataChanged()来刷新。

- ViewModel`修改数据`的情况，也可以使用notifyDiffUtilSetDataChanged()来刷新，但注意要在ViewModel中对应的isDataEquals（）函数中，对会出现变动的数据进行判断。否则，无法更新。

## IDiffComparator 接口说明

+ getDiffCompareObject( )函数说明。

 + 根据实现IDiffComparator接口时，传递的泛型，返回对应类型的数据对象。泛型可以传Entity、当前实现
IDiffComparator接口的ViewModel，或者其他类型的任意参数。一般建议直接传当前ViewModel从API解析
得到的Entity数据类型，毕竟，在实际应用场景中，一般都是Entity中的数据会发生变化。


+ isDataEquals(T t)函数说明。

 + 用于处理判断比较逻辑逻辑，如果当前ViewModel中的数据同`t`中的数据比较后，如数据无变化(不需要刷新):
return true。数据有变化(需要刷新): return false。

 + 如果实现接口时，使用的泛型是对应的Entity，则直接调用Entity的equals函数进行比较即可。简单快捷。
注意：Entity必须要重写equals函数，并实现对应判断，才有效，如果Entity有使用`lombok`相应的注解，则不
需要重写equals函数了，因为`lombok`库已经自动帮我们重写生成equals函数了。



- 参考代码

![image](/uploads/b28aca27e6cf5e5d3a59d00e976b4afb/image.png)

![image](/uploads/61d9d3fc9bd0a313cec815655435be70/image.png)


## About 
+ [甘果主页](http://ganguo.io/)  
+ [甘果gitlab](https://gitlab.cngump.com/)  
+ [osxtoy by ganguo](http://www.osxtoy.com/)  
+ [Android wiki update log](https://gitlab.cngump.com/ganguo_android/ggandroid/wikis/Android-Wiki-Update-Log)
+ [markdown格式](http://doc.gitlab.com/ce/markdown/markdown.html)