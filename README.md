# movie-database 
#Jersey web service 
<p> webapi/actors  -->Show all actors</p>
<p>webapi/movies --> Show all movies</p>
<p>webapi/movies/0/10 --> Show  movies with paging support -- Start form record number list N records </p>
<p>webapi/actors/0/10 --> Show  actors with paging support</p>
<p> webapi/search/movies?year=1995&year=2018 --> Serch movies between two years</p>
<p>webapi/movies/0/10/search?year=1995&year=2018  --> serch movies between years with pagination</p>
<p>webapi/search/movies/bytitle?title=Mission  --> Serch movies by title keywords</p>
<br>
<p> in StartupClass there is apsolute path to 2 images one of them is <br>
 E:\\dev\\java-workspace\\move-database\\src\\main\\resources\\images\\flash.jpg 
please make change to this paths before you start the program</p>
 
 <br>
 primer POST http://localhost:8080/move-database/webapi/movies/movie

```
 {
         "description": "Anna Boden, Ryan Fleck  ",
         "title": "Captain Marvel (2019)",
         "year": "2019" ,
         "actors": [{
                 "firstName": "Brie   ",
                 "lastName": "Larson"
             },
             {
                 "firstName": "Domhnall",
                 "lastName": "Gleeson"
             }
             ]
     }
