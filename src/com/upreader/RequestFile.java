package com.upreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

/**
 * File attached (uploaded) to a Http request
 * 
 * @author Flavius
 * 
 */
public class RequestFile {
	private static final int CHAR_BUFFER_SIZE = 2048;
	private final FileItem part;
	
	private RequestFile(FileItem part, String filename, String contentType) {
		this.part = ((FileItem) checkNotNull(part, "part"));
	}

	public String getParameterName() {
		return this.part.getName();
	}

	public String getFilename() {
		return this.part.getName();
	}
	
	public String getExtension() {
		checkNotNull(this.part, "part");
		checkNotNull(getFilename(), "fileName");
		
		int dot = getFilename().lastIndexOf('.');
		if(dot > 0 && dot < getFilename().length())
			return getFilename().substring(dot+1);
		else
			return null;
	}

	public String getContentType() {
		return this.part.getContentType();
	}

	public long getSize() {
		return this.part.getSize();
	}

	public InputStream getContentAsStream() throws IOException {
		return this.part.getInputStream();
	}

	public byte[] getContentAsBytes() throws IOException {
		return this.part.get();
	}

	public String getContentAsString(Charset charset) throws IOException {
		checkNotNull(charset, "charset");
		InputStreamReader in = new InputStreamReader(getContentAsStream(), charset);
		StringBuilder out = new StringBuilder();
		CharBuffer buffer = CharBuffer.allocate(CHAR_BUFFER_SIZE);
		while (in.read(buffer) != -1) {
			buffer.flip();
			out.append(buffer);
			buffer.clear();
		}
		return out.toString();
	}

	public void writeTo(Path destination) throws IOException {
		checkNotNull(destination, "destination");
		Files.copy(getContentAsStream(), destination, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
	}
	
	public String toString() {
		return String.format("RequestFile{parameterName=%s, fileName=%s, type=%s, size=%s}", new Object[] { getParameterName(),
				getFilename(), getContentType(), Long.valueOf(getSize()) });
	}

	public static RequestFile get(UpreaderRequest request, String parameterName) {
		checkNotNull(request, "request");
		checkNotNull(parameterName, "parameterName");
		if (!request.isMultiPart()) {
			return null;
		}
		
		FileItem item = request.getPart(parameterName);
		
		return fromPart(item);
	}

	public static List<RequestFile> get(UpreaderRequest request) {
		checkNotNull(request, "request");
		if (!request.isMultiPart()) {
			return Collections.emptyList();
		}
		Collection<FileItem> parts = request.getParts();
		if ((parts == null) || (parts.isEmpty())) {
			return Collections.emptyList();
		}
		List<RequestFile> files = null;
		for (FileItem part : parts) {
			RequestFile file = fromPart(part);
			if (file != null) {
				if (files == null) {
					files = new ArrayList<>();
				}
				files.add(file);
			}
		}
		if (files == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(files);
	}

	private static <T> T checkNotNull(T object, String name) {
		if (object == null) {
			throw new IllegalArgumentException("Argument '" + name + "' must not be null.");
		}
		return object;
	}


	private static RequestFile fromPart(FileItem part) {
		if (part == null) {
			return null;
		}
		
		return new RequestFile(part, part.getFieldName(), part.getContentType());
	}
}
