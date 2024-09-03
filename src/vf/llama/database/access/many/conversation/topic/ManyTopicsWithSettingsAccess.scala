package vf.llama.database.access.many.conversation.topic

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicWithSettingsDbFactory
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.combined.conversation.TopicWithSettings

import java.time.Instant

object ManyTopicsWithSettingsAccess extends ViewFactory[ManyTopicsWithSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyTopicsWithSettingsAccess = 
		_ManyTopicsWithSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyTopicsWithSettingsAccess(override val accessCondition: Option[Condition]) 
		extends ManyTopicsWithSettingsAccess
}

/**
  * A common trait for access points that return multiple topics with settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyTopicsWithSettingsAccess 
	extends ManyTopicsAccessLike[TopicWithSettings, ManyTopicsWithSettingsAccess] 
		with ManyRowModelAccess[TopicWithSettings]
{
	// COMPUTED	--------------------
	
	/**
	  * topic ids of the accessible topic settings
	  */
	def settingsTopicIds(implicit connection: Connection) = 
		pullColumn(settingsModel.topicId.column).map { v => v.getInt }
	
	/**
	  * persona ids of the accessible topic settings
	  */
	def settingsPersonaIds(implicit connection: Connection) = 
		pullColumn(settingsModel.personaId.column).map { v => v.getInt }
	
	/**
	  * names of the accessible topic settings
	  */
	def settingsNames(implicit connection: Connection) = 
		pullColumn(settingsModel.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible topic settings
	  */
	def settingsCreationTimes(implicit connection: Connection) = 
		pullColumn(settingsModel.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible topic settings
	  */
	def settingsDeprecationTimes(implicit connection: Connection) = 
		pullColumn(settingsModel.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the topic settings associated with this topic with settings
	  */
	protected def settingsModel = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicWithSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyTopicsWithSettingsAccess = 
		ManyTopicsWithSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted topic settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any topic settings was affected
	  */
	def settingsDeprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(settingsModel.deprecatedAfter.column, newDeprecatedAfter)
}

