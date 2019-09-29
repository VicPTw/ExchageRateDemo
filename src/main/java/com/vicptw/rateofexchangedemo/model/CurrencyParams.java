/**
 * 
 */
package com.vicptw.rateofexchangedemo.model;


/**
 * @author vicpeng
 *
 */
public class CurrencyParams {
	
	private String currency;
	private String currencyType;
	private String rangeType;
	private String startDate;
	private String endDate;
	private String currencyTitle;
	
	public CurrencyParams() {
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getRangeType() {
		return rangeType;
	}
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCurrencyTitle() {
		return currencyTitle;
	}
	public void setCurrencyTitle(String currencyTitle) {
		this.currencyTitle = currencyTitle;
	}

	@Override
	public String toString() {
		return "Data [currency=" + currency + ", currencyType=" + currencyType + ", rangeType=" + rangeType
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", currencyTitle=" + currencyTitle + "]";
	}
	
	
	
}
