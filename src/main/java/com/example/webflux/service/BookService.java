package com.example.webflux.service;

import com.example.webflux.dao.BookDto;
import com.example.webflux.repository.BookRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    @Value("${naver_client_id}")
    private String clientId;

    @Value("${naver_client_secret}")
    private String clientSecret;

    private final BookRespository bookRepository;
    private final BookDto bookDto;

    public void postBooks(String text) throws JsonProcessingException, ParseException {
        System.out.println(clientId);
        String response = getBooks(text);

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response);
        JSONArray items = (JSONArray) obj.get("items");

        for(Object item: items){
            int price = 0;
            if(((JSONObject) item).get("discount") != null) price = Integer.parseInt(((JSONObject) item).get("discount").toString());
            Date date = new Date(00000000);
            if(((JSONObject) item).get("date") != null) date = Date.valueOf(((JSONObject) item).get("date").toString());
            bookRepository.save(
                    bookDto.insertBook(
                            ((JSONObject) item).get("title").toString(),
                            ((JSONObject) item).get("author").toString(),
                            price,
                            ((JSONObject) item).get("publisher").toString(),
                            ((JSONObject) item).get("link").toString(),
                            ((JSONObject) item).get("image").toString(),
                            date
                    )
            );
        }
    }

    public String getBooks(String text){
        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e){
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + text; //JSON

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiUrl,requestHeaders);
        //System.out.println(responseBody);
        return responseBody;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header: requestHeaders.entrySet()){
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
