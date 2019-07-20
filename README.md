# wordfreq-svc

This repo has simple code written in Java8 ( using spring-boot ) which returns top N words from extract of Wiki page.


## Getting Started


### Prerequisites

- JDK8 or above
- maven 3.6.0 or above
- access to maven central for downloading artifacts 
- git client ( optional )

### Installing
- clone this repo 
```
git clone https://github.com/brihadeeswara/wordfreq-svc.git

```
- change directory
```
cd wordfreq-svc 
```
- build artifact

```
mvn clean install

```
   
## Running this service

 - cd to target folder

```
cd target
```
- run jar , this will return top 5 most repeated words , matching java regex "^[a-z]{4,}$" from wiki page#21721040
```
java -jar wordfrequency-service-0.0.1.jar
```

- this service can take 3 command line args
```
pageid- an integer value defaults to 21721040
```
```
regex- java regex to match words to pick, must be in double quotes when passed as command line arg, default is "^[a-z]{4,}$"
```
```
maxwords- number of most frequent numbers to report must be greater than 0 , default is 5
```
- command with all commanmd line args
```
java -jar wordfrequency-service-0.0.1.jar pageid=21721041 regex=".*[a-z]{10,}.*" maxwords=10 
```



### sample output

```
$ java -jar wordfrequency-service-0.0.1.jar pageid=21721040 regex="^[a-z]{4,}$" maxwords=10 
URL:
https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&pageids=21721040
Title: Stack Overflow
Top 10 words:
- 16 questions
- 12 overflow, stack
- 11 that
- 10 users
- 7 answer, answers, reputation
- 6 only, their
- 5 being, from, programming, site
- 4 atwood, developers, jeff, million, more, question, were, which
- 3 about, created, exchange, general, like, points, public, such, than, those, used, vote, voting, website
- 2 able, access, active, android, announced, answering, approximately, april, been, beta, broader, certain, code, database, development, earn, edit, encouraging, features, found, functionality, have, joel, most, network, open, other, over, privileges, profile, registered, related, score, software, source, study, suspension, tags, they, topics, version, with

```
