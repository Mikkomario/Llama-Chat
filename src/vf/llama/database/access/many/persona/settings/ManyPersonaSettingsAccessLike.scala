package vf.llama.database.access.many.persona.settings

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.persona.PersonaSettingsDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple persona settings or similar instances at a time
  * @tparam A Type of read (persona settings -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaSettingsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona settings
	  */
	def personaIds(implicit connection: Connection) = pullColumn(model.personaId.column).map { v => v.getInt }
	
	/**
	  * llm variant ids of the accessible persona settings
	  */
	def llmVariantIds(implicit connection: Connection) = 
		pullColumn(model.llmVariantId.column).map { v => v.getInt }
	
	/**
	  * names of the accessible persona settings
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible persona settings
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible persona settings
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible persona settings
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaSettingsDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted persona settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any persona settings was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param personaId persona id to target
	  * @return Copy of this access point that only includes persona settings with the specified persona id
	  */
	def forPersona(personaId: Int) = filter(model.personaId.column <=> personaId)
	
	/**
	  * @param personaIds Targeted persona ids
	  * @return Copy of this access point that only includes persona settings where persona id is within the
	  *  specified value set
	  */
	def forPersonas(personaIds: IterableOnce[Int]) = filter(model
		.personaId.column.in(IntSet.from(personaIds)))
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes persona settings with the specified name
	  */
	def named(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param llmVariantId llm variant id to target
	  * @return Copy of this access point that only includes persona settings 
		with the specified llm variant id
	  */
	def usingLlmVariant(llmVariantId: Int) = filter(model.llmVariantId.column <=> llmVariantId)
	
	/**
	  * @param llmVariantIds Targeted llm variant ids
	  * @return Copy of this access point that only includes persona settings where llm variant id is within
	  *  the specified value set
	  */
	def usingLlmVariants(llmVariantIds: IterableOnce[Int]) = 
		filter(model.llmVariantId.column.in(IntSet.from(llmVariantIds)))
	
	/**
	  * @param names Targeted names
	  * 
		@return Copy of this access point that only includes persona settings where name is within the specified
	  *  value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
}

