package com.cafe24.mammoth.oauth2.api.support;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpRequestDecorator extends HttpRequestWrapper {

	private HttpHeaders httpHeaders;

	private boolean existingHeadersAdded;
	
	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	
	public HttpRequestDecorator(HttpRequest request) {
		super(request);
	}
	
	public void addParameter(String name, String value) {
		parameters.add(name, value);
	}
	
	public HttpHeaders getHeaders() {
		if (!existingHeadersAdded) {
			this.httpHeaders = new HttpHeaders();
			httpHeaders.putAll(getRequest().getHeaders());
			existingHeadersAdded = true;
		}
		return httpHeaders;
	}
	
	@Override
	public URI getURI() {
		if (parameters.isEmpty()) {
			return super.getURI();
		}
		return URIBuilder.fromUri(super.getURI()).queryParams(parameters).build();
	}
}