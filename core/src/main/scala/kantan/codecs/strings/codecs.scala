/*
 * Copyright 2017 Nicolas Rinaudo
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

import java.io.File
import java.net.{URI, URL}
import java.nio.file.{Path, Paths}
import java.util.UUID
import kantan.codecs.Result

/** Defines default instances for [[StringEncoder]] and [[StringDecoder]]. */
object codecs {
  /** Defines a [[StringCodec]] instance for `BigDecimal`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[BigDecimal].decode("2")
    * res1: kantan.codecs.Result[DecodeError, BigDecimal] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[BigDecimal].encode(BigDecimal(2D))
    * res2: String = 2.0
    * }}}
    */
  implicit val bigDecimalStringCodec: StringCodec[BigDecimal] =
    StringCodec.from(StringDecoder.decoder("BigDecimal")(s ⇒ BigDecimal(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `BigInt`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[BigInt].decode("2")
    * res1: kantan.codecs.Result[DecodeError, BigInt] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[BigInt].encode(BigInt(2))
    * res2: String = 2
    * }}}
    */
  implicit val bigIntStringCodec: StringCodec[BigInt] =
    StringCodec.from(StringDecoder.decoder("BigInt")(s ⇒ BigInt(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `Boolean`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Boolean].decode("true")
    * res1: kantan.codecs.Result[DecodeError, Boolean] = Success(true)
    *
    * // Encoding example
    * scala> StringEncoder[Boolean].encode(true)
    * res2: String = true
    * }}}
    */
  implicit val booleanStringCodec: StringCodec[Boolean] =
    StringCodec.from(StringDecoder.decoder("Boolean")(s ⇒ s.trim.toBoolean))(_.toString)

  /** Defines a [[StringCodec]] instance for `Char`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Char].decode("a")
    * res1: kantan.codecs.Result[DecodeError, Char] = Success(a)
    *
    * // Encoding example
    * scala> StringEncoder[Char].encode('a')
    * res2: String = a
    * }}}
    */
  implicit val charStringCodec: StringCodec[Char] = StringCodec.from { s ⇒
    // This is a bit dodgy, but necessary: if the string has a length greater than 1, it might be a legal character with
    // padding. The only issue is if the character is *whitespace* with whitespace padding. This is acknowledged and
    // willfully ignored, at least for the time being.
    val t = if(s.length > 1) s.trim else s
    if(t.length == 1) Result.success(t.charAt(0))
    else              Result.failure(DecodeError(s"Not a valid Char: '$s'"))
  }(_.toString)

  /** Defines a [[StringCodec]] instance for `Double`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Double].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Double] = Success(2.0)
    *
    * // Encoding example
    * scala> StringEncoder[Double].encode(2D)
    * res2: String = 2.0
    * }}}
    */
  implicit val doubleStringCodec: StringCodec[Double] =
    StringCodec.from(StringDecoder.decoder("Double")(s ⇒ s.trim.toDouble))(_.toString)

  /** Defines a [[StringCodec]] instance for `Byte`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Byte].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Byte] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[Byte].encode(2)
    * res2: String = 2
    * }}}
    */
  implicit val byteStringCodec: StringCodec[Byte] =
    StringCodec.from(StringDecoder.decoder("Byte")(s ⇒ s.trim.toByte))(_.toString)

  /** Defines a [[StringCodec]] instance for `Float`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Float].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Float] = Success(2.0)
    *
    * // Encoding example
    * scala> StringEncoder[Float].encode(2F)
    * res2: String = 2.0
    * }}}
    */
  implicit val floatStringCodec: StringCodec[Float] =
    StringCodec.from(StringDecoder.decoder("Float")(s ⇒ s.trim.toFloat))(_.toString)

  /** Defines a [[StringCodec]] instance for `Int`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Int].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Int] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[Int].encode(2)
    * res2: String = 2
    * }}}
    */
  implicit val intStringCodec: StringCodec[Int] =
    StringCodec.from(StringDecoder.decoder("Int")(s ⇒ s.trim.toInt))(_.toString)

  /** Defines a [[StringCodec]] instance for `Long`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Long].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Long] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[Long].encode(2L)
    * res2: String = 2
    * }}}
    */
  implicit val longStringCodec: StringCodec[Long] =
    StringCodec.from(StringDecoder.decoder("Long")(s ⇒ s.trim.toLong))(_.toString)

  /** Defines a [[StringCodec]] instance for `Short`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[Short].decode("2")
    * res1: kantan.codecs.Result[DecodeError, Short] = Success(2)
    *
    * // Encoding example
    * scala> StringEncoder[Short].encode(2.toShort)
    * res2: String = 2
    * }}}
    */
  implicit val shortStringCodec: StringCodec[Short] =
    StringCodec.from(StringDecoder.decoder("Short")(s ⇒ s.trim.toShort))(_.toString)

  /** Defines a [[StringCodec]] instance for `String`.
    *
    * {{{
    * // Decoding example
    * scala> StringDecoder[String].decode("foobar")
    * res1: kantan.codecs.Result[DecodeError, String] = Success(foobar)
    *
    * // Encoding example
    * scala> StringEncoder[String].encode("foobar")
    * res2: String = foobar
    * }}}
    */
  implicit val stringStringCodec: StringCodec[String] =
    StringCodec.from(s ⇒ Result.success(s))(_.toString)

  /** Defines a [[StringCodec]] instance for `java.util.UUID`.
    *
    * {{{
    * // Decoding example
    * scala> import java.util.UUID
    *
    * scala> StringDecoder[UUID].decode("123e4567-e89b-12d3-a456-426655440000")
    * res1: kantan.codecs.Result[DecodeError, UUID] = Success(123e4567-e89b-12d3-a456-426655440000)
    *
    * // Encoding example
    * scala> StringEncoder[UUID].encode(UUID.fromString("123e4567-e89b-12d3-a456-426655440000"))
    * res2: String = 123e4567-e89b-12d3-a456-426655440000
    * }}}
    */
  implicit val uuidStringCodec: StringCodec[UUID] =
    StringCodec.from(StringDecoder.decoder("UUID")(s ⇒ UUID.fromString(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `java.net.URL`.
    *
    * {{{
    * // Decoding example
    * scala> import java.net.URL
    *
    * scala> StringDecoder[URL].decode("http://localhost:8080")
    * res1: kantan.codecs.Result[DecodeError, URL] = Success(http://localhost:8080)
    *
    * // Encoding example
    * scala> StringEncoder[URL].encode(new URL("http://localhost:8080"))
    * res2: String = http://localhost:8080
    * }}}
    */
  implicit val urlStringCodec: StringCodec[URL] =
    StringCodec.from(StringDecoder.decoder("URL")(s ⇒ new URL(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `java.net.URI`.
    *
    * {{{
    * // Decoding example
    * scala> import java.net.URI
    *
    * scala> StringDecoder[URI].decode("http://localhost:8080")
    * res1: kantan.codecs.Result[DecodeError, URI] = Success(http://localhost:8080)
    *
    * // Encoding example
    * scala> StringEncoder[URI].encode(new URI("http://localhost:8080"))
    * res2: String = http://localhost:8080
    * }}}
    */
  implicit val uriStringCodec: StringCodec[URI] =
    StringCodec.from(StringDecoder.decoder("URI")(s ⇒ new URI(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `java.io.File`.
    *
    * {{{
    * // Decoding example
    * scala> import java.io.File
    *
    * scala> StringDecoder[File].decode("/home/nrinaudo")
    * res1: kantan.codecs.Result[DecodeError, File] = Success(/home/nrinaudo)
    *
    * // Encoding example
    * scala> StringEncoder[File].encode(new File("/home/nrinaudo"))
    * res2: String = /home/nrinaudo
    * }}}
    */
  implicit val fileStringCodec: StringCodec[File] =
    StringCodec.from(StringDecoder.decoder("File")(s ⇒ new File(s.trim)))(_.toString)

  /** Defines a [[StringCodec]] instance for `java.nio.file.Path`.
    *
    * {{{
    * // Decoding example
    * scala> import java.nio.file.{Path, Paths}
    *
    * scala> StringDecoder[Path].decode("/home/nrinaudo")
    * res1: kantan.codecs.Result[DecodeError, Path] = Success(/home/nrinaudo)
    *
    * // Encoding example
    * scala> StringEncoder[Path].encode(Paths.get("/home/nrinaudo"))
    * res2: String = /home/nrinaudo
    * }}}
    */
  implicit val pathStringCodec: StringCodec[Path] =
    StringCodec.from(StringDecoder.decoder("Path")(p ⇒ Paths.get(p.trim)))(_.toString)
}