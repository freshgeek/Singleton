

## 接口文档

> 传输都是使用对象然后转成json格式字符串传输 utf-8编码
>
> 返回也是json字符串对象

[TOC]

 





### 基本格式如下:

``` java

@Data
public class Message {
    /***
     * 操作类型 :  'register'  'login' 'file'  'text' 'realTime'
     */
    private String type;
	// 自身标识 可以不带,建立连接时已经包含了
    private String resourceIMEI;
	//对方标识 , 隔开 英文逗号
    private String targetIMEI;
	//发送消息体
    private String content;
	// 如下表单
    private UserForm userForm;
	// 传输文件信息才带,非文件信息请置空
    private FileInfo fileInfo;

    @Data
    public static class UserForm{
		//账号
        private String account;
        // 密码
        private String password;
        // 验证码
        private String code;
        
    }

}

```

```java
// 响应格式如下
@Data
public class Result {
    /**
     * 返回编码 默认'0000' 成功 和 '0001' 文件传输部分成功
     其余编码均为异常
     */
    private String code;
    /***
     * 操作类型
     */
    private String type;
    /***
     *  返回信息
     */
    private String message;
    /***
     *  返回数据
     */
    private String data;

}

```

### 使用响应枚举如下:

> 常规状态直接显示即可,如果需要自定义可以根据响应码判断自行输出响应提示信息

``` java

public enum ResultType {

    SUCCESS("0000", "成功"),
    FILE_PART_SUCCESS("0001", "文件片段上传成功"),

    TARGET_IEMI_OFFLINE("1001", "对方不在线"),

    USER_EXISTS_EXCEPTION("2001", "用户已经存在"),
    USER_ACCOUNT_EXCEPTION("2002", "账号或密码错误"),
    USER_CODE_EXCEPTION("2003", "验证码错误"),
    USER_CODE_TIMEOUT_EXCEPTION("2004", "验证码超时"),

    EXCEPTION("4001", "未知异常"),
    METHOD_EXCEPTION("4002", "方法解析异常,请检查传递传输格式"),
    METHOD_NOT_SUPPORT_EXCEPTION("4005", "不支持的方法类型"),
    METHOD_PARAM_EXCEPTION("4003", "方法解析参数异常,请检查传递参数"),
    TARGET_IEMI_NULL_EXCEPTION("4004", "方法解析参数异常:目标对象为空为空"),

    FILE_EXISTS_EXCEPTION("4104", "文件已存在"),
    FILE_NAME_NULL_EXCEPTION("4105", "文件名为空"),
    FILE_NAME_EXCEPTION("4106", "文件名不能包含_"),
    FILE_MAX_EXCEPTION("4107", "文件过大!"),

    EXTEND_EXCEPTION("4999", "自定义返回异常信息"),

    SEND_MESSAGE_FAIL("5001", "发送消息失败"),

    FAIL("9001", "执行失败"),
    FILE_WRITE_ERROR("9003", "文件写入失败");

    ResultType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```



### 建立连接:/websocket/message/自身的标识

> 例如:/websocket/message/123
>
> 自身标识就是-->resourceIMEI:'123'
>
> 例如上面的全路径为:ws://主机:端口/websocket/message/123
>
> 这样就建立了一个自身标识为123的websocket 连接 后面的resourceIEMI 都不用传
>
> 并且,在后面请求内容中传递无效 ,
>
> 在以下示例中的请求中为空的可以不要
>
> 但是必须保持应有的结构
>
> 例如:发送验证 {"type":"sendCode","resourceIMEI":"","content":"","userForm":{"account":"1163518793@qq.com","password":"","code":""},"targetIMEI":""}
>
> 可以请求 {"type":"sendCode","userForm":{"account":"1163518793@qq.com","password":"","code":""}}

> 可以部署代码后 使用 主机:端口/message_demo.html 访问 , 打开f12 调试工具 查看 控制台打印参数

+ ##### 发送验证码

| 请求                                                         | 响应                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"sendCode","resourceIMEI":"","content":"","userForm":{"account":"1163518793@qq.com","password":"","code":""},"targetIMEI":""} | {"code":"0000","message":"成功","total":0,"type":"sendCode"} |



+ ##### 注册

| 请求                                                         | 响应                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"register","resourceIMEI":"","content":"","userForm":{"account":"1163518793@qq.com","password":"O2oadminPwd$","code":"472829"},"targetIMEI":""} | {"code":"2001","message":"用户已经存在","total":0,"type":"register"} |



+ ##### 登录

| 请求:                                                        | 响应                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"login","resourceIMEI":"","content":"","userForm":{"account":"15797735752","password":"111111"},"targetIMEI":""} | {"code":"2002","message":"账号或密码错误","total":0,"type":"login"} |



+ ##### 找回密码

|                                                              |                                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"findPassword","resourceIMEI":"","content":"","userForm":{"account":"1163518793@qq.com","password":"","code":"617728"},"targetIMEI":""} | {"code":"0000","message":"成功","total":0,"type":"findPassword"} |



+ ##### 群发消息

| {"type":"text","resourceIMEI":"","content":"12312","userForm":{"account":"","password":"","code":""},"targetIMEI":"12312"} | {"code":"1001","message":"对方不在线","total":0,"type":"text"} |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"text","resourceIMEI":"","content":"12312","userForm":{"account":"","password":"","code":""},"targetIMEI":"12312,1231231"} | {"code":"1001","message":"对方不在线","total":0,"type":"text"} |

+ ##### 群发广播

|                                                              |                                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| {"type":"realTime","resourceIMEI":"123","content":"123123","userForm":{"account":"1163518793@qq.com","password":"111111","code":"617728"},"targetIMEI":"123123123312312,31212312"} | {"code":"1001","message":"对方不在线","total":0,"type":"realTime"} |



### 建立连接:/websocket/file/自身标识

> 可以部署代码后 使用 主机:端口/upload_demo.html 访问 , 打开f12 调试工具 查看 控制台打印参数

+ ##### 上传文件信息

+ ##### 上传文件

1.这两个是配合使用 , 上传文件流之前必须先将文件的信息上传

``` json
{"type":"sendFileInfo","content":"","userForm":{"account":"","password":"","code":""},"targetIMEI":"","fileInfo":{"fileName":"20180929174347140.png","fileSize":493467,"fileType":"image/png"}}

```

2.接受返回

``` json
{"code":"0001","data":"0","message":"文件片段上传成功","total":0,"type":"sendFileInfo"}
```

3.上传文件流

``` j'son
ArrayBuffer(493467) {}
```

4.接受返回,data 中包含了文件路径

``` json
{"code":"0000","data":"1575618708212_123_20180929174347140.png","message":"成功","total":0,"type":"uploadFile"}
```



+ ##### 转发文件

将刚才接受到的文件路径拼接成可供下载的连接:/api/file/文件路径

``` json
{"type":"sendFile","content":"用户[123]发来的文件:<a href=\"/api/file/1575618826981_123_20180929174347140.png\">点击下载</a>","userForm":{"account":"","password":"","code":""},"targetIMEI":"123123,13123123","fileInfo":null}

```

返回

``` json
{"code":"1001","message":"对方不在线","total":0,"type":"sendFile"}
```







