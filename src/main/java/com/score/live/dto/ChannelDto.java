package com.score.live.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto implements Serializable {
    private String title;

    private Integer ttl;

    private String link;

    private String description;

    private String copyRight;

    private String language;
    private Date pubDate;

    private List<NewsItemDto> itemList;

    public ChannelDto(String title, Integer ttl, String link, String description, String copyRight, String language, Date pubDate) {
        this.title = title;
        this.ttl = ttl;
        this.link = link;
        this.description = description;
        this.copyRight = copyRight;
        this.language = language;
        this.pubDate = pubDate;
    }
}
