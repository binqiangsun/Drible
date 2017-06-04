# Drible
- A Dribbble App by Android Architecture

### 2017.6.5
1. 增加TypeConverter:数据库不支持的类型转化为json字符串；
2. 增加list adapter： BaseListRecyclerViewAdapter处理loading，error等viewholder； ListRecyclerViewAdapter处理list数据；
3. 数据库缓存列表数据只缓存前3页；

#### 2017.6.2
1. 引入Retrofit封装module： service
2. 初步实现 UI-ViewModel-Repository(db and http) 三层架构； 
3. 通过LiveData与Room数据库结合，由model数据层变化驱动界面的变化，并实现数据持久化；

