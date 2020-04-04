name := "HelloApi"

version := "0.3"

scalaVersion := "2.13.1"

javaOptions in Universal ++= Seq("-jvm-debug 5005")

mainClass in Compile := Some("HelloApi")

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-servlet" % "9.3.12.v20160915",
  "org.eclipse.jetty" % "jetty-server" % "9.3.12.v20160915"
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

packageName in Universal := "helloapi"