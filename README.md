# springLearn-Project
用于对spring的学习和demo搭建的工程，目前主要想的是master不写项目，创建多个分支用于学习springboot及springCloud项目，按照分支名来明确搭建的项目属于哪一类。

每天进步一点点，加油努力！！！

这个项目顺便记录工作遇到的问题和本身思考的疑问：
1 openfegin 是否可以不使用注册中心，通过url参数指定请求路径。
2 xgg加解密是通过数据库拦截器实现的（ibatis拦截器 + 注解） 还未自己手动实现
3 spring cloud 跨域如何实现的，如何注入crosFilter的配置。
4 java函数式编程学习
5 computerFuture框架学习（forkJoin框架）
6 浏览器跨域问题（origin header头的影响）,学习总结跨域遇到的问题。
7 @bean 在方法上在哪一步进行的实例化
8 spring的事件监听如何做到的（深挖设计模式）
9 测试mybatis的scan包下游实体类被扫描到是否报错。


问题1：
导出项目查看Aop是否支持多注解继承拦截，否则需要自己注入代理类实现这个方法了。
问题2：
导出项目parse逻辑补充，画出流程图，否则自己都会忘记设计。




0929：
今天看到了一个maven的脚手架搭建的视频，因此今天需要搭建一个基础的脚手架.
目标：
1 搭建一个基础的脚手架

2 上传脚手架到maven仓库
3 使用脚手架创建项目


10.27:
1 自定义executor实例方式   完成
2 executor 实例化的pageArgs如何修改（提供扩展接口）  不修改，把pageArgs放到executor中，
因为以后想要支持其他文件格式的导出，因此只需要实现对应executor就可以了   完成
3 在sub和normal上提供限制接口，可以提供限制最大导出数量功能  部分完成
目前normal\sub模式下，通过limit限制最大导出数量,操作方式不完美,可以后续继续改.
sub模式下,超过limit限制会报错
normal模式下,最多会查limit条数据
4 annotation 是否支持多注解方式，不支持可能需要使用proxy进行操作 (feature 版本需要修改,通过代理实现)

11.07
完成通过json/xml自动生成实体的实现
1 方案调研2天（考虑可以动态适配其他文件类型）
