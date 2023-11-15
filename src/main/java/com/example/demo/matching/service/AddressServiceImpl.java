package com.example.demo.matching.service;

import com.example.demo.exception.impl.FailedGetAddressException;
import com.example.demo.exception.impl.JsonParsingException;
import com.example.demo.exception.impl.AddressNotFoundException;
import com.example.demo.matching.dto.AddressRequestDto;
import com.example.demo.matching.dto.RoadAddressDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    @Value("${address-api.key}")
    private String apiKey;

    @Override
    public List<RoadAddressDto> getAddress(String keyword) {
        AddressRequestDto addressRequestDto = AddressRequestDto.fromKeyword(keyword);
        String addressData = getAddressString(addressRequestDto);
        List<String> parseAddress = parseAddress(addressData);

        return parseAddress.stream()
                .map(address
                        -> new RoadAddressDto(address))
                .collect(Collectors.toList());

    }

    private String getAddressString(AddressRequestDto addressRequestDto) {
        String apiUrl = makeAddressApiUrl(addressRequestDto);
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

    private static BufferedReader getBufferedReader(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader bufferedReader;

        if (responseCode == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        return bufferedReader;
    }

    private String makeAddressApiUrl(AddressRequestDto addressRequestDto) {
        return "https://business.juso.go.kr/addrlink/addrLinkApi.do?confmKey=" + apiKey
                + "&currentPage=" + addressRequestDto.getCurrentPage()
                + "&countPerPage=" + addressRequestDto.getCountPerPage()
                + "&keyword=" + addressRequestDto.getKeyword()
                + "&resultType=" + addressRequestDto.getResultType()
                + "&hstryYn=" + addressRequestDto.getHstryYn()
                + "&firstSort=" + addressRequestDto.getFirstSort();
    }

    private List<String> parseAddress(String addressData) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(addressData);
        } catch (ParseException e) {
            throw new JsonParsingException();
        }
        List<String> parseResults = new ArrayList<>();

        JSONObject resultsForJuso = (JSONObject) jsonObject.get("results");
        JSONArray jusoList = (JSONArray) resultsForJuso.get("juso");

        try {
            for (int i = 0; i < jusoList.size(); i++) {
                JSONObject jusoData = (JSONObject) jusoList.get(i);
                parseResults.add(jusoData.get("roadAddr").toString());
            }
        } catch (Exception e) {
            throw new AddressNotFoundException();
        }
        return parseResults;
    }
}