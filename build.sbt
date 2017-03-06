name := "twitter_bruno"

version := "1.0"

lazy val `twitter_bruno` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs )

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"