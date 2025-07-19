package vf.llama.database.storable.enums

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.enums.EnumFactory
import vf.llama.model.partial.enums.EnumData
import vf.llama.model.stored.enums.Enum

import java.time.Instant

/**
  * Used for constructing EnumDbModel instances and for inserting enums to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumDbModel 
	extends StorableFactory[EnumDbModel, Enum, EnumData] with FromIdFactory[Int, EnumDbModel] 
		with HasIdProperty with EnumFactory[EnumDbModel] with NullDeprecatable[EnumDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
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
	
	override def table = LlamaChatTables.enums
	
	override def apply(data: EnumData) = apply(None, data.name, Some(data.created), data.archivedAfter)
	
	/**
	  * @param archivedAfter Time when this enumeration was archived
	  * @return A model containing only the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = apply(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this enum was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withArchivedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name of this enumeration (mutable)
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	override protected def complete(id: Value, data: EnumData) = Enum(id.getInt, data)
}

/**
  * Used for interacting with Enums in the database
  * @param id enum database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumDbModel(id: Option[Int] = None, name: String = "", created: Option[Instant] = None, 
	archivedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, EnumDbModel] 
		with EnumFactory[EnumDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = EnumDbModel.table
	
	override def valueProperties = 
		Vector(EnumDbModel.id.name -> id, EnumDbModel.name.name -> name, EnumDbModel.created.name -> created, 
			EnumDbModel.archivedAfter.name -> archivedAfter)
	
	/**
	  * @param archivedAfter Time when this enumeration was archived
	  * @return A new copy of this model with the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this enum was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name of this enumeration (mutable)
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
}

