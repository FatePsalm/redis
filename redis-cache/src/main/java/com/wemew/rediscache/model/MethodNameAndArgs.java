package com.wemew.rediscache.model;

import java.util.Arrays;

public class MethodNameAndArgs {
    private String name;
    private Class<?>[] args;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getArgs() {
        return args;
    }

    public void setArgs(Class<?>[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "MethodNameAndArgs{" +
                "name='" + name + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
