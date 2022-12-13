package com.score.live.controller;

import com.score.live.dto.NewsItemDto;
import com.score.live.dto.NewsSummaryDto;
import com.score.live.dto.SearchCriteria;
import com.score.live.service.ScoreXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    private static Logger log = LoggerFactory.getLogger(ScoreController.class);

    private final ScoreXmlService scoreXmlService;
    @Autowired
    public ScoreController(
            ScoreXmlService scoreXmlService
    ){
        this.scoreXmlService= scoreXmlService;
    }

    @GetMapping("/home")
    public ModelAndView showLatestScore(){

        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("test", "my test string");
        modelAndView.addObject("searchCriteria", new SearchCriteria());
        modelAndView.setViewName("view-score");

        List<NewsSummaryDto>scoreList= scoreXmlService.getNewsSummary(new SearchCriteria());
        modelAndView.addObject("scoreList", scoreList);

        return modelAndView;
    }

    @PostMapping
    public ResponseEntity insertNews(){
        Boolean inserted= scoreXmlService.insertRecord();

        return new ResponseEntity(inserted,  inserted? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @GetMapping("/list")
    public ResponseEntity getLatestScores( @PageableDefault(value = 10) Pageable pageable){

        List<NewsItemDto> scores= scoreXmlService.getNewsItemList(pageable);

        if(scores==null || scores.size()==0){
            return new ResponseEntity(scores, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(scores, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchScore( SearchCriteria searchCriteria, Pageable pageable){

        log.info("search criteria form submitted!");
        List<NewsSummaryDto> scores= scoreXmlService.getNewsSummary(searchCriteria);

        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity testString(){
        log.info("Security testing!");
        return new ResponseEntity<>("Security testing: processing", HttpStatus.OK);
    }
}
