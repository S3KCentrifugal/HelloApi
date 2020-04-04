import org.eclipse.jetty.servlet.ServletHandler
import org.eclipse.jetty.server.{NetworkConnector, Server}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import java.util.concurrent.atomic.AtomicInteger

object HelloApi {
  import CounterServlets._

  val incrementRoute = "/increment"
  val resetRoute = "/reset"
  val server = createServer()
  val handler = new ServletHandler()

  def main(args: Array[String]) ={
    server.setHandler(handler)
    handler.addServletWithMapping(classOf[IncrementServlet], incrementRoute)
    handler.addServletWithMapping(classOf[ResetServlet], resetRoute)

    server.start()
    println(s"Server started on ${port()} with endpoints: '$incrementRoute' and '$resetRoute'")
    server.join()
  }

  def port() = {
    val conn = server.getConnectors()(0).asInstanceOf[NetworkConnector]
    conn.getLocalPort()
  }

  def createServer() = new Server(39611)

  object CounterServlets{
    private var requestCount = new AtomicInteger(0)

    class IncrementServlet extends HttpServlet {
      override protected def doGet(request: HttpServletRequest, response: HttpServletResponse):Unit = {
        requestCount.getAndIncrement() // Thread-Safe Increment

        response.setContentType("text/html")
        response.setStatus(HttpServletResponse.SC_OK)
        response.getWriter().println(s"<h2>Increment received. Count is now $requestCount.</h2>")
      }
    }

    class ResetServlet extends HttpServlet {
      override protected def doGet(request: HttpServletRequest, response: HttpServletResponse):Unit = {
        requestCount.getAndIncrement() // Thread-Safe Increment

        response.setContentType("text/html")
        response.setStatus(HttpServletResponse.SC_OK)
        response.getWriter().println(s"<h2>Counter reset to 0.</h2>")
      }
    }
  }
}
