name := "glitter"

version := "0.2"

organization := "com.github.julienrf"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

unmanagedClasspath in Compile += Attributed.blank(new java.io.File("yop"))