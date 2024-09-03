package vf.llama.database.storable.llm

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.llm.LlmFactory
import vf.llama.model.partial.llm.LlmData
import vf.llama.model.stored.llm.Llm

import java.time.Instant

/**
  * Used for constructing LlmDbModel instances and for inserting llms to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlmDbModel 
	extends StorableFactory[LlmDbModel, Llm, LlmData] with FromIdFactory[Int, LlmDbModel] with HasIdProperty 
		with LlmFactory[LlmDbModel] with NullDeprecatable[LlmDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with aliases
	  */
	lazy val alias = property("alias")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with removed afters
	  */
	lazy val removedAfter = property("removedAfter")
	
	override val deprecationAttName = "removedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.llm
	
	override def apply(data: LlmData) = apply(None, data.name, data.alias, Some(data.created), 
		data.removedAfter)
	
	/**
	  * @param alias Alias given to this LLM
	  * @return A model containing only the specified alias
	  */
	override def withAlias(alias: String) = apply(alias = alias)
	
	/**
	  * @param created Time when this llm was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name of this LLM in Ollama
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	/**
	  * @param removedAfter Time when this LLM was removed from this application
	  * @return A model containing only the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
	
	override protected def complete(id: Value, data: LlmData) = Llm(id.getInt, data)
}

/**
  * Used for interacting with Llms in the database
  * @param id llm database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class LlmDbModel(id: Option[Int] = None, name: String = "", alias: String = "", 
	created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, LlmDbModel] with LlmFactory[LlmDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = LlmDbModel.table
	
	override def valueProperties = 
		Vector(LlmDbModel.id.name -> id, LlmDbModel.name.name -> name, LlmDbModel.alias.name -> alias, 
			LlmDbModel.created.name -> created, LlmDbModel.removedAfter.name -> removedAfter)
	
	/**
	  * @param alias Alias given to this LLM
	  * @return A new copy of this model with the specified alias
	  */
	override def withAlias(alias: String) = copy(alias = alias)
	
	/**
	  * @param created Time when this llm was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name of this LLM in Ollama
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
	
	/**
	  * @param removedAfter Time when this LLM was removed from this application
	  * @return A new copy of this model with the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

