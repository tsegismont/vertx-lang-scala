/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.scala.ext

import scala.collection.JavaConverters._
import io.vertx.core.json.JsonObject
import io.vertx.core.json.JsonArray
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import scala.concurrent.Promise

import io.vertx.ext.mail.{MailConfig => JMailConfig}
import io.vertx.core
import io.vertx.ext.mail.{MailResult => JMailResult}
import io.vertx.core.{Future => JFuture}
import io.vertx.ext.mail.{MailClient => JMailClient}
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.{Vertx => JVertx}
import io.vertx.ext.mail.{MailMessage => JMailMessage}

package object mail{


  type DKIMSignOptions = io.vertx.ext.mail.DKIMSignOptions
  object DKIMSignOptions {
    def apply() = new DKIMSignOptions()
    def apply(json: JsonObject) = new DKIMSignOptions(json)
  }



  object MailAttachment {
    /**
     * construct an empty MailAttachment object that can be filled with the
     * setters
     */
    def create() = {
      io.vertx.ext.mail.MailAttachment.create()
    }
    /**
     * create a MailAttachment object from a JsonObject representation     * @param json object to be copied
     */
    def create(json: io.vertx.core.json.JsonObject) = {
      io.vertx.ext.mail.MailAttachment.create(json)
    }
    /**
     * create a copy of a MailAttachment object     * @param other object to be copied
     */
    def create(other: io.vertx.ext.mail.MailAttachment) = {
      io.vertx.ext.mail.MailAttachment.create(other)
    }
  }



  /**
    * SMTP mail client for Vert.x
    * 
    * A simple asynchronous API for sending mails from Vert.x applications
    */

  implicit class MailClientScala(val asJava: io.vertx.ext.mail.MailClient) extends AnyVal {

    /**
     * Like sendMail from [[io.vertx.ext.mail.MailClient]] but returns a Scala Future instead of taking an AsyncResultHandler.
     */
    def sendMailFuture(email: io.vertx.ext.mail.MailMessage): scala.concurrent.Future[io.vertx.ext.mail.MailResult] = {
      val promise = concurrent.Promise[io.vertx.ext.mail.MailResult]()
      asJava.sendMail(email, {a:AsyncResult[io.vertx.ext.mail.MailResult] => if(a.failed) promise.failure(a.cause) else promise.success(a.result());()})
      promise.future
    }

  }



  type MailConfig = io.vertx.ext.mail.MailConfig
  object MailConfig {
    def apply() = new MailConfig()
    def apply(json: JsonObject) = new MailConfig(json)
  }




  type MailMessage = io.vertx.ext.mail.MailMessage
  object MailMessage {
    def apply() = new MailMessage()
    def apply(json: JsonObject) = new MailMessage(json)
  }




  type MailResult = io.vertx.ext.mail.MailResult
  object MailResult {
    def apply() = new MailResult()
    def apply(json: JsonObject) = new MailResult(json)
  }



}
