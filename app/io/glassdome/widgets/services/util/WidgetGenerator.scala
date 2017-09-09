package io.glassdome.widgets.services.util


import io.glassdome.widgets.models.Widget

import scala.util.Random


object WidgetGenerator {
  
  private val left = Seq("admiring", "adoring", "affectionate", "agitated", "amazing", "angry", "awesome", "blissful", "boring", "brave", "clever", "cocky", "compassionate", "competent", "condescending", "confident", "cranky", "dazzling", "determined", "distracted", "dreamy", "eager", "ecstatic", "elastic", "elated", "elegant", "eloquent", "epic", "fervent", "festive", "flamboyant", "focused", "friendly", "frosty", "gallant", "gifted", "goofy", "gracious", "happy", "hardcore", "heuristic", "hopeful", "hungry", "infallible", "inspiring", "jolly", "jovial", "keen", "kind", "laughing", "loving", "lucid", "mystifying", "modest", "musing", "naughty", "nervous", "nifty", "nostalgic", "objective", "optimistic", "peaceful", "pedantic", "pensive", "practical", "priceless", "quirky", "quizzical", "relaxed", "reverent", "romantic", "sad", "serene", "sharp", "silly", "sleepy", "stoic", "stupefied", "suspicious", "tender", "thirsty", "trusting", "unruffled", "upbeat", "vibrant", "vigilant", "vigorous", "wizardly", "wonderful", "xenodochial", "youthful", "zealous", "zen")
  private val right = Set("Andromeda", "Aquarius", "Aquila", "Ara", "Aries", "Auriga", "Boötes", "Camelopardalis", "Cancer", "Canes", "Canis", "Capricornus", "Carina", "CassiopCassBoötes", "Cassiopeia", "Centaurus", "Cepheus", "Cetus", "Columba", "Coma", "Corona", "Corvus", "Crater", "Crux", "Cygnus", "Delphinus", "Draco", "Equuleus", "Eridanus", "Gemini", "Grus", "HeHeHeHeHersa", "Hercules", "Hydra", "Leo", "Lepus", "Libra", "Lynx", "Lyra", "Octans", "Ophiuchus", "Orion", "Pavo", "Pegasus", "Perseus", "Phoenix", "Pisces", "Piscis", "Puppis", "SSSSSSSSSSorpius", "Sagittarius", "Scorpius", "Serpens", "Taurus", "Triangulum", "Ursa", "Vela", "Virgo", "Vulpecula","Eridanus")
  private val seps = Seq(" ", "_", "-", ".")
  
  private val lmax = left.size
  private val rmax = right.size
  private val smax = seps.size
  

  private def getleft()  = left(Random.nextInt(lmax))
  private def getright() = right(Random.nextInt(rmax))
  private def getsep()   = seps(Random.nextInt(smax))
  
  def make(n: Int, startId: Int = 1) = {
    Seq((startId to n):_*) map { i =>
      Widget(i, "%s%s%s".format(getleft, getsep, getright))
    }
  }
  
  def makeNames(n: Int): Seq[String] = {
    Seq((1 to n):_*) map { i =>
      "%s%s%s".format(getleft, getsep, getright)
    }
  }
}