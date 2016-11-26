package cn.crap.dto;

import java.io.Serializable;

import cn.crap.model.Interface;

public class InterfacePDFDto implements Serializable{
	/**
	 * 生成pdf
	 */
	private static final long serialVersionUID = 1L;
	private Interface model;
	private Object formParams;
	private String customParams;
	private Object headers;
	private Object responseParam;
	private Object errors;
	private String trueMockUrl;
	private String falseMockUrl;
	private Object paramRemarks;

	public Interface getModel() {
		return model;
	}
	public void setModel(Interface model) {
		this.model = model;
	}
	public Object getFormParams() {
		return formParams;
	}
	public void setFormParams(Object formParams) {
		this.formParams = formParams;
	}
	public String getCustomParams() {
		return customParams;
	}
	public void setCustomParams(String customParams) {
		this.customParams = customParams;
	}
	public Object getHeaders() {
		return headers;
	}
	public void setHeaders(Object headers) {
		this.headers = headers;
	}
	public Object getResponseParam() {
		return responseParam;
	}
	public void setResponseParam(Object responseParam) {
		this.responseParam = responseParam;
	}
	public Object getErrors() {
		return errors;
	}
	public void setErrors(Object errors) {
		this.errors = errors;
	}
	public String getTrueMockUrl() {
		return trueMockUrl;
	}
	public void setTrueMockUrl(String trueMockUrl) {
		this.trueMockUrl = trueMockUrl;
	}
	public String getFalseMockUrl() {
		return falseMockUrl;
	}
	public void setFalseMockUrl(String falseMockUrl) {
		this.falseMockUrl = falseMockUrl;
	}
	public Object getParamRemarks() {
		return paramRemarks;
	}
	public void setParamRemarks(Object paramRemarks) {
		this.paramRemarks = paramRemarks;
	}
	
	
}
