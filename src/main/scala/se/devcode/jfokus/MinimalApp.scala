package se.devcode.jfokus

import unfiltered.request._
import unfiltered.response._
import unfiltered.scalate._
import dispatch._
import java.net.URL

class MinimalApp extends unfiltered.filter.Plan {
  def intent = {
    case GET(_) =>
      val service = url("http://api.ipinfodb.com/v3/ip-city/")
      val params = Map(
        "key" -> "your client id",
        "format" -> "json")
      ResponseString(Http(service <<? params as_str))
  }
}

object MinimalServer {
  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.local(8086)
    http
      .context("/assets") { _.resources(new URL(this.getClass().getResource("/www/css"), ".")) }
      .filter(new MinimalApp).run({ svr => unfiltered.util.Browser.open(http.url) })
  }
}
