package com.bot.service;

import com.bot.model.ovvatv.Channel;
import com.bot.model.ovvatv.Language;
import com.bot.model.ovvatv.ProgramsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class OvvaService {

    @Autowired
    private RestTemplate restTemplate;

    public ProgramsData getProgramsData(Language language, Channel channel, LocalDate localDate) {
        String url = "https://api.ovva.tv/v2/" + language.getValue() + "/tvguide/"
                + channel.getId() + "/" + localDate.toString();
        ResponseEntity<ProgramsData> responseEntity = restTemplate.getForEntity(url, ProgramsData.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Can't get data! Request to API failed!");
        }
    }
}
