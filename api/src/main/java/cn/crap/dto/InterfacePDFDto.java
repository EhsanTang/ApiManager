package cn.crap.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class InterfacePDFDto implements Serializable{
	/**
	 * 该类仅仅用于生成pdf、word
	 */
	private static final long serialVersionUID = 1L;
	private InterfaceDto model;
	private List<ParamDto> formParams;
	private String customParams;
	private List<ParamDto> headers;
	private List<ParamDto> responseParam;
	private List<ErrorDto> errors;
	private String trueMockUrl;
	private String falseMockUrl;
	private List<ParamDto> paramRemarks;
	// 是否是自定义参数
	private boolean custom = false;


	public InterfaceDto getModel() {
		return model;
	}

	public void setModel(InterfaceDto model) {
		this.model = model;
	}

	public List<ParamDto> getFormParams() {
		return formParams;
	}

	public void setFormParams(List<ParamDto> formParams) {
		this.formParams = formParams;
	}

	public String getCustomParams() {
		return customParams;
	}

	public void setCustomParams(String customParams) {
		this.customParams = customParams;
	}

	public List<ParamDto> getHeaders() {
		return headers;
	}

	public void setHeaders(List<ParamDto> headers) {
		this.headers = headers;
	}

	public List<ParamDto> getResponseParam() {
		return responseParam;
	}

	/**
	 * 名称根据deep缩进
	 * @param responseParam
	 */
	public void setResponseParam(List<ParamDto> responseParam) {
		if (CollectionUtils.isEmpty(responseParam)){
			this.responseParam = new ArrayList<>();
			return;
		}
		for (ParamDto responseParamDto : responseParam){
			Integer deep = responseParamDto.getDeep();
			if (deep == null){
                responseParamDto.setDeep(0);
				deep = 0;
			}
			// TODO 空格无效
			StringBuilder sb = new StringBuilder("");
			while (deep > 0){
                sb.append(" ");
                deep = deep - 1;
            }
			responseParamDto.setName(sb.toString() +
					(responseParamDto.getName() == null ? "" : responseParamDto.getName()));
		}
		this.responseParam = responseParam;
	}

	public List<ErrorDto> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDto> errors) {
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

	public List<ParamDto> getParamRemarks() {
		return paramRemarks;
	}

	public void setParamRemarks(List<ParamDto> paramRemarks) {
		this.paramRemarks = paramRemarks;
	}

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
