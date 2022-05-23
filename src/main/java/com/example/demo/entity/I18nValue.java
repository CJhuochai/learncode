package com.example.demo.entity;

/**
 * i18n暂时只支持中英文
 * @author xsq
 *
 */
public class I18nValue implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String en;
	private String zh;
	public I18nValue() {
		super();
	}
	public I18nValue(String en, String zh) {
		super();
		this.en = en;
		this.zh = zh;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public I18nValue join(I18nValue i18nValue,String delimiter) {
		return new I18nValue(this.en+delimiter+i18nValue.getEn(), this.zh+delimiter+i18nValue.getZh());
	}
	}
