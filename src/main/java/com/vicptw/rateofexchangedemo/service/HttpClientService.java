/**
 * 
 */
package com.vicptw.rateofexchangedemo.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicptw.rateofexchangedemo.model.CurrencyParams;
import com.vicptw.rateofexchangedemo.model.Rate;

/**
 * @author vicpeng
 *
 */
@Service
public class HttpClientService {

	public List<Rate> getEsunBankData(CurrencyParams currencyParams) throws ClientProtocolException, IOException {
		Optional<String> optional = Optional.ofNullable(currencyParams.getCurrency());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String date = dateFormat.format(currentDate);
		if (!optional.isPresent()) {
			currencyParams = new CurrencyParams();
			currencyParams.setCurrency("USD");
			currencyParams.setStartDate(date);
			currencyParams.setEndDate(date);
		}

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson");

		String requestBody = "{\"data\": "
				+ "{\"Currency\": \"" + currencyParams.getCurrency() + "\","
				+ "\"Currencytype\": \"1\"," 
				+ "\"Rangetype\": \"0\"," 
				+ "\"Startdate\": \"" + currencyParams.getStartDate() + "\"," 
				+ "\"Enddate\": \"" + currencyParams.getEndDate() + "\"}"
				+ "}";

		StringEntity entity = new StringEntity(requestBody, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Referer",
				"https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/exchange-rate-chart");

		CloseableHttpResponse response = client.execute(httpPost);
		List<Rate> rates = responseHandler(response, currencyParams);
		
		client.close();
		return rates;
	}

	private List<Rate> responseHandler(CloseableHttpResponse response, CurrencyParams currencyParams) {

		List<Rate> rates = new ArrayList<Rate>();
		String rawData;
		try {
			rawData = EntityUtils.toString(response.getEntity());

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rawNode = objectMapper.readTree(rawData);
			JsonNode rootNode = rawNode.path("d");
			JsonNode ratesNode = objectMapper.readTree(rootNode.asText()).path("Rates");
			for (int i = 0; i < ratesNode.size(); i++) {
				Rate rate = new Rate();
				rate.setCurrency(currencyParams.getCurrency());
				rate.setTime(ratesNode.get(i).get("Time").asText());
				rate.setBuyRate(ratesNode.get(i).get("BuyRate").asText());
				rate.setSellRate(ratesNode.get(i).get("SellRate").asText());
				rates.add(rate);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return rates;
	}

}
