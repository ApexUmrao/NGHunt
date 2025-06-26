package com.newgen.event;

import com.newgen.util.Log;

class EventGen extends Log {
   public EventGen() {
      logger.info("Constructor called");
   }

   public static void main(String[] args) {
      try {
         initializeLogger();
         EventGenBot objTC = new EventGenBot();
         Thread objThread = new Thread(objTC);
         objThread.start();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
