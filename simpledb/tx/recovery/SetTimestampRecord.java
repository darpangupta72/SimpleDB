package simpledb.tx.recovery;

import simpledb.server.SimpleDB;
import simpledb.buffer.*;
import simpledb.file.Block;
import simpledb.log.BasicLogRecord;

class SetTimestampRecord implements LogRecord {
   private int txnum, offset;
   private long val;
   private Block blk;

   /**
    * Creates a new settimestamp log record.
    * @param txnum the ID of the specified transaction
    * @param blk the block containing the value
    * @param offset the offset of the value in the block
    * @param val the new value
    */
   public SetTimestampRecord(int txnum, Block blk, int offset, long val) {
      this.txnum = txnum;
      this.blk = blk;
      this.offset = offset;
      this.val = val;
   }

   /**
    * Creates a log record by reading five other values from the log.
    * @param rec the basic log record
    */
   public SetTimestampRecord(BasicLogRecord rec) {
      txnum = rec.nextInt();
      String filename = rec.nextString();
      int blknum = rec.nextInt();
      blk = new Block(filename, blknum);
      offset = rec.nextInt();
      val = rec.nextTimestamp();
   }

   /**
    * Writes a setTimestamp record to the log.
    * This log record contains the SETTIMESTAMP operator,
    * followed by the transaction id, the filename, number,
    * and offset of the modified block, and the previous
    * timestamp (long) value at that offset.
    * @return the LSN of the last log value
    */
   public int writeToLog() {
      Object[] rec = new Object[] {SETTIMESTAMP, txnum, blk.fileName(),
         blk.number(), offset, val};
      return logMgr.append(rec);
   }

   public int op() {
      return SETTIMESTAMP;
   }

   public int txNumber() {
      return txnum;
   }

   public String toString() {
      return "<SETTIMESTAMP " + txnum + " " + blk + " " + offset + " " + val + ">";
   }

   /**
    * Replaces the specified data value with the value saved in the log record.
    * The method pins a buffer to the specified block,
    * calls setTimestamp to restore the saved value
    * (using a dummy LSN), and unpins the buffer.
    * @see simpledb.tx.recovery.LogRecord#undo(int)
    */
   public void undo(int txnum) {
      BufferMgr buffMgr = SimpleDB.bufferMgr();
      Buffer buff = buffMgr.pin(blk);
      buff.setTimestamp(offset, val, txnum, -1);
      buffMgr.unpin(buff);
   }
}
