import scala.io.Source

object Q3_LongestRunWithoutUK {
  def main(args: Array[String]): Unit = {
    // Define file paths
    val flightDataFile = "data/flightData.csv"

    // Find the greatest number of countries a passenger has been in without being in the UK
    val longestRuns = findLongestRunsWithoutUK(flightDataFile)

    // Print results
    println("Passenger ID\tLongest Run")
    longestRuns.foreach { case (passengerId, longestRun) =>
      println(s"$passengerId\t$longestRun")
    }
  }

  def findLongestRunsWithoutUK(flightDataFile: String): Map[Int, Int] = {
    // Read flight data CSV file
    val flightData = Source.fromFile(flightDataFile)
      .getLines()
      .drop(1) // Skip header
      .map(_.split(","))
      .map(fields => (fields(0).toInt, fields(3))) // Extract passenger ID and destination
      .toList

    // Group flight data by passenger ID
    val flightsPerPassenger = flightData.groupBy(_._1)

    // Calculate longest run without UK for each passenger
    val longestRuns = flightsPerPassenger.mapValues { flights =>
      var longestRun = 0
      var currentRun = 0
      var previousCountry = ""

      for ((_, country) <- flights) {
        if (country != "UK") {
          // If the passenger is not in the UK, increment the current run
          currentRun += 1
        } else {
          // If the passenger is in the UK, update the longest run if needed and reset the current run
          longestRun = math.max(longestRun, currentRun)
          currentRun = 0
        }
        previousCountry = country
      }

      // Update longest run if needed after processing all flights
      longestRun = math.max(longestRun, currentRun)
      longestRun
    }

    return longestRuns.toMap
  }
}
