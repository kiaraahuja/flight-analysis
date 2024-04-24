import scala.io.Source

object Q2_FrequentFlyers {
  def main(args: Array[String]): Unit = {
    // Define file paths
    val flightDataFile = "data/flightData.csv"
    val passengersFile = "data/passengers.csv"

    // Find the names of the 100 most frequent flyers
    val frequentFlyers = findTopFrequentFlyers(flightDataFile, passengersFile, 100)

    // Print results
    println("Passenger ID\tNumber of Flights\tFirst name\tLast name")
    frequentFlyers.foreach { case (passengerId, numFlights, firstName, lastName) =>
      println(s"$passengerId\t$numFlights\t$firstName\t$lastName")
    }
  }

  def findTopFrequentFlyers(flightDataFile: String, passengersFile: String, limit: Int): List[(Int, Int, String, String)] = {
    // Read flight data and passengers
    val flightData = readFlightData(flightDataFile)
    val passengers = readPassengers(passengersFile)

    // Count the number of flights for each passenger
    val flightsPerPassenger = flightData.groupBy(_._1).mapValues(_.size)

    // Sort passengers by the number of flights in descending order
    val topPassengers = flightsPerPassenger.toList.sortBy { case (_, numFlights) => -numFlights }.take(limit)

    // Extract details for the top passengers
    val frequentFlyers = topPassengers.flatMap { case (passengerId, numFlights) =>
      passengers.get(passengerId).map { case (firstName, lastName) =>
        (passengerId, numFlights, firstName, lastName)
      }
    }

    frequentFlyers
  }

  def readFlightData(flightDataFile: String): List[(Int, Int)] = {
    // Read flight data CSV file
    val flightData = Source.fromFile(flightDataFile)
      .getLines()
      .drop(1) // Skip header
      .map(_.split(","))
      .map(fields => (fields(0).toInt, fields(1).toInt))
      .toList

    flightData
  }

  def readPassengers(passengersFile: String): Map[Int, (String, String)] = {
    // Read passengers CSV file
    val passengers = Source.fromFile(passengersFile)
      .getLines()
      .drop(1) // Skip header
      .map(_.split(","))
      .map(fields => (fields(0).toInt, (fields(1), fields(2))))
      .toMap

    passengers
  }
}
