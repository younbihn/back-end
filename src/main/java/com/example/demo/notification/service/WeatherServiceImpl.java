package com.example.demo.notification.service;

import com.example.demo.exception.impl.FailedGetAddressException;
import com.example.demo.exception.impl.JsonParsingException;
import com.example.demo.notification.dto.WeatherDto;
import com.example.demo.notification.dto.WeatherRequestDto;
import com.example.demo.notification.dto.LocationAndDateFromMatching;
import com.example.demo.type.PrecipitationType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather-api.key}")
    private String apiKey;

    @Override
    public WeatherDto getWeather(LocationAndDateFromMatching locationAndDateFromMatching) {
        WeatherRequestDto weatherRequestDto
                = WeatherRequestDto.fromLocationAndDate(locationAndDateFromMatching);
        String weatherData = getWeatherString(weatherRequestDto);
        WeatherDto weatherDto = parseWeather(weatherData);

        return weatherDto;
    }

    private String getWeatherString(WeatherRequestDto weatherRequestDto) {
        String apiUrl = makeWeatherApiUrl(weatherRequestDto);
        try {
            BufferedReader bufferedReader = getBufferedReader(apiUrl);
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            return response.toString();
        } catch (Exception e) {
            throw new FailedGetAddressException();
        }
    }

    private BufferedReader getBufferedReader(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader bufferedReader;

        if (responseCode == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        return bufferedReader;
    }

    private String makeWeatherApiUrl(WeatherRequestDto weatherRequestDto) {
        return "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                + "?serviceKey=" + apiKey
                + "&numOfRows=" + weatherRequestDto.getNumOfRows()
                + "&pageNo=" + weatherRequestDto.getPageNo()
                + "&dataType=" + weatherRequestDto.getDataType()
                + "&base_date=" + weatherRequestDto.getBaseDate()
                + "&base_time=" + weatherRequestDto.getBaseTime()
                + "&nx=" + weatherRequestDto.getNx()
                + "&ny=" + weatherRequestDto.getNy();
    }

    private WeatherDto parseWeather(String weatherData) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(weatherData);
        } catch (ParseException e) {
            throw new JsonParsingException();
        }

        JSONObject resultsForWeather = (JSONObject) jsonObject.get("response");
        JSONObject resultOfBody = (JSONObject) resultsForWeather.get("body");
        JSONObject resultOfItems = (JSONObject) resultOfBody.get("items");
        JSONArray resultOfItem = (JSONArray) resultOfItems.get("item");

        JSONObject ptyObject = (JSONObject) resultOfItem.get(6);
        JSONObject popObject = (JSONObject) resultOfItem.get(7);
        String precipitationCode = ptyObject.get("fcstValue").toString();
        if (Integer.valueOf(precipitationCode) <= 0) {
            return null;
        }
        PrecipitationType precipitationType
                = PrecipitationType.findPrecipitationType(precipitationCode);

        String precipitationProbability = popObject.get("fcstValue").toString() + "%";
        return WeatherDto.builder().precipitationType(precipitationType)
                .precipitationProbability(precipitationProbability)
                .build();
    }

}
