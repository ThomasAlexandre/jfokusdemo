package se.devcode.jfokus

import org.specs._
import unfiltered.response._
import dispatch._
import net.liftweb.json.JsonDSL._
import scala.util.logging.ConsoleLogger

object RestClientSpec extends SpecificationWithJUnit
                      with unfiltered.spec.jetty.Served {
  
  def setup = { _.filter(unfiltered.filter.Planify {
      case _ => Xml(<deals>
                      <deal>
                        <title>Helicopter Tour</title>
                        <disc>60</disc>
                        <city>Stockholm</city>
                      </deal>
                      <deal>
                        <title>10% OFF on Hotel Bedroom</title>
                        <disc>10</disc>
                        <city>Paris</city>
                      </deal>
                    </deals>)})}

  "Getting deals with more than 20% discount " should {
    
    "return only one Stockholm deal" in {
      val restClient = new RestClient
      restClient.nbOfGoodDealsByCity(host) must_== Map("Stockholm" -> 1)
    }
  }
}

