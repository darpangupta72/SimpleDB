 					THE SIMPLEDB DATABASE SYSTEM
                Files Changed to implement Project 2


This document contains the following sections:
    * Implementing timestamp as a type
    * Implementing timestamp with BTree Index
    * Implementing between queries in general plan
    * Implementing between queries with Indexes


I- Timestamp as a Type:
	
	1) Created a class simpledb.query.timestamp.java which implements simpledb.query.Constant.java.

	2) Implemented a isValid(String s1) and extended constant() & modify() in simpledb.parse.Parser.java to correctly identify and store timestamp data type.Also added "timestamp" keyword in simpledb.parse.Lexer,java

	3) Created a class simpledb.tx.recovery.SetTimestampRecord.java which implements simpledb.tx.recovery.LogRecord.java. It is used to update the Log Records(writeToLog()). 
	Also implemented a function setTimestamp(Buffer buff, int offset, long newval) in simpledb.tx.recovey.RecoveryMgr.java
	The following files were also changed
		*simpledb.tx.recovery.LogRecordIterator.java
		*simpledb.tx.recovery.LogRecord.java

	4) Implemented addTimeStampField(String fldname) in simpledb.record.Schema.java 
	& 
	long getTimestamp() and void setTimestamp() in 
		*simpledb.tx.Transaction.java
		*simpledb.record.RecordPage.java
		*simpledb.record.RecorFile.java
		*simpledb.query.SelectScan.java
		*simpledb.query.TableScan.java
		*simpledb.query.Scan.java
		*simpledb.query.ProjectScan.java
		*simpledb.query.ProductScan.java
		*simpledb.multibuffer.MultiBufferProductScan.java
		*simpledb.multibuffer.ChunkScan.java
		*simpledb.log.LogMgr.java
		*simpledb.file.Page.java
		*simpledb.buffer.Buffer.java
	to insert, delete, and update timestamp datatype.
	Also long nextTimestamp() in simpledb.log.BasicLogRecord.java

	5) Implemented decrementnumrecs() and incrementnumrecs() in
		*simpledb.query.TablePlan.java
		*simpledb.query.SelectPlan.java
		*simpledb.query.ProjectPlan.java
		*simpledb.query.ProductPlan.java
		*simpledb.query.Plan.java
		*simpledb.multibuffer.MultiBufferProductPlan.java
		*simpledb.metadata.StatInfo.java
		*simpledb.materialize.MaterializePlan.java

II-Timestamp in BTree Index:
	
	1) Changed BasicUpdatePlanner() and BasicQueryPlanner() in simpledb.server.SimpleDB.java to IndexUpdatePlanner() and HeuristicQueryPlanner().

	2) Changed HashIndex to BTreeIndex in IndexInfo.java

	3) Implemented long getTimestamp() and void setTimestamp() in 
		*simpledb.index.query.IndexSelectPlan.java
		*simpledb.index.query.IndexJoinScan.java
		*simpledb.index.btree.BTreePage.java
		*


	to insert, delete, and update timestamp datatype.
	Also long nextTimestamp() in simpledb.log.BasicLogRecord.java

	4) Implemented decrementnumrecs() and incrementnumrecs() in
		*simpledb.index.query.IndexSelectPlan.java
		*simpledb.index.query.IndexJoinPlan.java
		*simpledb.index.planner.IndexupdatePlanner.java
	to throw MemoryError.

	5) Some minor changes in files such as simpledb.btree.BTreeFormatter.java

III- Between Query in Basic PLan: 
	1) Updated simpledb.query.Term.java to add a new Expression range which is basically corresponds to uppervalue in between query. Did multiple changes in this file to support the new Term structure in a general case(i.e. '=')range will be null.

	2)Added a function equatesWithUpper(String fldname) in simpledb.query.Predicate.java

	3)In general plan, function issatisfied() in simpledb.query.Term() is called for all tuples in the table.

IV- Between Query with BTree Indexing:
	1) Created functions boolean next(Constant val) and and beforeFirst(Constant val1, Constant val2) in
		*simpledb.index.Index.java
		*simpledb.index.btree.BTreeIndex.java
		*simpledb.index.btree.BTreeLeaf.java

	2) Changed insert(RID datarid) in simpledb.index.btree.BTreeLeaf.java to correctly connect the leafs. Original algo is correct for only equalTo queries.