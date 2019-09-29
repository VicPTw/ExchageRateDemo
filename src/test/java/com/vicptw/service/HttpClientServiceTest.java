/**
 * 
 */
package com.vicptw.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicptw.rateofexchangedemo.model.CurrencyParams;
import com.vicptw.rateofexchangedemo.model.Rate;

/**
 * @author vicpeng
 *
 */

public class HttpClientServiceTest {

	@Test
	public void whenSendPostRequestUsingHttpClient_thenCorrect() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://www.example.com/");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", "John"));
		params.add(new BasicNameValuePair("password", "pass"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();
	}

	@Test
	public void whenSendPostRequestWithAuthorizationUsingHttpClient_thenCorrect()
			throws ClientProtocolException, IOException, AuthenticationException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://www.example.com/");

		httpPost.setEntity(new StringEntity("test post"));
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials("John", "pass");
		httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

		CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();

	}

	@Test
	public void whenPostJsonRequestUsingHttpClient_thenCorrect() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://www.example.com/");

		String json = "{\"id\":1,\"name\":\"John\"}";
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();

	}

	@Test
	public void whenPostFormUsingHttpClientFluentAPI_thenCorrect() throws ClientProtocolException, IOException {
		HttpResponse response = Request.Post("http://www.example.com/")
				.bodyForm(Form.form().add("username", "John").add("password", "pass").build()).execute()
				.returnResponse();
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

	}

	@Test
	public void serviceTest() throws ClientProtocolException, IOException {
		String json = "{" + "\"data\": {" + "\"Currency\": \"AUD\"," + "\"Currnecytype\": \"1\","
				+ "\"Rangetype\": \"0\"," + "\"Startdate\": \"2019-09-26\"," + "\"Enddate\": \"2019-09-26\","
				+ "\"CurrencyTitle\": \"澳幣(AUD)\"" + "}" + "}";
		System.out.println(json);
//		StringEntity entity = new StringEntity(json);
		HttpResponse response = Request
				.Post("https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson").

				addHeader("Accept", "application/json").addHeader("Content-type", "application/json")
				.addHeader("Referer",
						"https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/exchange-rate-chart?Currency=USD/TWD")
				.execute().returnResponse();
		String mark = EntityUtils.toString((response.getEntity()), "UTF-8");
		System.out.println("\r\n\r\n" + mark);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

	@Test
	public void serviceTest2() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson");

		String json = "{" + "\"data\": {" + "\"Currency\": \"USD\"," + "\"Currencytype\": \"1\","
				+ "\"Rangetype\": \"0\"," + "\"Startdate\": \"2019-09-21\"," + "\"Enddate\": \"2019-09-26\","
				+ "\"CurrencyTitle\": \"美元(USD)\"" + "}" + "}";
		StringEntity entity = new StringEntity(json, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Referer",
				"https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/exchange-rate-chart?Currency=USD/TWD");

		CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();
	}

	@Test
	public void newTest() throws ParseException, IOException {
		CurrencyParams data = new CurrencyParams();
		data.setCurrency("USD");
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson");

		String json = "{" + "\"data\": {" + "\"Currency\": \"USD\"," + "\"Currencytype\": \"1\","
				+ "\"Rangetype\": \"0\"," + "\"Startdate\": \"2019-08-21\"," + "\"Enddate\": \"2019-09-26\","
				+ "\"CurrencyTitle\": \"美元(USD)\"" + "}" + "}";
		StringEntity entity = new StringEntity(json, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Referer",
				"https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/exchange-rate-chart?Currency=USD/TWD");

		CloseableHttpResponse response = client.execute(httpPost);
		json = EntityUtils.toString(response.getEntity());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rawNode = objectMapper.readTree(json);
		JsonNode rootNode = rawNode.path("d");
		JsonNode ratesNode = objectMapper.readTree(rootNode.asText()).path("Rates");
		for(int i = 0; i < ratesNode.size(); i++) {
		System.out.println("Rates = " + ratesNode.get(i));
			Rate rate = new Rate();
			rate.setCurrency(data.getCurrency());
			System.out.println(data.getCurrency());
			rate.setTime(ratesNode.get(i).get("Time").asText());
			System.out.println(rate.getTime());
			rate.setBuyRate(ratesNode.get(i).get("BuyRate").asText());
			System.out.println(rate.getBuyRate());
			rate.setSellRate(ratesNode.get(i).get("SellRate").asText());
			System.out.println(rate.getSellRate());
			System.out.println(rate.toString());
		}
		
		client.close();
		
	}

}
