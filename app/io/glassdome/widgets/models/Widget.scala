package io.glassdome.widgets.models

import java.time.ZonedDateTime

case class Widget(
    id: Int, 
    name: String,
    owner: Int,
    description: Option[String] = None)