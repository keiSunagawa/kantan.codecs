/*
 * Copyright 2016 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan.codecs.strings.joda.time.laws.discipline

import kantan.codecs.laws.discipline.GenCodecValue
import org.joda.time.{DateTime, LocalDate, LocalDateTime, LocalTime}
import org.joda.time.format.DateTimeFormatter
import org.scalacheck.Arbitrary

object arbitrary {
  implicit val arbDateTime: Arbitrary[DateTime] =
    Arbitrary(Arbitrary.arbitrary[Long].map(offset ⇒ new DateTime(System.currentTimeMillis + offset)))

  implicit val arbLocalDateTime: Arbitrary[LocalDateTime] = Arbitrary(Arbitrary.arbitrary[Int].map { offset ⇒
    new LocalDateTime(System.currentTimeMillis + offset).withMillisOfSecond(0)
  })

  implicit val arbLocalDate: Arbitrary[LocalDate] = Arbitrary(Arbitrary.arbitrary[Int].map { offset ⇒
    new LocalDate(System.currentTimeMillis + offset)
  })

  implicit val arbLocalTime: Arbitrary[LocalTime] = Arbitrary(Arbitrary.arbitrary[Int].map { offset ⇒
    new LocalTime(System.currentTimeMillis + offset).withMillisOfSecond(0)
  })

  implicit def strDateTime(implicit format: DateTimeFormatter): GenCodecValue[String, DateTime] =
    GenCodecValue.nonFatal[String, DateTime](format.print)(format.parseDateTime)

  implicit def strLocalDateTime(implicit format: DateTimeFormatter): GenCodecValue[String, LocalDateTime] =
    GenCodecValue.nonFatal[String, LocalDateTime](format.print)(format.parseLocalDateTime)

  implicit def strLocalDate(implicit format: DateTimeFormatter): GenCodecValue[String, LocalDate] =
    GenCodecValue.nonFatal[String, LocalDate](format.print)(format.parseLocalDate)

  implicit def strLocalTime(implicit format: DateTimeFormatter): GenCodecValue[String, LocalTime] =
    GenCodecValue.nonFatal[String, LocalTime](format.print)(format.parseLocalTime)
}
