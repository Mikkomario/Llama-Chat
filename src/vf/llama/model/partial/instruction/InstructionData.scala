package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.instruction.InstructionFactory

import java.time.Instant

object InstructionData extends FromModelFactoryWithSchema[InstructionData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Single(PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = InstructionData(valid("created").getInstant)
}

/**
  * Represents a behavioral instruction that may be given to an LLM
  * @param created Time when this instruction was added to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionData(created: Instant = Now) 
	extends InstructionFactory[InstructionData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Single("created" -> created))
	
	override def withCreated(created: Instant) = copy(created = created)
}

