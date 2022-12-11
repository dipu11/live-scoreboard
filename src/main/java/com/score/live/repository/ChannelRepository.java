package com.score.live.repository;

import com.score.live.dto.ChannelDto;
import com.score.live.dto.NewsSummaryDto;
import com.score.live.dto.SearchCriteria;
import com.score.live.entity.Channel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("select new com.score.live.dto.ChannelDto(channel.title, channel.ttl, channel.link, channel.description, channel.copyRight, channel.language, channel.pubDate) \n " +
            " from Channel channel order by channel.id desc")
    List<ChannelDto> getChannelsByCriteria(Pageable pageable);

    List<Channel> getByPubDateAndTitleAndTtlAndLanguageAndCopyRight(Date pubDate, String title, Integer ttl, String language, String copyRight);


}
