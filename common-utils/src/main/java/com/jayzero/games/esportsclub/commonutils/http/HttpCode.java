package com.jayzero.games.esportsclub.commonutils.http;

/**
 * HttpCodes
 *
 * @author 成至
 * @version HttpCodes.java, v0.1
 * @date 2017/09/08
 */
public enum HttpCode {

    /**
     * Response to a successful GET, PUT, PATCH or DELETE.
     * Can also be used for a POST that doesn't result in a creation.
     */
    OK(200, "OK"),

    /**
     * Response to a POST that results in a creation.
     * Should be combined with a Location header pointing to the location of the new resource
     */
    CREATED(201, "Created"),

    /**
     * Response to a successful request that won't be returning a body (like a DELETE request)
     */
    NO_CONTENT(204, "No Content"),

    /**
     * Used when HTTP caching headers are in play
     */
    NOT_MODIFIED(304, "Not Modified"),

    /**
     * The request is malformed,such as if the body does not parse
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * When no or invalid authentication details are provided.Also useful to trigger an auth popup if
     * the API is used from a browser
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * When authentication succeeded but authenticated user doesn't have access to the resource
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * When a non-existent resource is requested
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * When an HTTP method is being requested that isn't allowed for the authenticated user
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * 表示不再支持老版本API
     * Indicates that the resource at this end point is no longer available.
     * Useful as a blanket response for old API versions
     */
    GONE(410, "Gone"),

    /**
     * If incorrect content type was provided as part of the request
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * 无法处理的对象 Used for validation errors
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    /**
     * 超过Api调用频率限制
     * When a request is rejected due to rate limiting
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),

    SERVICE_ERROR(500, "Service Error"),;

    private int code;

    private String name;

    HttpCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}