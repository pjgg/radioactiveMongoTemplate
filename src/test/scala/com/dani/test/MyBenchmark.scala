package com.dani.test

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.radioactiveMongoTemplate.crud.util.{Person, PersonDaoImpl}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@State(Scope.Thread)
@Fork(1)
class MyBenchmark {

  var personDaoImpl = new PersonDaoImpl()

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @OutputTimeUnit(TimeUnit.SECONDS)
  @Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
  def testMethod(): Any = {

    val futureList: Future[List[Person]] = personDaoImpl.findAll()
    futureList.map { list =>
      list.size
    }
  }

}
