package com.supermonkey.lifeassistant.capabilities.http;

/**
 * @author supermonkey
 * @version 1.0
 * @date 2017/4/20
 * @Description <参数类>
 */
public class Param {
    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value != null ? value : "";
    }

    public Param(String key, int value) {
        this.key = key;
        this.value = value + "";
    }

    String key;

    String value;
}