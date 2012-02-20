package se.devcode.jfokus

import dispatch._

case class Deal(title: String = "Empty", discount: Int = 0, city: String = "Stockholm")

object RestClient {

  val request = url("http://localhost:8086/deals")

  def main(args: Array[String]) {

    val dealsAsXml = Http(request <> { r => r \\ "deal" })

    println("All Deals as XML: " + dealsAsXml)

    val deals = dealsAsXml map { d => Deal((d \ "title").text, (d \ "discount").text.toInt, (d \ "city").text) }

    val (badDeals, goodDeals) = deals partition { _.discount < 20 }

    val goodDealsByCity = goodDeals groupBy { _.city }

    val nbOfGoodDealsByCity = goodDealsByCity mapValues { _.size }

    println("Good Deals sorted by city: " + nbOfGoodDealsByCity)

  }
}

class RestClient {
    
    // Alternative implementations of above

    // As a tweetable oneliner
    def nbOfGoodDealsByCity(r:Request): Map[String, Int] =
      (for{x<-Http(r<>{_\"deal"});d=(x\"disc").text.toInt if d>20}yield Deal((x\"title").text,d,(x\"city").text))groupBy(_.city)mapValues(_.size)
    
    // A little more readable version
    def nbOfGoodDealsByCity2(request:Request): Map[String, Int] = {
      val deals = for {
        deal <- Http(request <> { r => r \\ "deal" })
        discount = (deal \ "disc").text.toInt if discount > 20
        } yield Deal((deal \ "title").text,discount,(deal \ "city").text)    
      deals groupBy(_.city) mapValues(_.size)
    }
}
