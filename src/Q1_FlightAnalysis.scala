import scala.io.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Q1_FlightAnalysis {
  def main(args: Array[String]): Unit = {
    // Define file paths
    val flightDataFile = "data/flightData.csv"

    // Read flight data and calculate total number of flights for each month
    val flightsPerMonth = calculateFlightsPerMonth(flightDataFile)

    // Print results
    println("Month\tNumber of Flights")
    flightsPerMonth.foreach { case (month, numFlights) =>
      println(s"$month\t$numFlights")
    }
  }

  def calculateFlightsPerMonth(flightDataFile: String): Map[Int, Int] = {
    // Define date format
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Read flight data CSV file
    val flights = Source.fromFile(flightDataFile)
      .getLines()
      .drop(1) // Skip header
      .map(_.split(","))
      .map(fields => (fields(4), 1)) // Extract date and initialize count to 1
      .toList

    // Parse dates, extract month, and calculate total number of flights for each month
    val flightsPerMonth = flights.groupBy { case (dateStr, _) =>
      val date = LocalDate.parse(dateStr, dateFormatter)
      date.getMonthValue
    }.mapValues(_.size)

    return  flightsPerMonth.toList.sortBy { case (month, _) => -month }.toMap
  }
}
