package com.score.live.repository;

import com.score.live.dto.NewsItemDto;
import com.score.live.dto.NewsSummaryDto;
import com.score.live.entity.NewsItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    @Query("select new com.score.live.dto.NewsItemDto(item.title, item.link, item.description, item.guid) \n " +
            "from NewsItem item \n" +
            "order by item.id desc")
    List<NewsItemDto> getByNewsItemList(Pageable pageable);

    @Query("select new com.score.live.dto.NewsSummaryDto" +
            "   (" +
            "       channel.title, channel.ttl, channel.link, channel.description, channel.pubDate, item.title, item.link, item.description, item.guid" +
            "   ) " +
            "from NewsItem  item \n " +
            "join item.channel channel \n " +
            "where \n " +
            "(1=1) and \n " +
            "(:channelTitle is null or channel.title = :channelTitle) and \n " +
            "(:channelDescription is null or channel.description like %:channelDescription%) and \n " +
            "(:ttl is null  or channel.ttl = :ttl) and \n " +
            "(:channelLink is null or channel.link = :channelLink) and \n" +
           // "(:pubDate is null or (channel.pubDate >= :pubDate and channel.pubDate <= :pubDate)) and \n " +
            "(:pubDate is null or channel.pubDate = :pubDate ) and \n " +
            "(:itemTitle is null or item.title = :itemTitle) and \n" +
            "(:itemLink is null or item.link = :itemLink) and \n " +
            "(:itemDescription is null or item.description like %:itemDescription%) and \n " +
            "(:itemGuid is null or item.guid = :itemGuid) order by item.id desc"
     )
    List<NewsSummaryDto> getNewsSummaryBySearchCriteria(
            @Param("channelTitle") String channelTitle,
            @Param("channelDescription") String channelDescription,
            @Param("ttl") Integer ttl,
            @Param("channelLink") String channelLink,
            @Param("pubDate") Date pubDate,
            @Param("itemTitle") String itemTitle,
            @Param("itemLink") String itemLink,
            @Param("itemDescription") String itemDescription,
            @Param("itemGuid") String itemGuid
    );


    @Query("select item from NewsItem item \n " +
            "join item.channel channel \n " +
            "where \n " +
            "channel.pubDate = :pubDate and \n " +
            "item.title in (:titleList)")
    List<NewsItem>  getNewsItemsByPubDateAndNewsTitle(@Param("pubDate")Date pubDate, @Param("titleList") List<String>titleList);
}
