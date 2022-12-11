package com.score.live.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsSummaryDto implements Serializable {
    private String channelTitle;
    private Integer channelTtl;

    private String channelLink;
    private String channelDescription;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date pubDate;

    private String itemTitle;
    private String itemLink;

    private String itemDescription;

    private String itemGuid;

}
