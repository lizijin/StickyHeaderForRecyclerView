StickyHeaderForRecyclerView

这可能是目前为止设计最精良的RecyclerView吸顶库

## 目前支持的功能

- 支持复杂吸顶View功能
- 支持单类型吸顶功能
- 支持多类型吸顶功能
- 支持开启和关闭吸顶功能
- 支持指定位置吸顶功能
- 支持设置吸顶偏移量
- 支持兼容ItemDecoration和ItemAnimator
- 支持RecyclerView数据变化和滚动到指定位置
- 支持自定义RecyclerView上Item吸顶top
- 支持配合AppBarLayout使用

## 更改日志

- 修复吸顶view高度很小时，滑动时容易被回收掉，快速往下滑，吸顶消失的问题
- 增加复杂吸顶View的Demo
- 修复下滑到顶部，吸顶View不消失的bug
- 增加重建吸顶时，如果下一个有可能吸顶的View与当前吸顶区域有重叠，吸顶向上联动
- 增加了处理RecyclerView数据变化时，重建吸顶功能
- 增加了处理RecyclerView滑动到指定位置时，重建吸顶功能

## TODO LIST

- 支持吸顶View复杂动画功能
- 自动注册StickyHeaderModel，避免手动注册
- 不在布局文件中声明吸顶FrameLayout，程序自动处理
- 支持头部导航效果，注册监听事件，吸顶Model增加index属性
- 支持横向RecyclerView吸顶

---

## 效果
- 支持单类型吸顶功能

![支持单类型吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video1.gif)

- 支持多类型吸顶功能

![支持多类型吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video2.gif)

- 支持开启和关闭吸顶功能

![支持开启和关闭吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video3.gif)

- 支持指定位置吸顶功能

![支持指定位置吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video4.gif)

- 支持设置吸顶偏移量

![支持设置吸顶偏移量](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video5.gif)

- 支持自定义RecyclerView上Item吸顶边界自定义

![支持自定义RecyclerView上Item吸顶边界自定义](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video6.gif)

## 使用教程

1. 安装依赖

- 项目build.gradle增加maven地址

```
allprojects {
    repositories {
        jcenter()
        maven {
            url "https://dl.bintray.com/xuanyudaddy/sticky-header-recyclerview"
        }
        google()
    }
    tasks.withType(Javadoc) {
        enabled = false
    }
}
```
- app目录下build.gradle增加依赖

```
dependencies {
   implementation 'com.xuanyu.stickyheader:stickyheader:1.0.1'
 }
```
2. 布局文件增加吸顶Layout,header.layout为吸顶布局的占位布局

```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler.view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></androidx.recyclerview.widget.RecyclerView>


    <FrameLayout
        android:id="@+id/header.layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"></FrameLayout>

     
     
</FrameLayout>
```
3. 创建BaseStickyHeaderModel的子类,泛型对应的Book为RecyclerView ItemView对应的实体类

```
public class BookStickyHeaderModel extends BaseStickyHeaderModel<Book> {

    @Override
    public View getStickyView(Context context) {
        System.out.println("jiangbin getView");
        BookView bookView = new BookView(context);
        return bookView;
    }

    @Override
    public void onBindView(View view, Book data) {

        ((BookView) view).setData(new Book(data.name + "吸顶"));
    }
}

```
4. 编写Adapter实现StickyHeaderAdapter接口，重写transferToStickyHeaderModel方法，默认返回StickyHeaderHelper.transferToStickyHeaderModel(this, position)。您可以做其它特殊处理

```
public class NamingStickyHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderAdapter<Naming> {
    @Override
    public BaseStickyHeaderModel<Naming> transferToStickyHeaderModel(int position) {
        return StickyHeaderHelper.transferToStickyHeaderModel(this, position);
    }
 }    

```
5. 在RecyclerView#setAdapter之后，初始化吸顶代码。需要调用StickyHeaderRegistry.registerTransfer将需要吸顶Item对应的Bean和它对应的StickyHeaderModel一一对应起来

```
 StickyHeaderHelper.init(mRecyclerView, mHeaderLayout, 0);
 StickyHeaderRegistry.registerTransfer(Book.class, BookStickyHeaderModel.class);
```
6. 结束！Enjoy it！！

---


欢迎您访问github项目地址[https://github.com/lizijin/StickyHeaderForRecyclerView](https://github.com/lizijin/StickyHeaderForRecyclerView)，如果您使用本库，请提出您的宝贵意见。

如果你有任何问题，欢迎您关注微信公众号。我将第一时间给您答疑解惑
![欢迎扫码关注公众号](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/byte_station_微信公众号.jpeg)
 

