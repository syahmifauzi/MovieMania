# MovieMania
CSC1103 (OOP) Group Project

# TODO: To be improved later
1) Better project structure: Separate directory for views, controllers, movie_mania (maybe)
2) Scenes for receipt and about
3) Figure out how to communicate between different scenes
>> This [JavaFX - Communication Between Controllers](https://www.youtube.com/watch?v=NgubWgheboI) might help.
4) Figure out how to compile into build directory and maintain the directories structure

# Create JAR from JAVA source files
In project directory:
```sh
  $ echo Main-Class: Main > MANIFEST.MF
  $ javac *.java  # compile all java files
  $ java Main # test if the project working or not
  $ jar -cvmf MANIFEST.MF MovieMania.jar *.class *.css img movie_mania/*.class views movie-data # create jar
  $ java -jar MovieMania.jar # run jar
```
