package main.scala

import java.io.BufferedWriter
import java.io.FileWriter
import java.io.File
import scala.collection.mutable.LinkedHashMap

class VocabCounter {
  var vocab: LinkedHashMap[String, Long] = LinkedHashMap.empty[String, Long]

  def addVocab(term: String): Unit = {
    if (vocab.contains(term)) {
      vocab.update(term, vocab.get(term).get + 1L)
    } else {
      vocab += term -> 1L
    }
  }

  def addVocabs(terms: Array[String]): VocabCounter = {
    terms.foreach(addVocab)
    this
  }

  def sortDescending(): VocabCounter = {
    vocab = LinkedHashMap(vocab.toList.sortWith { (x, y) => x._2 > y._2 }: _*)
    this
  }

  def writeOutput(top: Int, output: String): Unit = {
    val outFile = new File(output)
    if (!outFile.exists) {
      val parent = new File(outFile.getParent)
      parent.mkdirs()
      outFile.createNewFile()
    } else {
      outFile.delete()
      outFile.createNewFile()
    }
    val writer = new BufferedWriter(new FileWriter(outFile))
    writer.flush()
    if (top != 0) {
      writer.write(top + "\n")
      vocab.take(top).foreach(item => {
        writer.write(item._1 + "\t" + item._2 + "\n")
      })
    } else {
      writer.write(vocab.size + "\n")
      vocab.foreach(item => {
        writer.write(item._1 + "\t" + item._2 + "\n")
      })
    }
    writer.close()
  }
}