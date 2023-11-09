package com.example.demo.matching.service;

import com.example.demo.matching.dto.KeywordDto;
import com.example.demo.matching.dto.RoadAddressDto;
import java.util.List;

public interface AddressService {

    public List<RoadAddressDto> getAddress(KeywordDto keywordDto);

}