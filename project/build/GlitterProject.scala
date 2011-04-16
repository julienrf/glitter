import sbt._
import de.element34.sbteclipsify._

class GlitterProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify {
  val scalatest = "org.scalatest" % "scalatest" % "1.3"
  override def artifactID = "glitter"
}