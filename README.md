# Drible
- A Dribbble App by Android Architecture

![app architecture](http://img1.lukou.com/static/p/blog/medium/0013/40/98/36/13409836.jpg)


------------------edit log----------------------
### 2016.6.5
1. 增加数据中间层对象： Resource<T>, 用于判断数据结果状态：Success，Loading， Error；
2. 分页请求bug：当结果成功或失败的时候，一定要removeObserver()， 防止数据库变化导致数据重复更新；
3. 数据库逻辑：针对分页的数据，增加字段page（表示页面id），删除和查询都通过where page = pageId;


### 2017.6.4
1. 增加TypeConverter:数据库不支持的类型转化为json字符串；
2. 增加list adapter： BaseListRecyclerViewAdapter处理loading，error等viewholder； ListRecyclerViewAdapter处理list数据；
3. 数据库缓存列表数据只缓存前3页， 通过对比数据确定是否更新数据库；

#### 2017.6.2
1. 引入Retrofit封装module： service
2. 初步实现 UI-ViewModel-Repository(db and http) 三层架构； 
3. 通过LiveData与Room数据库结合，由model数据层变化驱动界面的变化，并实现数据持久化；

