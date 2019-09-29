/**
 * 
 */
package com.vicptw.rateofexchangedemo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vicptw.rateofexchangedemo.model.CurrencyParams;
import com.vicptw.rateofexchangedemo.model.Rate;
import com.vicptw.rateofexchangedemo.service.HttpClientService;

/**
 * @author vicpeng
 *
 */
@Controller
public class RateOfExchangeChartController {

	@Autowired
	HttpClientService httpClientService;

	@RequestMapping("/")
	public String getAllRates(Model model) {
		CurrencyParams currencyParams = new CurrencyParams();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String date = dateFormat.format(currentDate);
		currencyParams.setCurrency("default_select_value");
		currencyParams.setStartDate(date);
		currencyParams.setEndDate(date);
		
		model.addAttribute("rates", null);
		model.addAttribute("currencyParams", currencyParams);

		return "index";
	}
	
    @GetMapping("/rates")
    public String getAllRates(@ModelAttribute("currencyParams") CurrencyParams currencyParams, Model model) {

    	List<Rate> list;
		try {
			list = httpClientService.getEsunBankData(currencyParams);
		} catch (IOException e) {
			list = null;
			e.printStackTrace();
		}
		model.addAttribute("rates", list);
		
		return "index";
    }    
}
