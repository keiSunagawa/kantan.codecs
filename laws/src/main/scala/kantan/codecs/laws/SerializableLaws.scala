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

package kantan.codecs.laws

import scala.util.Try

/** Laws for serializable type class instances. */
trait SerializableLaws[A] {
  def value: A

  def serializable(): Boolean = {
    try {SerializableTools.serialize(value)}
    catch {
      case e: Exception ⇒ e.printStackTrace()
    }
    Try(SerializableTools.serialize(value)).isSuccess
  }
}

object SerializableLaws {
  implicit def apply[A](implicit a: A): SerializableLaws[A] = new SerializableLaws[A] {
    override def value = a
  }
}
