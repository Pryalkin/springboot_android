package com.student.pryalkin.service;

@FunctionalInterface
public
interface DropboxActionResolver<T> {

    T perform() throws Exception;

}
