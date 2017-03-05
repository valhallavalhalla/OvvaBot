package com.bot.model.ovvatv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoCard {

    @JsonProperty(value = "card_id")
    private String cardId;

    private String title;

    private String subtitle;

    @JsonProperty(value = "image_collection")
    private Image image;

    @JsonProperty(value = "iframe_src")
    private String iframeSrc;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getIframeSrc() {
        return iframeSrc;
    }

    public void setIframeSrc(String iframeSrc) {
        this.iframeSrc = iframeSrc;
    }
}
