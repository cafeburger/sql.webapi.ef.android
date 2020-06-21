package com.cafeburger.demoapiandroid.utility;

public interface CallbackHandler<T> {
    void onComplete(T data);
    void onError(String errorMessage);
}
