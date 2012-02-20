package se.devcode.jfokus

import unfiltered.request._

import unfiltered.response._
import unfiltered.scalate._
import dispatch._
import scala.util.logging.Logged
import scala.xml._

case class Xml(nodes: NodeSeq)
  extends ComposeResponse(TextXmlContent ~> ResponseString(nodes.toString))

class LocalApp extends unfiltered.filter.Plan {
  
  def intent = {

    case request @ Path("/cities") => Ok ~> Scalate(request, "cities.ssp")

    case request @ Path("/deals") & Params(params) =>
      Ok ~> Scalate(request,params.getOrElse("city",List("all")).first + "_deals.ssp")
    
    case request @ Path("/ds") => Ok ~> Scalate(request, "ds.ssp")
    
    case request @ Path("/d") => Ok ~> Scalate(request, "d.ssp")
    
    case _ => Redirect("/ds")
  }
  
}

/** embedded server */
object LocalServer extends Logged {

  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.local(8086)
    http.context("/assets") { _.resources(new java.net.URL(this.getClass().getResource("/www/css"), ".")) }.
      filter(new LocalApp).run({ svr => unfiltered.util.Browser.open(http.url) }, { svr => log("shutting down server") })
  }
}
