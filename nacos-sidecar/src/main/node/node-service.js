//引入相关依赖
var http=require('http');
var url=require('url');
var path=require('path');

//创建server
var server = http.createServer(function(request,response){
    //获取请求的URI路径
    var pathname=url.parse(request.url).pathname;
    console.log('pathname==='+pathname);
    response.writeHead(200,{'Content-Type':'application/json; charset=utf-8'});
    //设置访问http://localhost:8060/时，会进入此条件中，返回json数据：{"index":"已进入首页"}
    if(pathname === '/'){
        response.end(JSON.stringify({"index":"已进入首页"}));
    }
    //设置访问http://localhost:8060/health是，会进入此条件中，返回json数据：
    else if(pathname === '/health.json'){
        response.end(JSON.stringify({"status":"UP"}));
    }
    else {
        //其他情况，返回404
        response.end("404");
    }
});

//设置服务端口并监听、打印日志
server.listen(8060,function(){
    console.log('run service successful：localhost:8060');
});
