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

package kantan.codecs
package strings
package java8

import java.time.LocalTime
import laws.discipline._, arbitrary._

class LocalTimeCodecTests extends DisciplineSuite {

  checkAll("StringDecoder[LocalTime]", StringDecoderTests[LocalTime].decoder[Int, Int])
  checkAll("StringDecoder[LocalTime]", SerializableTests[StringEncoder[LocalTime]].serializable)

  checkAll("StringEncoder[LocalTime]", StringEncoderTests[LocalTime].encoder[Int, Int])
  checkAll("StringEncoder[LocalTime]", SerializableTests[StringEncoder[LocalTime]].serializable)

  checkAll("StringCodec[LocalTime]", StringCodecTests[LocalTime].codec[Int, Int])

}
