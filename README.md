# Android-REST-Server
Simple REST-server on Spring Boot, H2DB and Embedded ElasticSearch.

Main feature, that might be interested for someone, is ElasticSearch configuration and usage.

You may use ElasticSearch as you NoSQL data storage, but i decided to use H2 as main DB and ElasticSearch only as search engine.
The reason i did this is that i want to show you my way to make those different storages in sync with each other.

I used autoconfigurated beans that came with **spring-boot-starter-data-elasticsearch**. For my purposes it doesn't needed any manual 
tweaks. 

But notice, if you use, for example, LocalDate to display and storing dates, I highly recomend you to add **jackson-datatype-jsr310**
into your **pom.xml**, implement custom **Jackson ObjectMapper** and register **JavaTimeModule** in it. So after this you must create 
**ElasticsearchTemplate** bean with your ObjectMapper.

The following notable thing is the way I update ElasticSearch storage and keep it sync wth my base. 
I used **Spring AOP** and few custom annotations. 

Every time i start application, i want to refresh ElasticSearch storage. Specificaly in this small project this feature is overkill, 
but it may be helpfull when you have **data.sql** file and you want to frequently update the scheme manually.

I used **CompletableFuture** class to make updating proccess async. I know, Spring have **@Async** annotation and can do async job for you, 
but i wanted to learn more about **CompletableFuture** so i decided to go this way.

Another repo with Android Client side will be soon.

I hope, this small project with practices, that i collected bit by bit, will help someone as much, 
as it could help me if I found it earlier
