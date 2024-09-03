package vf.llama.database.access.single.conversation.topic.settings

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicSettingsDbFactory
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.stored.conversation.TopicSettings

import java.time.Instant

object UniqueTopicSettingsAccess extends ViewFactory[UniqueTopicSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueTopicSettingsAccess = 
		_UniqueTopicSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueTopicSettingsAccess(override val accessCondition: Option[Condition]) 
		extends UniqueTopicSettingsAccess
}

/**
  * A common trait for access points that return individual and distinct topic settings.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicSettingsAccess 
	extends SingleChronoRowModelAccess[TopicSettings, UniqueTopicSettingsAccess] 
		with DistinctModelAccess[TopicSettings, Option[TopicSettings], Value] 
		with NullDeprecatableView[UniqueTopicSettingsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the topic these settings describe. 
	  * None if no topic settings (or value) was found.
	  */
	def topicId(implicit connection: Connection) = pullColumn(model.topicId.column).int
	
	/**
	  * Id of the persona with whom this topic is discussed. 
	  * None if no topic settings (or value) was found.
	  */
	def personaId(implicit connection: Connection) = pullColumn(model.personaId.column).int
	
	/**
	  * Name assigned to this topic. 
	  * None if no topic settings (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Time when this topic settings was added to the database. 
	  * None if no topic settings (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when these settings were replaced with another version. 
	  * None if no topic settings (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible topic settings. None if no topic settings was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueTopicSettingsAccess = UniqueTopicSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted topic settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any topic settings was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

