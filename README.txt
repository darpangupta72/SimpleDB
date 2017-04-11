						THE SIMPLEDB DATABASE SYSTEM (EXTENDED)


I. Problem Statement

Implemented a new data type, timestamp in the SimpleDB package and integrated it with the implementation
of BTree index provided. Used the BTree index to support efficient between queries.

Details: SimpleDB (http://www.cs.bc.edu/~sciore/simpledb/intro.html) is a small database used
only for academic purposes. It supports a restricted version of SQL and two data types – Integer (int) and String (varchar). We have extended support for the Timestamp (timestamp) data type. Given a time interval [t , t'], it is often required to efficiently retrieve all records with timestamps between t and t'. Here, we can specify such a query using the between keyword.

A B-Tree is an index data structure that allows efficient retrieval of records that correspond to a particular search key, or a range of search keys. SimpleDB has an existing implementation of the B-Tree. We have extended this implementation to support timestamp data and subsequently used it to answer queries
with the between keyword.


II. Features

The SimpleDB implementation now supports the following operations:

1. The timestamp data type:

Implemented the support for the data type, timestamp, which will store temporal information
(date and time) and associated usual data manipulation operations:

• Create: The user can now specify a column’s data type as timestamp. 
For e.g., create table example (recordID int, recordTime timestamp);

• Insert: The user can insert tuples having temporal data using INSERT statements. 
For e.g., insert into example(recordID, recordTime) values(1,‘2011-10-09 20:00:00’);

• Delete: Deletion of tuples with a specified timestamp is now supported. 
For e.g., delete from example where recordTime=‘2011-10-09 20:00:00’;

• Update: Updation of tuples with a specified timestamp. 
For e.g., update example set recordTime=‘2016-10-09 20:00:00’ where recordTime=‘2011-10-09 20:00:00’;

• Select/Project: You should be able to query using the field with data type as timestamp. 
For e.g., select recordTime from example where recordID=3;
							or
	      select recordID from example where recordTime=‘2011-10-09 20:00:00’;


2. Creation of BTree over this data type:

The user can now specify and create a BTree index as follows:
create index testindex on example(recordTime);

This implementation supports incremental inserts on the index, i.e., the index and the table are updated as and when new data records are inserted/updated.

3. Answer between queries:

The system now supports a new keyword, between. This will be used to query for records with timestamps that fall within a given range. 

For e.g., the query
select recordID, recordTime from example where recordTime between ‘2011-10-08 20:00:00’ ‘2011-10-09 22:00:00’; 
returns all tuples where the timestamp falls between ‘2011-10-08 20:00:00’ and ‘2011-10-09 22:00:00’ (both inclusive).


III. Error Handling

No additional checks on the syntax have been made, only those on the original SimpleDB implementation are used. However, this code correctly identifies / handles the following types of errors:

1. InvalidIntervalError: When the between query involves timestamps t and t' where t > t', the code identifies the invalid interval and reports an appropriate exception.

2. InvalidDateFormatError: If the timestamps in a query have an invalid input format, for eg, 
‘2016-23-31 23:59:59’, then the code reports it as an InvalidDateFormatError.

3. MemoryError: The maximum number of tuples in any relation have been fixed to be 10^5. If one attempts to insert more tuples, the code reports a MemoryError.
