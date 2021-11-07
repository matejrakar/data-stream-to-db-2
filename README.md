# data-stream-to-db
Data stream to DB by Matej Rakar

This program reads a .txt file that simulates continuous data stream. It's objective is to insert data in database
as fast as possible, but the order of insertion must be the same as read order based on MatchId. It must also keep 
track of read data in ordered way.

Each line contains MatchId, which looks like "123A1", the number on the left of a character being actual MatchId,
the number on the right being Occurence (represents in which order the data with same MatchId was read) and 
the character in the middle, which represents processing difficulty; A being 1000ms and B being 1ms.

The program uses multithreading so that multiple tasks with different difficulty levels can be processed at 
the same time. For database insertion, it uses DBCP connection pool to avoid wasting time with multiple connections
opening and closing. When data is ready to be inserted, it is passed to another thread to further improve efficiency.

To test this application, please follow the steps below:

1. Install xampp on your machine.
2. Start Apache and MySql services from xampp.
3. Visit http://localhost/phpmyadmin/ and create DB schema named datastream.
4. Then go to Import tab and import file named data.sql from my repository.
5. Install Eclipse IDE with Java EE (using JDK 11).
6. Connect it with GitHub and clone my repository to your machine.
7. Run application.
