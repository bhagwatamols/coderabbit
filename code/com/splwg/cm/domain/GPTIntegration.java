package com.splwg.cm.domain;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GPTIntegration {

	public static void main(String[] args) {
        HttpClient httpClient = HttpClients.createDefault();
        String apiKey = "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";
        String apiUrl = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
        
        try {
            HttpPost request = new HttpPost(apiUrl);
            request.addHeader("Authorization", "Bearer " + apiKey);
            request.addHeader("Content-Type", "application/json");
            
            String prompt = "Translate the following English text to French: '" + "Translate this text." + "'";
            String requestBody = "{\"prompt\": \"" + prompt + "\"}";
            request.setEntity(new StringEntity(requestBody));
            
            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(responseString);
            
            String completion = jsonResponse.get("choices").get(0).get("text").asText();
            System.out.println("GPT-3.5 Response: " + completion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
