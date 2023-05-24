package com.mvpjava.random;

import static com.mvpjava.random.factories.HttpPostFactory.*;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.mvpjava.random.factories.HttpPostFactory;
import com.mvpjava.random.json.response.RandomResponse;

public class AtmosphericRandom {

	private final CloseableHttpClient httpClient;
	private final HttpPostFactory httpPostFactory;
	private final RandomMapper mapper;

	public AtmosphericRandom(CloseableHttpClient httpClient, HttpPostFactory httpPostFactory, RandomMapper mapper) {
		this.httpClient = httpClient;
		this.httpPostFactory = httpPostFactory;
		this.mapper = mapper;
	}

	public int nextInt() {
		int[] randomNumbers = nextInt (DEFAULT_NUM_OF_RANDOMS, DEFAULT_MIN_RANDOM_RANGE, DEFAULT_MAX_RANDOM_RANGE, DEFAULT_UNIQUE_VALUES, DEFAULT_BASE);
		if (randomNumbers.length == 0) {
			throw new RuntimeException("Unable to generate a random int from random.org");
		}
		return randomNumbers[0];
	}
	
	public int[] nextInt(int numberOfIntegers, int min, int max) {
		int[] randomNumbers = nextInt(numberOfIntegers, min, max, DEFAULT_UNIQUE_VALUES, DEFAULT_BASE);		
		return randomNumbers;
	}
	
	public int[] nextInt(int numberOfIntegers, int min, int max, boolean replacement) {
		int[] randomNumbers = nextInt(numberOfIntegers, min, max, replacement, DEFAULT_BASE);		
		return randomNumbers;
	}

	//Only base 10 supported for demo
	private  int[] nextInt(int numberOfIntegers, int min, int max, boolean replacement, int base) {
		HttpPost httpPostRequest = httpPostFactory.newHttpPostRequest(numberOfIntegers, min, max, replacement, base);		
		RandomResponse randomResponse = executeHttpRequest(httpPostRequest);
		int[] randomInts = randomResponse.getRandomData();
		return randomInts;
	}
	
	private RandomResponse executeHttpRequest(HttpPost httpPost) {
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mapper.mapHttpResponseToRandomResponse(response);
	}
}