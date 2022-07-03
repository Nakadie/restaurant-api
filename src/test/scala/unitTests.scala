import io.undertow.Undertow

import utest._

object unitTests extends TestSuite {
  def withServer[T](example: cask.main.Main)(f: String => T): T = {
    val server = Undertow.builder
      .addHttpListener(8081, "localhost")
      .setHandler(example.defaultHandler)
      .build
    server.start()
    val res =
      try f("http://localhost:8081")
      finally server.stop()
    res
  }

  val tests = Tests {
    Symbol("conntest") - {
      test("httpresponse") - withServer(httpresponse) { host =>
        val success = requests.get(host)
        success.text() ==> "connected"
        success.statusCode ==> 200

        requests.get(s"$host/doesnt-exist", check = false).statusCode ==> 404

        requests.post(s"$host/tables", data = "hello", check = false).statusCode ==> 405

        requests.get(s"$host/tables", check = true).statusCode ==> 200

        requests.get(s"$host/tables/orders", data = """{"tableNumber": 1}""", check = true).statusCode ==> 200

        val order = """{"tableNumber": 5, "orders": ["pizza", "hamburger"]}"""
        val orders = requests.post(s"$host/tables/orders", data = order, check = true)
        orders.statusCode ==> 200
        //first time checking order is complete
        orders.text() ==> "complete"

        //second time checking order gets a new message
        requests.post(s"$host/tables/orders", data = order, check = true).text() ==> "already have order"

      }
    }
  }
}