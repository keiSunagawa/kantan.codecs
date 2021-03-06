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
package shapeless

import _root_.shapeless._
import laws._
import laws.discipline._, arbitrary._
import strings._

object Instances extends DisciplineSuite {

  // - HList / Coproduct instances -------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------

  implicit def hlistEncoder[A: StringEncoder]: StringEncoder[A :: HNil] =
    StringEncoder.from {
      case h :: _ => StringEncoder[A].encode(h)
    }

  implicit def hlistDecoder[A: StringDecoder]: StringDecoder[A :: HNil] =
    StringDecoder[A].map(h => h :: HNil)
}

@SuppressWarnings(Array("org.wartremover.warts.Null"))
class InstancesTests extends DisciplineSuite {
  import Instances._

  // - Tests -----------------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  checkAll("StringDecoder[Int Or Boolean]", StringDecoderTests[Int Or Boolean].decoder[Int, Int])
  checkAll("StringEncoder[Int Or Boolean]", StringEncoderTests[Int Or Boolean].encoder[Int, Int])
  checkAll("StringCodec[Int Or Boolean]", StringCodecTests[Int Or Boolean].codec[Int, Int])

  test("Encoder[?, CNil, ?] should fail") {
    intercept[IllegalStateException] { StringEncoder[CNil].encode(null) }
    ()
  }

}
