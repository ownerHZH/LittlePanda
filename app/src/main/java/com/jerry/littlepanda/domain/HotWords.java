package com.jerry.littlepanda.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry.hu on 18/08/17.
 */

public class HotWords<T> implements Serializable {
    long error_code;
    String reason;
    List<T> result;

    public long getError_code() {
        return error_code;
    }

    public void setError_code(long error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HotWords{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
