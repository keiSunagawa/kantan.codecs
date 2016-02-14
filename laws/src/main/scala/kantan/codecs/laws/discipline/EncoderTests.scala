package kantan.codecs.laws.discipline

import kantan.codecs.laws.CodecValue.{IllegalValue, LegalValue}
import kantan.codecs.laws.{CodecValue, DecoderLaws, EncoderLaws}
import org.scalacheck.Arbitrary
import org.scalacheck.Prop._
import org.typelevel.discipline.Laws

trait EncoderTests[E, D] extends Laws {
  def laws: EncoderLaws[E, D]

  implicit def arbLegal: Arbitrary[LegalValue[E, D]]
  implicit val arbD: Arbitrary[D] = Arbitrary(arbLegal.arbitrary.map(_.decoded))

  private def rules[A: Arbitrary, B: Arbitrary](name: String)(implicit arbED: Arbitrary[CodecValue[E, D]]): RuleSet = new DefaultRuleSet(
    name = name,
    parent = None,
    "encode"                → forAll(laws.encode _),
    "contramap identity"    → forAll(laws.contramapIdentity _),
    "contramap composition" → forAll(laws.contramapComposition[A, B] _)
  )

  def bijectiveEncoder[A: Arbitrary, B: Arbitrary]: RuleSet =
    rules("bijective encoder")(implicitly[Arbitrary[A]], implicitly[Arbitrary[B]], Arbitrary(arbLegal.arbitrary))

  def encoder[A: Arbitrary, B: Arbitrary](implicit ai: Arbitrary[IllegalValue[E, D]]): RuleSet = rules[A, B]("encoder")
}

object EncoderTests {
  def apply[E, D](implicit l: EncoderLaws[E, D],
                  al: Arbitrary[LegalValue[E, D]]): EncoderTests[E, D] = new EncoderTests[E, D] {
    override val laws = l
    override val arbLegal = al
  }
}