package com.cafe24.mammoth.oauth2.api.support;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class URIBuilder{
		
		private final String baseUri;
		
		private MultiValueMap<String, String> parameters;

		private URIBuilder(String baseUri) {
			this.baseUri = baseUri;
			parameters = new LinkedMultiValueMap<String, String>();
		}
		
		public static URIBuilder fromUri(URI baseUri) {
			return new URIBuilder(baseUri.toString());
		}
		
		public static URIBuilder fromUri(String baseUri) {
			return new URIBuilder(baseUri);
		}
		
		public URIBuilder queryParams(MultiValueMap<String, String> params) {
			parameters.putAll(params);
			return this;
		}
		
		public URI build() {
			try {
				StringBuilder builder = new StringBuilder();
				Set<Entry<String, List<String>>> entrySet = parameters.entrySet();
				for (Iterator<Entry<String, List<String>>> entryIt = entrySet.iterator(); entryIt.hasNext();) {
					Entry<String, List<String>> entry = entryIt.next();
					String name = entry.getKey();
					List<String> values = entry.getValue();
					for(Iterator<String> valueIt = values.iterator(); valueIt.hasNext();) {
						String value = valueIt.next();
						builder.append(formEncode(name)).append("=");
						if(value != null) {
							builder.append(formEncode(value));
						}
						if(valueIt.hasNext()) {
							builder.append("&");
						}
					}
					if(entryIt.hasNext()) {
						builder.append("&");
					}
				}
				
				String queryDelimiter = "?";
				if(URI.create(baseUri).getQuery() != null) {
					queryDelimiter = "&";
				}
				return new URI(baseUri + (builder.length() > 0 ? queryDelimiter + builder.toString() : ""));
			} catch (URISyntaxException e) {
				throw new RuntimeException();
			}
		}
		
		private String formEncode(String data) {
			try {
				return URLEncoder.encode(data, "UTF-8");
			}
			catch (UnsupportedEncodingException wontHappen) {
				throw new IllegalStateException(wontHappen);
			}
		}
	}