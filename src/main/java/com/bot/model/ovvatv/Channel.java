package com.bot.model.ovvatv;

public enum Channel {

    ONE_PLUS_ONE("1plus1", 1),
    TWO_PLUS_TWO("2plus2", 2),
    TET("tet", 3),
    TSN_SURDO("tsnsurdo", 11),
    SPORT_ONE("sport1", 12),
    ONE_PLUS_ONE_IN("1plus1in", 14);

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
