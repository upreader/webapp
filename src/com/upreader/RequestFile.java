package com.upreader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * File attached (uploaded) to a Http request
 * 
 * @author Flavius
 * 
 */
public class RequestFile {
	private static final int BYTE_BUFFER_SIZE = 4096;
	private static final int CHAR_BUFFER_SIZE = 2048;
	private static final Pattern FILENAME_PATTERN = Pattern.compile("(.*)filename=\"(.*)\"");
	private final Part part;
	private final String filename;
	private final String contentType;

	private RequestFile(Part part, String filename, String contentType) {
		this.part = ((Part) checkNotNull(part, "part"));
		this.filename = ((String) checkNotNull(filename, "filename"));
		this.contentType = ((String) checkNotNull(contentType, "contentType"));
	}

	public String getParameterName() {
		return this.part.getName();
	}

	public String getFilename() {
		return this.filename;
	}

	public String getContentType() {
		return this.contentType;
	}

	public long getSize() {
		return this.part.getSize();
	}

	public InputStream getContentAsStream() throws IOException {
		return this.part.getInputStream();
	}

	public byte[] getContentAsBytes() throws IOException {
		InputStream in = getContentAsStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		while (true) {
			int bytesRead = in.read(buffer);
			if (bytesRead == -1) {
				break;
			}
			out.write(buffer, 0, bytesRead);
		}
		return out.toByteArray();
	}

	public String getContentAsString(Charset charset) throws IOException {
		checkNotNull(charset, "charset");
		InputStreamReader in = new InputStreamReader(getContentAsStream(), charset);
		StringBuilder out = new StringBuilder();
		CharBuffer buffer = CharBuffer.allocate(2048);
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
		if (!mightContainFiles(request)) {
			return null;
		}
		HttpServletRequest servletRequest = request.getRawRequest();
		Part part;
		try {
			part = servletRequest.getPart(parameterName);
		} catch (ServletException | IOException e) {
			return null;
		}
		return fromPart(part);
	}

	public static List<RequestFile> get(UpreaderRequest request) {
		checkNotNull(request, "request");
		if (!mightContainFiles(request)) {
			return Collections.emptyList();
		}
		HttpServletRequest servletRequest = request.getRawRequest();
		Collection<Part> parts;
		try {
			parts = servletRequest.getParts();
		} catch (ServletException | IOException e) {
			return Collections.emptyList();
		}
		if ((parts == null) || (parts.isEmpty())) {
			return Collections.emptyList();
		}
		List files = null;
		for (Part part : parts) {
			RequestFile file = fromPart(part);
			if (file != null) {
				if (files == null) {
					files = new ArrayList();
				}
				files.add(file);
			}
		}
		if (files == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(files);
	}

	private static boolean isValidFilename(String filename) {
		if (filename == null) {
			return false;
		}
		Path path;
		try {
			path = Paths.get(filename, new String[0]);
		} catch (InvalidPathException e) {
			return false;
		}
		if ((path.getNameCount() != 1) || (path.isAbsolute())) {
			return false;
		}
		Path normalized = null;
		try {
			normalized = path.normalize();
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		if (!path.equals(normalized)) {
			return false;
		}
		String pathname = normalized.toString();
		if ((pathname.isEmpty()) || (pathname.equals("..")) || (!pathname.equals(filename))) {
			return false;
		}
		return true;
	}

	private static <T> T checkNotNull(T object, String name) {
		if (object == null) {
			throw new IllegalArgumentException("Argument '" + name + "' must not be null.");
		}
		return object;
	}

	private static boolean mightContainFiles(UpreaderRequest request) {
		if (request.isPost()) {
			if (request.getRawRequest().getContentType().startsWith("multipart/form-data"))
				return true;
		}
		return false;
	}

	private static RequestFile fromPart(Part part) {
		if (part == null) {
			return null;
		}

		String contentDisposition = part.getHeader("content-disposition");
		if (contentDisposition == null) {
			return null;
		}
		Matcher matcher = FILENAME_PATTERN.matcher(contentDisposition);
		if (!matcher.matches()) {
			return null;
		}
		String filename = matcher.group(2);

		if (!isValidFilename(filename)) {
			return null;
		}
		String contentType = part.getContentType();
		if (contentType == null) {
			return null;
		}
		return new RequestFile(part, filename, contentType);
	}
}
