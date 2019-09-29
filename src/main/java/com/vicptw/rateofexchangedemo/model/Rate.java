package com.vicptw.rateofexchangedemo.model;


import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author vicpeng
 *
 */
public class Rate {
	
	private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private String time;
	private String buyRate;
	private String sellRate;
	
	public Rate() {
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}
	public String getSellRate() {
		return sellRate;
	}
	public void setSellRate(String sellRate) {
		this.sellRate = sellRate;
	}

	@Override
	public String toString() {
		return "Rate [currency=" + currency + ", time=" + time + ", buyRate=" + buyRate + ", sellRate=" + sellRate
				+ "]";
	}
	
	
	
}
