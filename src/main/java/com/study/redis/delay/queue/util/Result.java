package com.study.redis.delay.queue.util;

import lombok.Data;

/**
 * Response结构：{’success’:true/false, ‘error’:’error reason’, ‘id’:’xxx’, ‘value’:’job body'}
 */
@Data
public class Result {
    private boolean success;
    private String error;
    private String id;
    private String value;

    public static Result ok(String id, String value) {
        Result result = new Result();
        result.success = true;
        result.id = id;
        result.value = value;
        return result;
    }

    public static Result error(String error) {
        Result result = new Result();
        result.success = false;
        result.error = error;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "'success'=" + success +
                ", 'error'='" + error + '\'' +
                ", 'id'='" + id + '\'' +
                ", 'value'='" + value + '\'' +
                '}';
    }
}
