package com.upreader.controller;

import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;

public class ProjectController {
	private final BasicPathHandler handler;

	public ProjectController(BasicPathHandler handler) {
		this.handler = handler;
	}

	public boolean doCmd(Context context) {
		return false;
	}
}
