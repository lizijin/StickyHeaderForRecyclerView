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
- 支持配合appbarlayout使用

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