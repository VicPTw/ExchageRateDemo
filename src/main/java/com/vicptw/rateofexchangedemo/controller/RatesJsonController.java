/**
 * 
 */
package com.vicptw.rateofexchangedemo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vicptw.rateofexchangedemo.model.CurrencyParams;
import com.vicptw.rateofexchangedemo.model.Rate;
import com.vicptw.rateofexchangedemo.service.HttpClientService;


/**
 * @author vicpeng
 *
 */

@RestController
public class RatesJsonController {

	@Autowired
	HttpClientService httpClientService;
	
	@GetMapping("/exchangeRates/{from}/{to}")
	public List<Rate> getRatesJson(@PathVariable String from, @PathVariable String to, @ModelAttribute("currencyParams") CurrencyParams currencyParams) {
		
		currencyParams.setCurrency(from);
		List<Rate> list;
		try {
			list = httpClientService.getEsunBankData(currencyParams);
		} catch (IOException e) {
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}

}
