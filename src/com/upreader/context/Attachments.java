package com.upreader.context;

import java.util.List;

import com.upreader.RequestFile;
import com.upreader.UpreaderRequest;

/**
 * Http Request upload handler
 * 
 * @author Flavius
 * 
 */
public class Attachments {
	private final UpreaderRequest request;

	public Attachments(Context context) {
		this.request = context.getRequest();
	}

	public RequestFile get(String name) {
		return RequestFile.get(this.request, name);
	}

	public List<RequestFile> list() {
		return RequestFile.get(this.request);
	}
}