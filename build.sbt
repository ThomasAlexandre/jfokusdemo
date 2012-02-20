name:="jfokusdemo"

version := "1.0"

organization := "com.devcode.jfokus"

scalaVersion := "2.9.1"

libraryDependencies ++= {
  val version = Map("akka" -> "1.3-RC1",
                    "unfiltered" -> "0.5.0",  // previously "0.4.1"
                    "dispatch" -> "0.8.7")
  Seq(
    "se.scalablesolutions.akka" % "akka-kernel" % version("akka"),
    "net.databinder" %% "unfiltered-filter" % version("unfiltered"),
    "net.databinder" %% "unfiltered-jetty" % version("unfiltered"),
    "net.databinder" %% "unfiltered-json" % version("unfiltered"),
    "net.databinder" %% "unfiltered-scalate" % version("unfiltered"),
    "net.databinder" %% "unfiltered-spec" % version("unfiltered"),
    "net.databinder" %% "dispatch-core" % version("dispatch"),
    "net.databinder" %% "dispatch-http" % version("dispatch"),
    "net.databinder" %% "dispatch-http-json" % version("dispatch"),
    "org.apache.camel" % "camel-jetty" % "2.8.0",
    "org.apache.camel" % "camel-spring" % "2.8.0",
    "org.slf4j" % "log4j-over-slf4j" % "1.6.1",
    "org.slf4j" % "slf4j-simple" % "1.6.1",
    "org.specs2" %% "specs2" % "1.6.1" % "test", 
    "junit" % "junit" % "4.7" % "test"
  )
}

resolvers ++= Seq(
    "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "atlassian maven" at "https://maven.atlassian.com/content/repositories/public/",
    "guiceyfruit google repo" at "http://guiceyfruit.googlecode.com/svn/repo/releases/"
)

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1")

scalacOptions += "-P:continuations:enable"

parallelExecution in Test := false

shellPrompt := { state => "jfokus> " }

initialCommands := "import dispatch._;import akka.dispatch._;import Future.flow"