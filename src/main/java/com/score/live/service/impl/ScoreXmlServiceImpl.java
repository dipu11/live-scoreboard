package com.score.live.service.impl;

import com.score.live.dto.ChannelDto;
import com.score.live.dto.NewsItemDto;
import com.score.live.dto.NewsSummaryDto;
import com.score.live.dto.SearchCriteria;
import com.score.live.entity.Channel;
import com.score.live.entity.NewsItem;
import com.score.live.repository.ChannelRepository;
import com.score.live.repository.NewsItemRepository;
import com.score.live.service.ScoreXmlService;
import com.score.live.util.Defs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

@Service
public class ScoreXmlServiceImpl implements ScoreXmlService {
    private static Logger log = LoggerFactory.getLogger(ScoreXmlServiceImpl.class);

    @Value("${app.live.score.api}")
    private String APP_LIVE_SCORE_API;

    private final ChannelRepository channelRepository;

    private final NewsItemRepository newsItemRepository;

    @Autowired
    public ScoreXmlServiceImpl(
            ChannelRepository channelRepository,
            NewsItemRepository newsItemRepository
    ){
        this.channelRepository= channelRepository;
        this.newsItemRepository= newsItemRepository;
    }


    //@Scheduled(initialDelayString = "1000", fixedDelayString = "300000")
    public void getLiveScore(){
        log.info("..........scheduler running............");

        this.insertRecord();
    }


    @Override
    public Boolean insertRecord() {

        Channel channel=null;
        try{


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(APP_LIVE_SCORE_API);

            doc.getDocumentElement().normalize();
            Date d=new Date((doc.getElementsByTagName(Defs.COURSE_PUB_DATE).item(0).getTextContent()));
            System.out.println(d);
            //parse channel values
            channel= new Channel(
                    doc.getElementsByTagName(Defs.COURSE_TITLE).item(0).getTextContent(),
                    Integer.parseInt(doc.getElementsByTagName(Defs.COURSE_TTL).item(0).getTextContent()),
                    doc.getElementsByTagName(Defs.ITEM_LINK).item(0).getTextContent(),
                    doc.getElementsByTagName(Defs.COURSE_DESCRIPTION).item(0).getTextContent(),
                    doc.getElementsByTagName(Defs.COURSE_COPYRIGHT).item(0).getTextContent(),
                    doc.getElementsByTagName(Defs.COURSE_LANGUAGE).item(0).getTextContent(),
                    new Date(doc.getElementsByTagName(Defs.COURSE_PUB_DATE).item(0).getTextContent())

            );

            //check for old channel
            List<Channel> channelList= channelRepository.getByPubDateAndTitleAndTtlAndLanguageAndCopyRight(
                    channel.getPubDate(), channel.getTitle(), channel.getTtl(), channel.getLanguage(), channel.getCopyRight()
            );

            if(channelList!=null && channelList.size() > 0){
                channel= channelList.get(0);
            }

            //parse the inner list: newsItemList
            NodeList newsItemNodeList= doc.getElementsByTagName("item");
            List<NewsItem> itemList= new ArrayList<>();

            for(int i=0; i<newsItemNodeList.getLength(); i++){
                Node itemNode= newsItemNodeList.item(i);
                if(itemNode.getNodeType() == Node.ELEMENT_NODE){
                    Element itemElement= (Element) itemNode;
                    NewsItem newsItem= new NewsItem(
                            itemElement.getElementsByTagName(Defs.ITEM_TITLE).item(0).getTextContent(),
                            itemElement.getElementsByTagName(Defs.ITEM_LINK).item(0).getTextContent(),
                            itemElement.getElementsByTagName(Defs.ITEM_DESCRIPTION).item(0).getTextContent(),
                            itemElement.getElementsByTagName(Defs.ITEM_GUID).item(0).getTextContent()
                    );

                    newsItem.setChannel(channel);
                    itemList.add(newsItem);
                }
            }

            List<NewsItem>uniqueItemList=getUniqueueItemList(channel.getPubDate(), itemList);
            if(uniqueItemList!=null && uniqueItemList.size()>0) {
                channel.setItemList(uniqueItemList);

                log.info("fetchedList size:"+itemList.size()+", unique List size:"+ uniqueItemList.size());

                channelRepository.save(channel);
            }
            else{
                log.info("No unique item found to save for this iteration!");
            }
        }
        catch (Exception e){
            log.error("Error occured:"+e.getMessage());
        }
        return channel!=null;
    }

    @Override
    public List<ChannelDto> getLatestScores(Pageable pageable) {
        return channelRepository.getChannelsByCriteria(pageable);
    }

    @Override
    public List<NewsItemDto> getNewsItemList(Pageable pageable) {
        return newsItemRepository.getByNewsItemList(pageable);
    }

    @Override
    public List<NewsSummaryDto> getNewsSummary(SearchCriteria searchCriteria) {
        return newsItemRepository.getNewsSummaryBySearchCriteria(
                searchCriteria.getChannelTitle(),
                searchCriteria.getChannelDescription(),
                searchCriteria.getTtl(),
                searchCriteria.getChannelLink(),
                searchCriteria.getPubDate(),
                searchCriteria.getItemTitle(),
                searchCriteria.getItemLink(),
                searchCriteria.getItemDescription(),
                searchCriteria.getItemGuid()
        );
    }


    /**
     * Remove duplicate/already existed newsitem
     * Criteria: publish date and news title are same--> marked as duplicate
     * @param pubDate
     * @param itemList
     * @return
     */
    private List<NewsItem> getUniqueueItemList(Date pubDate, List<NewsItem> itemList){

        List<String> titleList= new ArrayList<>();
        Map<String, NewsItem> titleVsIndexMap= new HashMap<>();

        for(int i=0; i<itemList.size(); i++){
            NewsItem item= itemList.get(i);
            titleList.add(item.getTitle());
            titleVsIndexMap.put(item.getTitle(), item);
        }

        List<NewsItem> oldItemList= newsItemRepository.getNewsItemsByPubDateAndNewsTitle(pubDate, titleList);
        for(NewsItem item: oldItemList){
            if(titleVsIndexMap.containsKey(item.getTitle())){
                NewsItem duplicateItem= titleVsIndexMap.get(item.getTitle());
                itemList.remove(duplicateItem);
            }
        }
        return itemList;
    }
}
