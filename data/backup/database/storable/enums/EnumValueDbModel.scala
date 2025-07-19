package vf.llama.database.storable.enums

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.enums.EnumValueFactory
import vf.llama.model.partial.enums.EnumValueData
import vf.llama.model.stored.enums.EnumValue

import java.time.Instant

/**
  * Used for constructing EnumValueDbModel instances and for inserting enum values to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumValueDbModel 
	extends StorableFactory[EnumValueDbModel, EnumValue, EnumValueData] 
		with FromIdFactory[Int, EnumValueDbModel] with HasIdProperty with EnumValueFactory[EnumValueDbModel] 
		with NullDeprecatable[EnumValueDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with enum ids
	  */
	lazy val enumId = property("enumId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with archive times
	  */
	lazy val archivedAfter = property("archivedAfter")
	
	override val deprecationAttName = "archivedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.enumValue
	
	override def apply(data: EnumValueData) = 
		apply(None, Some(data.enumId), Some(data.created), data.archivedAfter)
	
	/**
	  * @param archivedAfter Time when this enumeration value was archived
	  * @return A model containing only the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = apply(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this enum value was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withArchivedAfter(deprecationTime)
	
	/**
	  * @param enumId Id of the enumeration where this value appears
	  * @return A model containing only the specified enum id
	  */
	override def withEnumId(enumId: Int) = apply(enumId = Some(enumId))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: EnumValueData) = EnumValue(id.getInt, data)
}

/**
  * Used for interacting with EnumValues in the database
  * @param id enum value database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueDbModel(id: Option[Int] = None, enumId: Option[Int] = None, 
	created: Option[Instant] = None, archivedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, EnumValueDbModel] 
		with EnumValueFactory[EnumValueDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = EnumValueDbModel.table
	
	override def valueProperties = 
		Vector(EnumValueDbModel.id.name -> id, EnumValueDbModel.enumId.name -> enumId, 
			EnumValueDbModel.created.name -> created, EnumValueDbModel.archivedAfter.name -> archivedAfter)
	
	/**
	  * @param archivedAfter Time when this enumeration value was archived
	  * @return A new copy of this model with the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this enum value was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param enumId Id of the enumeration where this value appears
	  * @return A new copy of this model with the specified enum id
	  */
	override def withEnumId(enumId: Int) = copy(enumId = Some(enumId))
	
	override def withId(id: Int) = copy(id = Some(id))
}

