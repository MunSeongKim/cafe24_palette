package com.cafe24.mammoth.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;

/**
 * 
 * @deprecated API 리소스 서버에 대한 사항은 별도 구현
 * @Since <i>2018. 07. 02</i>
 * @author <i>MS Kim<i>
 */
public class Cafe24ResourceDetails extends ResourceServerProperties{
	private String admin;
	private String front;
	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
}
