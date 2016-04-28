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

package kantan.codecs.strings

import kantan.codecs.laws.discipline._
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline
import tagged._

class BooleanCodecTests extends FunSuite with GeneratorDrivenPropertyChecks with Discipline {
  checkAll("StringDecoder[Boolean]", DecoderTests[String, Boolean, Throwable, codecs.type].decoder[Int, Int])
  checkAll("StringEncoder[Boolean]", EncoderTests[String, Boolean, codecs.type].encoder[Int, Int])
  checkAll("StrnigCodec[Boolean]", CodecTests[String, Boolean, Throwable, codecs.type].codec[Int, Int])

  checkAll("TaggedDecoder[Boolean]", DecoderTests[String, Boolean, Throwable, tagged.type].decoder[Int, Int])
  checkAll("TaggedEncoder[Boolean]", EncoderTests[String, Boolean, tagged.type].encoder[Int, Int])
}