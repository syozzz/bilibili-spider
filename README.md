在 hexo-sakura 博客中，需要一些番剧数据填充页面，故写此项目爬取 B 站追番数据。  
使用：
 - 修改 HeaderInterceptor.java 中相关 cookie 信息为自己的（可用浏览器登录 B 站之后获取）  
 - 修改 FeignConfig.java 中的 UID  为目标用户
 - 执行 FeignConfig.main() 方法，在类路径下产出 output.txt
 
 