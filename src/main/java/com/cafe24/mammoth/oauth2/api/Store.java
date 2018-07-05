package com.cafe24.mammoth.oauth2.api;

import java.io.Serializable;

import com.cafe24.mammoth.oauth2.api.impl.json.StoreJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = StoreJsonDeserializer.class)
public class Store implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("shop_no")
	private String shopNo;
	
	@JsonProperty("shop_name")
	private String shopName;
	
	@JsonProperty("mall_id")
	private String mallId;
	
	private String baseDomain;
	private String primaryDomain;
	private String companyRegistrationNo;
	private String companyName;
	private String presidentName;
	private String companyCondition;
	private String companyLine;
	private String country;
	private String zipcode;
	private String address1;
	private String address2;
	private String phone;
	private String fax;
	private String email;
	private String mallUrl;
	private String mailOrderSalesRegistration;
	private String mailOrderSalesRegistrationNumber;
	private String missingReportReasonType;
	private String missingReportReason;
	private String aboutUsContents;
	private String companyMapUrl;
	private String customerServicePhone;
	private String customerServiceEmail;
	private String customerServiceFax;
	private String customerServiceSms;
	private String customerServiceHours;
	private String privacyOfficerName;
	private String privacyOfficerPosition;
	private String privacyOfficerDepartment;
	private String privacyOfficerPhone;
	private String privacyOfficerEmail;
	private String contactUsMobile;
	private String contactUsContents;
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}
	public String getBaseDomain() {
		return baseDomain;
	}
	public void setBaseDomain(String baseDomain) {
		this.baseDomain = baseDomain;
	}
	public String getPrimaryDomain() {
		return primaryDomain;
	}
	public void setPrimaryDomain(String primaryDomain) {
		this.primaryDomain = primaryDomain;
	}
	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}
	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPresidentName() {
		return presidentName;
	}
	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}
	public String getCompanyCondition() {
		return companyCondition;
	}
	public void setCompanyCondition(String companyCondition) {
		this.companyCondition = companyCondition;
	}
	public String getCompanyLine() {
		return companyLine;
	}
	public void setCompanyLine(String companyLine) {
		this.companyLine = companyLine;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMallUrl() {
		return mallUrl;
	}
	public void setMallUrl(String mallUrl) {
		this.mallUrl = mallUrl;
	}
	public String getMailOrderSalesRegistration() {
		return mailOrderSalesRegistration;
	}
	public void setMailOrderSalesRegistration(String mailOrderSalesRegistration) {
		this.mailOrderSalesRegistration = mailOrderSalesRegistration;
	}
	public String getMailOrderSalesRegistrationNumber() {
		return mailOrderSalesRegistrationNumber;
	}
	public void setMailOrderSalesRegistrationNumber(String mailOrderSalesRegistrationNumber) {
		this.mailOrderSalesRegistrationNumber = mailOrderSalesRegistrationNumber;
	}
	public String getMissingReportReasonType() {
		return missingReportReasonType;
	}
	public void setMissingReportReasonType(String missingReportReasonType) {
		this.missingReportReasonType = missingReportReasonType;
	}
	public String getMissingReportReason() {
		return missingReportReason;
	}
	public void setMissingReportReason(String missingReportReason) {
		this.missingReportReason = missingReportReason;
	}
	public String getAboutUsContents() {
		return aboutUsContents;
	}
	public void setAboutUsContents(String aboutUsContents) {
		this.aboutUsContents = aboutUsContents;
	}
	public String getCompanyMapUrl() {
		return companyMapUrl;
	}
	public void setCompanyMapUrl(String companyMapUrl) {
		this.companyMapUrl = companyMapUrl;
	}
	public String getCustomerServicePhone() {
		return customerServicePhone;
	}
	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}
	public String getCustomerServiceEmail() {
		return customerServiceEmail;
	}
	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}
	public String getCustomerServiceFax() {
		return customerServiceFax;
	}
	public void setCustomerServiceFax(String customerServiceFax) {
		this.customerServiceFax = customerServiceFax;
	}
	public String getCustomerServiceSms() {
		return customerServiceSms;
	}
	public void setCustomerServiceSms(String customerServiceSms) {
		this.customerServiceSms = customerServiceSms;
	}
	public String getCustomerServiceHours() {
		return customerServiceHours;
	}
	public void setCustomerServiceHours(String customerServiceHours) {
		this.customerServiceHours = customerServiceHours;
	}
	public String getPrivacyOfficerName() {
		return privacyOfficerName;
	}
	public void setPrivacyOfficerName(String privacyOfficerName) {
		this.privacyOfficerName = privacyOfficerName;
	}
	public String getPrivacyOfficerPosition() {
		return privacyOfficerPosition;
	}
	public void setPrivacyOfficerPosition(String privacyOfficerPosition) {
		this.privacyOfficerPosition = privacyOfficerPosition;
	}
	public String getPrivacyOfficerDepartment() {
		return privacyOfficerDepartment;
	}
	public void setPrivacyOfficerDepartment(String privacyOfficerDepartment) {
		this.privacyOfficerDepartment = privacyOfficerDepartment;
	}
	public String getPrivacyOfficerPhone() {
		return privacyOfficerPhone;
	}
	public void setPrivacyOfficerPhone(String privacyOfficerPhone) {
		this.privacyOfficerPhone = privacyOfficerPhone;
	}
	public String getPrivacyOfficerEmail() {
		return privacyOfficerEmail;
	}
	public void setPrivacyOfficerEmail(String privacyOfficerEmail) {
		this.privacyOfficerEmail = privacyOfficerEmail;
	}
	public String getContactUsMobile() {
		return contactUsMobile;
	}
	public void setContactUsMobile(String contactUsMobile) {
		this.contactUsMobile = contactUsMobile;
	}
	public String getContactUsContents() {
		return contactUsContents;
	}
	public void setContactUsContents(String contactUsContents) {
		this.contactUsContents = contactUsContents;
	}
	@Override
	public String toString() {
		return "Store [shopNo=" + shopNo + ", shopName=" + shopName + ", mallId=" + mallId + ", baseDomain="
				+ baseDomain + ", primaryDomain=" + primaryDomain + ", companyRegistrationNo=" + companyRegistrationNo
				+ ", companyName=" + companyName + ", presidentName=" + presidentName + ", companyCondition="
				+ companyCondition + ", companyLine=" + companyLine + ", country=" + country + ", zipcode=" + zipcode
				+ ", address1=" + address1 + ", address2=" + address2 + ", phone=" + phone + ", fax=" + fax + ", email="
				+ email + ", mallUrl=" + mallUrl + ", mailOrderSalesRegistration=" + mailOrderSalesRegistration
				+ ", mailOrderSalesRegistrationNumber=" + mailOrderSalesRegistrationNumber
				+ ", missingReportReasonType=" + missingReportReasonType + ", missingReportReason="
				+ missingReportReason + ", aboutUsContents=" + aboutUsContents + ", companyMapUrl=" + companyMapUrl
				+ ", customerServicePhone=" + customerServicePhone + ", customerServiceEmail=" + customerServiceEmail
				+ ", customerServiceFax=" + customerServiceFax + ", customerServiceSms=" + customerServiceSms
				+ ", customerServiceHours=" + customerServiceHours + ", privacyOfficerName=" + privacyOfficerName
				+ ", privacyOfficerPosition=" + privacyOfficerPosition + ", privacyOfficerDepartment="
				+ privacyOfficerDepartment + ", privacyOfficerPhone=" + privacyOfficerPhone + ", privacyOfficerEmail="
				+ privacyOfficerEmail + ", contactUsMobile=" + contactUsMobile + ", contactUsContents="
				+ contactUsContents + "]";
	}
	
}
