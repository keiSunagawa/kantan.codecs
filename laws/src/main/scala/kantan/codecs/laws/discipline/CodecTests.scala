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

package kantan.codecs.laws.discipline

import kantan.codecs.laws._
import kantan.codecs.laws.CodecValue.{IllegalValue, LegalValue}
import org.scalacheck.Arbitrary
import org.scalacheck.Prop._

trait CodecTests[E, D, F, T] extends DecoderTests[E, D, F, T] with EncoderTests[E, D, T] {
  def laws: CodecLaws[E, D, F, T]

  override implicit val arbD: Arbitrary[D] = Arbitrary(arbLegal.arbitrary.map(_.decoded))

  private def coreRules[A: Arbitrary, B: Arbitrary](implicit av: Arbitrary[CodecValue[E, D]]): RuleSet =
    new DefaultRuleSet(
      "round trip",
      Some(encoder[A, B]),
      "round trip (encoding)"              → forAll(laws.roundTripEncoding _),
      "round trip (decoding)"              → forAll(laws.roundTripDecoding _),
      "mapError identity(encoding)"        → forAll(laws.mapErrorIdentityEncoding _),
      "mapError composition(decoding)"     → forAll(laws.mapErrorCompositionEncoding[A, B] _),
      "imap identity (encoding)"           → forAll(laws.imapIdentityEncoding _),
      "imap identity (decoding)"           → forAll(laws.imapIdentityDecoding _),
      "imap composition (encoding)"        → forAll(laws.imapCompositionEncoding[A, B] _),
      "imap composition (decoding)"        → forAll(laws.imapCompositionDecoding[A, B] _),
      "imapEncoded identity (encoding)"    → forAll(laws.imapEncodedIdentityEncoding _),
      "imapEncoded identity (decoding)"    → forAll(laws.imapEncodedIdentityDecoding _),
      "imapEncoded composition (encoding)" → forAll(laws.imapEncodedCompositionEncoding[A, B] _),
      "imapEncoded composition(decoding)"  → forAll(laws.imapEncodedCompositionDecoding[A, B] _)
    )

  def bijectiveCodec[A: Arbitrary, B: Arbitrary]: RuleSet = new RuleSet {
    implicit val arbValues: Arbitrary[CodecValue[E, D]] = Arbitrary(arbLegal.arbitrary)

    val name = "bijective codec"
    val bases = Nil
    val parents = Seq(coreRules[A, B], bijectiveDecoder[A, B])
    val props = Seq.empty
  }

  def codec[A, B](implicit arbA: Arbitrary[A], arbB: Arbitrary[B], ai: Arbitrary[IllegalValue[E, D]]): RuleSet =
    new RuleSet {
      val name = "codec"
      val bases = Nil
      val parents = Seq(coreRules[A, B], decoder[A, B])
      val props = Seq.empty
    }
}

object CodecTests {
  def apply[E, D, F, T]
  (implicit l: CodecLaws[E, D, F, T], af: Arbitrary[F], al: Arbitrary[LegalValue[E, D]]): CodecTests[E, D, F, T] =
    new CodecTests[E, D, F, T] {
      override def laws = l
      override implicit def arbLegal = al
      override implicit def arbF = af
    }
}
