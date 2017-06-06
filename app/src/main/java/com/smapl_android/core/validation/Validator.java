package com.smapl_android.core.validation;


public interface Validator<T> {
    boolean validate(T value) throws ValidationException;
}
