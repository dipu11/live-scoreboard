# Live Score Dashboard

=====================================================
-  This is a dummy project. Basic auth implemented on `basic-auth-impl` branch
# Target
-   Fetch record from this link: `http://static.cricinfo.com/rss/livescores.xml` and insert into database, 
-   check for duplicate records
-   insert via a schedular which runs in five mins interval

# Setup:
- Java-1.8
- Maven- 3.8.6
- port: `8080`
- contextPath: `live-score-service`


# APIs:

- Base API path: `/api/score`
- Dashboard/homepage: `/api/score/home` [After app startup, please visit this link]
- Insert news API: `/api/score` [Only for Integration testing, insertion id done by a scheduler]
- Search API: `/api/score/search` [search by different criteria]
- Paginated List API: `/api/score/list` [This api is not integrated with the client]



# Test:

- Integration testing with h2 database 


# Note:

- Duplicacy check is done based on channel's news title(channel new's title and item news' title) and pubDate
- Full Dashboard Url: `http://localhost:8080/live-score-service/api/score/home`
- Spring security not implemented
- Schedular's initial delay: 1s, fixedDelay: 5 mins
- For other config: please check, `application.properties` file

