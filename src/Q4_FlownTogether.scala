
import scala.io.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Q4_FlownTogether {


  def flownTogether(atLeastNTimes: Int, fromDate: LocalDate, toDate: LocalDate): List[(Int, Int, Int, LocalDate, LocalDate)] = {
    // Define file paths
    val flightDataFile = "data/flightData.csv"

    // Define date formatter
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Read flight data CSV file
    val flightData = Source.fromFile(flightDataFile)
      .getLines()
      .drop(1) // Skip header
      .map(_.split(","))
      .map(fields => (fields(0).toInt, fields(1).toInt, LocalDate.parse(fields(4), dateFormatter))) // Extract passenger IDs and date
      .filter { case (_, _, date) => date.isAfter(fromDate) && date.isBefore(toDate) } // Filter flights within the specified range
      .toList

    // Group flights by passenger pairs
    val flightsByPair = flightData.groupBy { case (passenger1, passenger2, _) =>
      if (passenger1 < passenger2) (passenger1, passenger2) else (passenger2, passenger1)
    }

    // Calculate number of flights together for each pair within the range
    val flightsTogether = flightsByPair.flatMap { case (pair, flights) =>
      val numFlights = flights.size
      if (numFlights > atLeastNTimes) {
        val (passenger1, passenger2) = pair
        val from = flights.map(_._3).min
        val to = flights.map(_._3).max
        Some((passenger1, passenger2, numFlights, from, to))
      } else {
        None
      }
    }

    flightsTogether.toList
  }

  // Example usage:
  def main(args: Array[String]): Unit = {
    val fromDate = LocalDate.parse("2017-01-01")
    val toDate = LocalDate.parse("2017-12-31")
    val atLeastNTimes = 1
    val results = flownTogether(atLeastNTimes, fromDate, toDate)
    println("Passenger 1 ID\tPassenger 2 ID\tNumber of flights together\tFrom\tTo")
    results.foreach { case (passenger1, passenger2, numFlights, from, to) =>
      println(s"$passenger1\t$passenger2\t$numFlights\t$from\t$to")
    }
  }

}
