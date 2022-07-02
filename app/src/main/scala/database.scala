import ujson.Obj

//extending to cask.MainRoutes sets the default server location to http://localhost:8080
object database extends cask.MainRoutes{

  @cask.get("/")
  def index() = {
    "Hello!"
  }

  //display all tables with their current orders
  @cask.get("/tables")
  def displayAll() = {
    "heres a list of all the tables"
  }

  //display all orders for specific table
  @cask.get("/tables/orders")
  def displayTableOrders(table: Int): String = {
    s"heres all the orders for table $table"
  }

  //place orders for specific table
  @cask.post("/tables/orders")
  def placeOrders(table: Int, order: Seq[String]): String = {
    s"order placed for table $table order: ${order.toList}"
  }

  //change a single order from one item to another
  @cask.patch("/tables/order")
  def changeOrder(table: Int, oldOrder: Seq[String], newOrder: Seq[String]): String = {
    s"order for table $table, changed from $oldOrder to $newOrder"
  }
  @cask.patch("/tables/json-obj")
  def jsonEndpointObj(change: ujson.Value) = {
    ujson.Obj(change)
  }
  @cask.postJson("/json-obj")
  def jsonEndpointObj(value1: ujson.Value, value2: Seq[Int]) = {
    ujson.Obj(
      "value1" -> value1,
      "value2" -> value2
    )
  }


  //10 threads and make sure each makes a request.
  //return as json


  initialize()
}