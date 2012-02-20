package se.devcode.jfokus

import dispatch._
import akka.dispatch._
import Future.flow

object AkkaSample {
  
  def main(args: Array[String]) {
   
   val params = Promise[Map[String,String]]()
   
   val deals = Future( Http( url("http://localhost:8086/deals") <<? params.get as_str ))
   
   println ("Result: "+ deals.result)
   
   flow { params << Map( "city" -> "stockholm" ) }
   
   //Thread.sleep(2000)

   println("Result: "+ deals.result)
   
  }
}

