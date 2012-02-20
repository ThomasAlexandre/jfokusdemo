package se.devcode.jfokus

import org.specs._
import unfiltered.response._
import dispatch._
import net.liftweb.json.JsonDSL._
import scala.util.logging.ConsoleLogger

object MinimalAppSpec extends SpecificationWithJUnit with unfiltered.spec.jetty.Served with ConsoleLogger {
  
  def setup = {
    _.filter(unfiltered.filter.Planify {
      case _ => Json(("country"->"SWEDEN") ~ ("cityName" -> "STOCKHOLM") ~ ("lat"-> 59.333) ~ ("lng"-> 18.05) )
    })
  }

  "Json GET REST Request" should {
    
    "produce a location as JSON" in {
      val (header, body) = Http(host >+ { response => (response >:> { header => header }, response as_str) })
      log("Result header: " + header) 
      log("Result body: " + body)
      body must_== """{"country":"SWEDEN","cityName":"STOCKHOLM","lat":59.333,"lng":18.05}"""
    }   
  }
}