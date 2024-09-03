package vf.llama.database.storable.instruction

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.instruction.InstructionVersionFactory
import vf.llama.model.partial.instruction.InstructionVersionData
import vf.llama.model.stored.instruction.InstructionVersion

import java.time.Instant

/**
  * Used for constructing InstructionVersionDbModel instances and for inserting instruction versions to
  *  the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object InstructionVersionDbModel 
	extends StorableFactory[InstructionVersionDbModel, InstructionVersion, InstructionVersionData] 
		with FromIdFactory[Int, InstructionVersionDbModel] with HasIdProperty 
		with InstructionVersionFactory[InstructionVersionDbModel] 
		with DeprecatableAfter[InstructionVersionDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with enum value ids
	  */
	lazy val enumValueId = property("enumValueId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.instructionVersion
	
	override def apply(data: InstructionVersionData) = 
		apply(None, data.name, data.enumValueId, Some(data.created), data.deprecatedAfter)
	
	/**
	  * @param created Time when this instruction version was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with a new one
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param enumValueId Id of the enumeration value selected by applying this instruction. None if
	  *  not enumeration-based.
	  * @return A model containing only the specified enum value id
	  */
	override def withEnumValueId(enumValueId: Int) = apply(enumValueId = Some(enumValueId))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name of this instruction
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	override protected def complete(id: Value, data: InstructionVersionData) = InstructionVersion(id.getInt, 
		data)
}

/**
  * Used for interacting with InstructionVersions in the database
  * @param id instruction version database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionVersionDbModel(id: Option[Int] = None, name: String = "", 
	enumValueId: Option[Int] = None, created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, InstructionVersionDbModel] 
		with InstructionVersionFactory[InstructionVersionDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = InstructionVersionDbModel.table
	
	override def valueProperties = 
		Vector(InstructionVersionDbModel.id.name -> id, InstructionVersionDbModel.name.name -> name, 
			InstructionVersionDbModel.enumValueId.name -> enumValueId, 
			InstructionVersionDbModel.created.name -> created, 
			InstructionVersionDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param created Time when this instruction version was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with a new one
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param enumValueId Id of the enumeration value selected by applying this instruction. None if
	  *  not enumeration-based.
	  * @return A new copy of this model with the specified enum value id
	  */
	override def withEnumValueId(enumValueId: Int) = copy(enumValueId = Some(enumValueId))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name of this instruction
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
}

