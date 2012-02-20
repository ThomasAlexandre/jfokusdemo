package se.devcode.jfokus

import org.specs._
import unfiltered.response._
import dispatch._
import net.liftweb.json.JsonDSL._
import scala.util.logging.ConsoleLogger

object AppSpec extends SpecificationWithJUnit with unfiltered.spec.jetty.Served with ConsoleLogger {
  
  def setup = {
    _.filter(unfiltered.filter.Planify {
      case _ => Json(("id"->"honolulu") ~ ("country"->"USA") ~ ("name"->"Honolulu") ~ ("lat"-> 21.3069) ~ ("lng"-> -157.858) )
    })
  }

  "Json GET REST Request" should {
    
    "produce a response" in {
      val (header, body) = Http(host >+ { response => (response >:> { header => header }, response as_str) })
      log("Result header: " + header) 
      log("Result body: " + body)
      body must_== """{"id":"honolulu","country":"USA","name":"Honolulu","lat":21.3069,"lng":-157.858}"""
    }   
  }
}