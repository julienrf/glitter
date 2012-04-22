name := "glitter"

version := "0.2"

organization := "com.github.julienrf"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"

unmanagedClasspath in Compile += Attributed.blank(new java.io.File("yop"))