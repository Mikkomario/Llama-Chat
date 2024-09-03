package vf.llama.database.access.single.persona.settings

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.persona.PersonaSettingsDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual persona settings or similar items at a time
  * @tparam A Type of read (persona settings -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaSettingsAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona which these settings describe. 
	  * None if no persona settings (or value) was found.
	  */
	def personaId(implicit connection: Connection) = pullColumn(model.personaId.column).int
	
	/**
	  * Id of the LLM variant this persona wraps. 
	  * None if no persona settings (or value) was found.
	  */
	def llmVariantId(implicit connection: Connection) = pullColumn(model.llmVariantId.column).int
	
	/**
	  * Name assigned for this persona. 
	  * None if no persona settings (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Time when this persona settings was added to the database. 
	  * None if no persona settings (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when these settings were replaced with a new version. 
	  * None if no persona settings (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible persona settings. None if no persona settings was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

