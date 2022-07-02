/*
import org.sqlite.{SQLiteConfig, SQLiteDataSource}
import scalikejdbc.{AutoSession, ConnectionPool, DB, DataSourceConnectionPool}
import SQLiteConfig._
import org.sqlite._

object database extends App {
  Class.forName("org.sqlite.JDBC")
  ConnectionPool.singleton("jdbc:sqlite:orders.db", null, null)
/*  ConnectionPool.add("mydb", s"jdbc:sqlite:orders.db", null, null)
  ConnectionPool.add(
    "mydb_ro", {
      val conf = new SQLiteConfig()
      conf.setReadOnly(true)
      val source = new SQLiteDataSource(conf)
      source.setUrl(s"jdbc:sqlite:orders.db")
      new DataSourceConnectionPool(source)
    }
  )*/


  implicit val session = AutoSession


  sql"""
  create table if not exists tables (tableNumber integer);

  create table if not exists orders (
          food text,
          cooktime text,
          foreign key (tableNumber) references table(tableNumber)
        )""".execute.apply()


  def initializeDB(): Unit = {
    val count = DB autoCommit { implicit session =>
      sql"insert into tables (tableNumber) values (1)".execute.apply()
    }

    session.close()
  }

  def inputOrder(): Unit = {
    val count = DB autoCommit { implicit session =>
      sql"insert into tables (tableNumber) values (1)".execute.apply()
    }

    session.close()
  }

  initializeDB()
  val test: Option[Int] = DB autoCommit { implicit session =>
    sql"select tableNumber from tables where tableNumber = (1)".map(rs => rs.int("tableNumber")).single.apply()

    session.close()
    println(test)
  }

  def getorder(tablenumber: Int) = {
    val order: List[String] = DB readOnly { implicit session =>
      sql"SELECT food FROM orders WHERE tableNumber =:$tablenumber".map(rs => rs.string("food")).list.apply()
    }
    val cooktime: List[String] = DB readOnly { implicit session =>
      sql"SELECT cooktime FROM orders WHERE tableNumber =:$tablenumber".map(rs => rs.string("food")).list.apply()
    }
    val zipped = order zip cooktime
    val orders = zipped.map { case (order, cooktime) => new models.Order(order, time = cooktime) }
    val ret = new models.Table(tablenumber, orders)
  }

}
*/
