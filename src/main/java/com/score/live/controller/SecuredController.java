package com.score.live.controller;

import com.score.live.dto.NewsItemDto;
import com.score.live.service.ScoreXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secured/score")
public class SecuredController {
    private static Logger log = LoggerFactory.getLogger(SecuredController.class);

    private final ScoreXmlService scoreXmlService;
    @Autowired
    public SecuredController(
            ScoreXmlService scoreXmlService
    ){
        this.scoreXmlService= scoreXmlService;
    }

    @GetMapping("/list")
    public ResponseEntity getLatestScores(@PageableDefault(value = 10) Pageable pageable){

        List<NewsItemDto> scores= scoreXmlService.getNewsItemList(pageable);

        if(scores==null || scores.size()==0){
            return new ResponseEntity(scores, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(scores, HttpStatus.OK);
    }


}
