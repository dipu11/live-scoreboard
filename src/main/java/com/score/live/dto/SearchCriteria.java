package com.score.live.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String channelTitle;
    private String channelDescription;
    private Integer ttl;
    private String channelLink;


    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date pubDate;

    //new item properties
    private String itemTitle;
    private String itemLink;

    private String itemDescription;

    private String itemGuid;
}
