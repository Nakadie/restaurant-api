
import ujson.{Arr, Js, Obj, Value, read}
import upickle.default._

//extending to cask.MainRoutes sets the default server location to http://localhost:8080
object httpresponse extends cask.MainRoutes{

  @cask.get("/")
  def index() = {
    "Hello!"
  }

  //display all tables with their current orders ########done
  @cask.getJson("/tables")
    def displayAll() = {
    ujson.Arr(values.tables.map { x => x.toJson()})
  }

  //display all orders for specific table ######### done
  @cask.get("/tables/orders")
  def displayTableOrders(tableNum: cask.Request): Value = {
    val data = ujson.read(tableNum)
    val table = values.tables.filter { x => x.tableNumber == data("tableNumber").value }
    val ret = table(0).toJson()
    val arr = ujson.Arr(ret)
    arr(0)
  }


  //place orders for specific table #######done
  @cask.post("/tables/orders")
  def placeOrders(req: cask.Request) = {
    val data = ujson.read(req)
    val orders = data("orders").arr
    val ordersList = orders.map{x => models.Order(x.toString().slice(1, (x.toString().length)- 1))}
    val neworder = models.Table(data("tableNumber").toString().toInt, ordersList.toList)
    if (values.tables.contains(neworder)) {
      "already have order"
    } else {
      values.tables += neworder
      "complete"
    }

  }

  //change a single order from one item to another
  @cask.patch("/tables/orders")
  def changeOrder(value: cask.Request): String = {
    val data = ujson.read(value)
    val target = values.tables.filter(x => x.tableNumber == data("tableNumber").toString().toInt)
    println(target)
    println(data("oldOrder").toString())
    /*val newtable = target(0).orders.map{x => x.food == data("oldOrder").toString(). }*/
    s"order for table ${data("tableNumber")}, changed from ${data("oldOrder").toString()} to ${data("newOrder").toString()}"
  }


  //10 threads and make sure each makes a request.
  //return as json
  initialize()
}