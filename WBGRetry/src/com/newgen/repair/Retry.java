package com.newgen.repair;

import com.newgen.util.Log;

class Retry extends Log {
   public Retry() {
      logger.info("Constructor called");
   }

   public static void main(String[] args) {
      try {
         initializeLogger();
         RetryBot objTC = new RetryBot();
         Thread objThread = new Thread(objTC);
         objThread.start();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
