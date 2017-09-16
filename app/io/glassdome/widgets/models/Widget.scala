package io.glassdome.widgets.models

import java.time.ZonedDateTime

case class Widget(
    id: Int, 
    name: String,
    owner: Int,
    kind: Option[String] = None ,
    image: Option[String] = None,
    description: Option[String] = None)