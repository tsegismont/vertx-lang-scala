package io.vertx.lang.scala.streams.stage

import io.vertx.lang.scala.streams.api.{SimpleStage, Stage}
import io.vertx.lang.scala.{ScalaLogger, VertxExecutionContext}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * A [[Stage]] that performes an asynchronous side effect. It ensures that all resulting operations are executed on the correct
  * [[VertxExecutionContext]].
  * If a failure occurs a new token is propagated upstream to compensate the loss of a token.
  * @param f the function producing the [[Future]]
  * @param failureHandler called if a [[Future]] produced a failure
  * @param ec the [[VertxExecutionContext]] all resulting operations run on
  * @tparam I input event type
  */
class ProcessAsyncStage[I](f: I => Future[Unit], failureHandler: (I, Throwable) => Unit = (a: I, t: Throwable) => {})
                             (implicit ec: VertxExecutionContext) extends SimpleStage[I, I] {
  protected val Log = ScalaLogger.getLogger(getClass.getName)

  override def next(event: I): Unit = {
    f(event).onComplete {
      case Success(i) => subscriber.onNext(event)
      case Failure(t) => {
        Log.warn(s"Failed mapAsync for $event", t)
        receiveSubscription.request(1)
        failureHandler(event, t)
      }
    }
  }
}
