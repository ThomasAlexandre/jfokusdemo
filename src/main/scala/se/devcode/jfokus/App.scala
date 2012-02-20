package se.devcode.jfokus

import unfiltered.request._
import unfiltered.response._
import unfiltered.scalate._
import dispatch._
import scala.util.logging.Logged

class App extends unfiltered.filter.Plan {

  val apiURL = "http://api.groupon.com/v2/"
  val clientId = "0320d6e0589d0d2928611f0576662b3dba73b5ed"

  def intent = {

    case request @ Path(Seg(action :: Nil)) & Params(params) =>
      val serviceURL = url(apiURL + action + ".xml?client_id=" + clientId)
      request match {
        case GET(action) =>
          val cities = Http(serviceURL <> { response => response \\ "division" map { div => ((div \ "id").text, (div \ "name").text) } })
          Ok ~> Scalate(request, "divisions.jade", ("cities", cities))

        case POST(action) =>
          val dealURL = Http(serviceURL <<? Map("division_id" -> params("id").headOption.getOrElse("empty")) <> { response =>
            (response \\ "dealUrl").view.map(_.text).first
          })
          Redirect(dealURL)
      }

    case _ => Redirect("/divisions")
  }
}

/** embedded server */
object Server extends Logged {

  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.local(8086)
    http.context("/assets") { _.resources(new java.net.URL(this.getClass().getResource("/www/css"), ".")) }.
      filter(new App).run({ svr => unfiltered.util.Browser.open(http.url) }, { svr => log("shutting down server") })
  }
}
