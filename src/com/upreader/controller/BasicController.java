package com.upreader.controller;

import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;

public abstract class BasicController {
    private final UpreaderHandler handler;
    private final Context context;

    public BasicController(BasicPathHandler handler, Context context) {
        this.handler = (UpreaderHandler) handler;
        this.context = context;
    }

    public UpreaderHandler handler() {
        return handler;
    }

    public Context context() {
        return context;
    }
}
