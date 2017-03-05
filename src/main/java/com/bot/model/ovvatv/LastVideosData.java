package com.bot.model.ovvatv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastVideosData {

    @JsonProperty(value = "card_id")
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
