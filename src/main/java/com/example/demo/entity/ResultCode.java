package com.example.demo.entity;

/**
 * 响应码枚举，参考HTTP状态码的语义
 *
 * @author ZhangJP
 * @date 2020/10/9
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);


    private int code;

    public int getCode() {
        return code;
    }

    ResultCode(int code) {
        this.code = code;
    }

    /*
     *
     1 消息
     ▪ 100 Continue
     ▪ 101 Switching Protocols
     ▪ 102 Processing
     2 成功
     ▪ 200 OK
     ▪ 201 Created
     ▪ 202 Accepted
     ▪ 203 Non-Authoritative Information
     ▪ 204 No Content
     ▪ 205 Reset Content
     ▪ 206 Partial Content
     ▪ 207 Multi-Status
     3 重定向
     ▪ 300 Multiple Choices
     ▪ 301 Moved Permanently
     ▪ 302 Move Temporarily
     ▪ 303 See Other
     ▪ 304 Not Modified
     ▪ 305 Use Proxy
     ▪ 306 Switch Proxy
     ▪ 307 Temporary Redirect
     4 请求错误
     ▪ 400 Bad Request
     ▪ 401 Unauthorized
     ▪ 402 Payment Required
     ▪ 403 Forbidden
     ▪ 404 Not Found
     ▪ 405 Method Not Allowed
     ▪ 406 Not Acceptable
     ▪ 407 Proxy Authentication Required
     ▪ 408 Request Timeout
     ▪ 409 Conflict
     ▪ 410 Gone
     ▪ 411 Length Required
     ▪ 412 Precondition Failed
     ▪ 413 Request Entity Too Large
     ▪ 414 Request-URI Too Long
     ▪ 415 Unsupported Media Type
     ▪ 416 Requested Range Not Satisfiable
     ▪ 417 Expectation Failed
     ▪ 418 I'm a teapot
     ▪ 421 Misdirected Request
     ▪ 422 Unprocessable Entity
     ▪ 423 Locked
     ▪ 424 Failed Dependency
     ▪ 425 Too Early
     ▪ 426 Upgrade Required
     ▪ 449 Retry With
     ▪ 451 Unavailable For Legal Reasons

     */
}

