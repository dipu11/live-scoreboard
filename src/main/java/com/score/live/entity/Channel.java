package com.score.live.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private Integer ttl;

    private String link;

    private String description;

    private String copyRight;

    private String language;

    @Column(name = "publish_date")
    private Date pubDate;

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY ,cascade=CascadeType.ALL,  orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    List<NewsItem> itemList;

    public Channel(String title, Integer total, String link, String description, String copyRight, String language, Date pubDate) {
        this.title = title;
        this.ttl = total;
        this.link = link;
        this.description = description;
        this.copyRight = copyRight;
        this.language = language;
        this.pubDate = pubDate;
    }
}
