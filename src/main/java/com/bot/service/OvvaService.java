package com.bot.service;

import com.bot.model.ovvatv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class OvvaService {

    private static final String OVVA_TV_URL = "https://api.ovva.tv/v2/" ;

    private RestTemplate restTemplate = new RestTemplate();

    public ProgramsData getProgramsData(Language language, Channel channel, LocalDate localDate) {
        String url = OVVA_TV_URL + language.getValue() + "/tvguide/"
                + channel.getId() + "/" + localDate.toString();
        ResponseEntity<ProgramsData> responseEntity = restTemplate.getForEntity(url, ProgramsData.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Can't get data! Request to API failed!");
        }
    }

    public VideoCard getVideoCardByQuery(Language language, String query) {
        String projectId = getProjectIdByQuery(language, query);
        String cardId = getCardIdLastAddedVideoByProjectId(language, projectId);

        return getVideoCardByCardId(language, cardId);
    }

    public VideoCard getVideoCardByCardId(Language language, String videocardId) {
        String url = OVVA_TV_URL + language.getValue() + "/video/" + videocardId;
        ResponseEntity<VideoCardResponse> responseEntity = restTemplate.getForEntity(url, VideoCardResponse.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody().getData();
        } else {
            throw new RuntimeException("Can't get video card! Request to API failed!");
        }
    }


    public String getCardIdLastAddedVideoByProjectId(Language language, String projectId) {
        String url = OVVA_TV_URL + language.getValue() + "/lastvideos/" + projectId;
        ResponseEntity<LastVideosResponse> responseEntity = restTemplate.getForEntity(url, LastVideosResponse.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody().getData().get(0).getCardId();
        } else {
            throw new RuntimeException("Can't get card id! Request to API failed!");
        }
    }

    public String getProjectIdByQuery(Language language, String query) {
        String url = OVVA_TV_URL + language.getValue() + "/search/project/?query=" + query;
        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(url, SearchResponse.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity.getBody().getData().getResults().get(0).getId();
        } else {
            throw new RuntimeException("Can't get project id! Request to API failed!");
        }
    }
}
