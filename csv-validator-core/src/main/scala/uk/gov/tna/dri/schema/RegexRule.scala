package uk.gov.tna.dri.schema

import util.matching.Regex
import scalaz._
import Scalaz._
import uk.gov.tna.dri.metadata.Row

case class RegexRule(regex: Regex) extends Rule {

  val name = "regex"

  override def evaluate(columnIndex: Int, row: Row, schema: Schema): ValidationNEL[String, Any] = {
    val columnDefinition = schema.columnDefinitions(columnIndex)
    val reg = if (columnDefinition.contains(IgnoreCase())) "(?i)" + regex.pattern.pattern else regex.pattern.pattern

    if (row.cells(columnIndex).value matches reg) true.successNel[String]
    else error(regex.pattern.pattern, columnIndex, row, schema)
  }
}


