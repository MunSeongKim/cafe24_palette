package com.cafe24.mammoth.oauth2.api.support;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class URIBuilder {
	
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
				for (Iterator<String> valueIt = values.iterator(); valueIt.hasNext();) {
					String value = valueIt.next();
					builder.append(formEncode(name)).append("=");
					if (value != null) {
						builder.append(formEncode(value));
					}
					if (valueIt.hasNext()) {
						builder.append("&");
					}
				}
				if (entryIt.hasNext()) {
					builder.append("&");
				}
			}

			String queryDelimiter = "?";
			if (URI.create(baseUri).getQuery() != null) {
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
		} catch (UnsupportedEncodingException wontHappen) {
			throw new IllegalStateException(wontHappen);
		}
	}
	
	/**
	 * 파라미터가 요청 주소에 추가 될 때.
	 * @param path
	 * @param apiUrl, parameters
	 * @return {@link URI} 
	 * @author qyuee
	 * @since 2018-07-03
	 */
	public static URI buildApiUri(String apiUrl, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(apiUrl).queryParams(parameters).build();
	}
	
	/**
	 * GET, DELETE, UPDATE Method 사용 시, Uri에 query String이 아닌 pathVariable 형태로 <br>
	 * 값을 전달 할 때 사용하는 메소드. <br>
	 * 
	 * [결과 uri]<br>
	 * ex) buildApiUri(str1, str2, str3)<br>
	 * "https://ex1.cafe24.com/api/v2/admin/scripttags/value1/value2/value3" 의 형태.<br>
	 * 
	 * @param apiUrl, uriValues
	 * @return {@link URI}
	 * @author qyuee
	 * @since 2018-07-05
	 */
	public static URI buildApiUri(String apiUrl, String...uriValues) {
		List<String> uriValueList = new LinkedList<>();
		for(int i=0; i<uriValues.length; i++) {
			uriValueList.add("/"+(String)uriValues[i]);
		}
		String addtionalUriValue = String.join("", uriValueList);
		return URIBuilder.fromUri(apiUrl+addtionalUriValue).build();
	}
	
	/**
	 * 요청 주소에 파라미터가 포함되지 않을 때.
	 * @param apiUrl
	 * @return {@link URI}
	 * @author qyuee
	 * @since 2018-07-03
	 */
	public static URI buildApiUri(String apiUrl) {
		return URIBuilder.fromUri(apiUrl).build();
	}
	
	
}