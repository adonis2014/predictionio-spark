package pandaspark.examples

import spark.SparkContext
import spark.SparkContext._
import spark.SparkFiles;

import au.com.bytecode.opencsv.CSVReader

import java.io.StringReader

object TestableLoadCsvExample {
  def parseLine(line: String): Array[Double] = {
      val reader = new CSVReader(new StringReader(line))
      reader.readNext().map(_.toDouble)
  }
  def main(args: Array[String]) {
    if (args.length != 2) {
      System.err.println("Usage: TestableLoadCsvExample <master> <inputfile>")
      System.exit(1)
    }
    val master = args(0)
    val inputFile = args(1)
    val sc = new SparkContext(master, "Load CSV Example",
			      System.getenv("SPARK_HOME"),
			      Seq(System.getenv("JARS")))
    sc.addFile(inputFile)
    val inFile = sc.textFile(inputFile)
    val numericData = inFile.map(parseLine)
    val summedData = numericData.map(row => row.sum)
    println(summedData.collect().mkString(","))
  }

}
