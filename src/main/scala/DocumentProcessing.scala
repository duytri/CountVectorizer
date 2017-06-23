package main.scala

import java.util.StringTokenizer
import scala.collection.mutable.ArrayBuffer

class DocumentProcessing {
  /**
   * Make array string from a document content
   * @param content string content of a document
   * @param splitter delimiter
   * @return an array string of document content
   */
  def processing(content: String, splitter: String): Array[String] = {
    val tokens = new StringTokenizer(content, splitter)
    var result = new ArrayBuffer[String]
    while (tokens.hasMoreTokens) {
      result.append(tokens.nextElement.toString.toLowerCase)
    }
    result.toArray
  }
}