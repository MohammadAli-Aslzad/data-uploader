package com.aslzad.datauploader.response;

public class GenericResponse<T> {
    private T result;

    public GenericResponse() {
    }

    public GenericResponse(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
