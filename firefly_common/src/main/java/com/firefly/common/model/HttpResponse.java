package com.firefly.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class HttpResponse<T> {
    private Map<String, String> header;
    private T payload;

    public HttpResponse() {
        this.header = new HashMap<>();
    }

    public HttpResponse<T> setCode(int code) {
        this.header.put("code", String.valueOf(code));
        return this;
    }

    public HttpResponse<T> setMsg(String msg) {
        this.header.put("msg", msg);
        return this;
    }

    public HttpResponse<T> setMoreInfo(String moreInfo) {
        this.header.put("moreInfo", moreInfo);
        return this;
    }

    @JsonIgnore
    public int getCode() {
        return Integer.parseInt(this.header.getOrDefault("code", "200"));
    }

    @JsonIgnore
    public String getMsg() {
        return this.header.getOrDefault("msg", "success");
    }

    @JsonIgnore
    public String getMoreInfo() {
        return this.header.get("more-info");
    }


    public static <T> HttpResponse<T> success() {
        return new HttpResponse<T>()
                .setCode(200)
                .setMsg("success");
    }
    public static <T> HttpResponse<T> success(T payload) {
        return new HttpResponse<T>()
                .setCode(200)
                .setMsg("success")
                .setPayload(payload);
    }

    public static <T> HttpResponse<T> success(String msg, T payload) {
        return new HttpResponse<T>()
                .setCode(200)
                .setMsg(msg)
                .setPayload(payload);
    }

    public static <T> HttpResponse<T> badRequest(String msg) {
        return new HttpResponse<T>()
                .setCode(400)
                .setMsg(msg);
    }

    public static <T> HttpResponse<T> badRequest(String msg, String moreInfo) {
        return new HttpResponse<T>()
                .setCode(400)
                .setMsg(msg)
                .setMoreInfo(moreInfo);
    }

    public static <T> HttpResponse<T> serverError(String msg) {
        return new HttpResponse<T>()
                .setCode(500)
                .setMsg(msg);
    }

    public static <T> HttpResponse<T> serverError(String msg, String moreInfo) {
        return new HttpResponse<T>()
                .setCode(500)
                .setMsg(msg)
                .setMoreInfo(moreInfo);
    }

    public ResponseEntity<HttpResponse<T>> toResponseEntity() {
        HttpStatus httpStatus;
        switch (this.getCode()) {
            case 200:
                httpStatus = HttpStatus.OK;
                break;
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case 500:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(this, httpStatus);
    }
} 