StickyHeaderForRecyclerView
---
## 介绍
StickyHeaderForRecyclerView库主要的功能是方便RecyclerView实现吸顶功能。它目前支持以下功能:

1. 支持单类型吸顶功能
2. 支持多类型吸顶功能
3. 支持开启和关闭吸顶功能
4. 支持指定位置吸顶功能
5. 支持设置吸顶偏移量
6. 支持自定义RecyclerView上Item吸顶边界自定义

## 效果
- 支持单类型吸顶功能

![支持单类型吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_video1.gif)

- 支持多类型吸顶功能

![支持多类型吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_v2.gif)

- 支持开启和关闭吸顶功能

![支持开启和关闭吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_v3.gif)

- 支持指定位置吸顶功能

![支持指定位置吸顶功能](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/dmall/stickyheadern_v4.gif)

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
   implementation 'com.xuanyu.stickyheader:stickyheader:1.0.0'
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

4. 在RecyclerView#setAdapter之后，初始化吸顶代码。需要调用StickyHeaderRegistry.registerTransfer将需要吸顶Item对应的Bean和它对应的StickyHeaderModel一一对应起来

```
 StickyHeaderHelper.init(mRecyclerView, mHeaderLayout, 0);
 StickyHeaderRegistry.registerTransfer(Book.class, BookStickyHeaderModel.class);
```
5. 结束！Enjoy it！！

---


欢迎您访问github项目地址[https://github.com/lizijin/StickyHeaderForRecyclerView](https://github.com/lizijin/StickyHeaderForRecyclerView)，如果您使用本库，请提出您的宝贵意见。

如果你有任何问题，欢迎您关注微信公众号。我将第一时间给您答疑解惑
![欢迎扫码关注公众号](https://cdn.jsdelivr.net/gh/lizijin/bytestation@master/byte_station_微信公众号.jpeg)
 

