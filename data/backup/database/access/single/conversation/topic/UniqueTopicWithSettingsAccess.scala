package vf.llama.database.access.single.conversation.topic

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicWithSettingsDbFactory
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.combined.conversation.TopicWithSettings

import java.time.Instant

object UniqueTopicWithSettingsAccess extends ViewFactory[UniqueTopicWithSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueTopicWithSettingsAccess = 
		_UniqueTopicWithSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueTopicWithSettingsAccess(override val accessCondition: Option[Condition]) 
		extends UniqueTopicWithSettingsAccess
}

/**
  * A common trait for access points that return distinct topics with settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicWithSettingsAccess 
	extends UniqueTopicAccessLike[TopicWithSettings, UniqueTopicWithSettingsAccess] 
		with SingleChronoRowModelAccess[TopicWithSettings, UniqueTopicWithSettingsAccess] 
		with NullDeprecatableView[UniqueTopicWithSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the topic these settings describe. 
	  * None if no topic settings (or value) was found.
	  */
	def settingsTopicId(implicit connection: Connection) = pullColumn(settingsModel.topicId.column).int
	
	/**
	  * Id of the persona with whom this topic is discussed. 
	  * None if no topic settings (or value) was found.
	  */
	def settingsPersonaId(implicit connection: Connection) = pullColumn(settingsModel.personaId.column).int
	
	/**
	  * Name assigned to this topic. 
	  * None if no topic settings (or value) was found.
	  */
	def settingsName(implicit connection: Connection) = pullColumn(settingsModel.name.column).getString
	
	/**
	  * Time when this topic settings was added to the database. 
	  * None if no topic settings (or value) was found.
	  */
	def settingsCreated(implicit connection: Connection) = pullColumn(settingsModel.created.column).instant
	
	/**
	  * Time when these settings were replaced with another version. 
	  * None if no topic settings (or value) was found.
	  */
	def settingsDeprecatedAfter(implicit connection: Connection) = 
		pullColumn(settingsModel.deprecatedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked settings
	  */
	protected def settingsModel = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicWithSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueTopicWithSettingsAccess = 
		UniqueTopicWithSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted topic settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any topic settings was affected
	  */
	def settingsDeprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(settingsModel.deprecatedAfter.column, newDeprecatedAfter)
}

