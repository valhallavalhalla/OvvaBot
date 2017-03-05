package com.bot.model.ovvatv;

public enum Channel {

    ONE_PLUS_ONE("1+1", 1),
    TWO_PLUS_TWO("2+2", 2),
    TET("TET", 3),
    TSN_SURDO("ТСН з сурдоперекладом", 11),
    SPORT_ONE("Спорт 1", 12),
    ONE_PLUS_ONE_IN("1+1 International", 14);

    private final String name;
    private final Integer id;

    Channel(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Channel getById(Integer id) {
        for (Channel channel : Channel.values()) {
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        return ONE_PLUS_ONE;
    }
}
