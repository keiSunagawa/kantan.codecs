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

import kantan.codecs.laws.discipline.{
  DecoderTests,
  DisciplineSuite,
  EncoderTests,
  StringCodecTests,
  StringDecoderTests,
  StringEncoderTests
}
import kantan.codecs.laws.discipline.arbitrary._

class IntCodecTests extends DisciplineSuite {

  checkAll("StringDecoder[Int]", StringDecoderTests[Int].decoder[Int, Int])
  checkAll("StringEncoder[Int]", StringEncoderTests[Int].encoder[Int, Int])
  checkAll("StringCodec[Int]", StringCodecTests[Int].codec[Int, Int])

  checkAll("TaggedDecoder[Int]", DecoderTests[String, Int, DecodeError, tagged.type].decoder[Int, Int])
  checkAll("TaggedEncoder[Int]", EncoderTests[String, Int, tagged.type].encoder[Int, Int])

}