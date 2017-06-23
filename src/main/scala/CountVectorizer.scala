package main.scala

import java.io.File
import scala.collection.mutable.Map
import java.io.BufferedReader
import java.io.FileReader

object CountVectorizer {

  val splitter = " "

  def main(args: Array[String]): Unit = {
    try {
      if (args.length < 4 || args.length == 0 || args(0).equals("-h") || args(0).equals("--help"))
        printHelp
      else {
        // input arguments
        println("Getting user parameters...")
        val params = new ParamsHelper
        val input = params.checkAndGetInput(args, "-i", "--input", ParamsHelperType.STRING).asInstanceOf[String]
        val output = params.checkAndGetInput(args, "-o", "--output", ParamsHelperType.STRING).asInstanceOf[String]
        val top = params.checkAndGetInput(args, "-n", "--top", ParamsHelperType.INTEGER).asInstanceOf[Int]
        if (input == null || output == null) throw new Exception("ERROR: You must declare input and output")
        // Processing
        println("Processing...")
        val docProcessing = new DocumentProcessing
        val vocabCounter = new VocabCounter
        val file = new File(input)
        if (file.isDirectory) { // is directory
          val files = file.listFiles()
          files.foreach(oneFile => {
            val reader = new BufferedReader(new FileReader(oneFile))
            reader.readLine() // ignore first line
            var content = reader.readLine()
            while (content != null) {
              vocabCounter.addVocabs(docProcessing.processing(content, splitter))
              content = reader.readLine()
            }
          })
          println("Output processing and writing...")
          vocabCounter.sortDescending.writeOutput(top, output)
        } else if (file.isFile) { // is a file
          val reader = new BufferedReader(new FileReader(file))
          reader.readLine() // ignore first line
          var content = reader.readLine()
          while (content != null) {
            vocabCounter.addVocabs(docProcessing.processing(content, splitter))
            content = reader.readLine()
          }
          println("Output processing and writing...")
          vocabCounter.sortDescending.writeOutput(top, output)
        }
        println("Complete successfully.")
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
        printHelp()
      }
    }
  }

  def printHelp() = {
    println("Usage: CountVectorizer [Arguments]")
    println("       Arguments:")
    println("              -i --input [path]  : Input file path or folder path")
    println("              -n --top [number]  : Input number of top words to output (ignore or zero for all)")
    println("              -o --output [path] : Output file path")
    println("              -h --help          : Print this help")
  }
}