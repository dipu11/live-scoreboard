package com.score.live.service;

import com.score.live.dto.ChannelDto;
import com.score.live.dto.NewsItemDto;

import com.score.live.dto.NewsSummaryDto;
import com.score.live.dto.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreXmlService {

    Boolean insertRecord();
    List<ChannelDto> getLatestScores(Pageable pageable);

    List<NewsItemDto> getNewsItemList(Pageable pageable);

    List<NewsSummaryDto> getNewsSummary(SearchCriteria searchCriteria);


}
