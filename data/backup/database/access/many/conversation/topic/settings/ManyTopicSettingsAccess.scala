package vf.llama.database.access.many.conversation.topic.settings

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicSettingsDbFactory
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.stored.conversation.TopicSettings

import java.time.Instant

object ManyTopicSettingsAccess extends ViewFactory[ManyTopicSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override
		 def apply(condition: Condition): ManyTopicSettingsAccess = _ManyTopicSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyTopicSettingsAccess(override val accessCondition: Option[Condition]) 
		extends ManyTopicSettingsAccess
}

/**
  * A common trait for access points which target multiple topic settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyTopicSettingsAccess 
	extends ManyRowModelAccess[TopicSettings] 
		with ChronoRowFactoryView[TopicSettings, ManyTopicSettingsAccess] 
		with NullDeprecatableView[ManyTopicSettingsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * topic ids of the accessible topic settings
	  */
	def topicIds(implicit connection: Connection) = pullColumn(model.topicId.column).map { v => v.getInt }
	
	/**
	  * persona ids of the accessible topic settings
	  */
	def personaIds(implicit connection: Connection) = pullColumn(model.personaId.column).map { v => v.getInt }
	
	/**
	  * names of the accessible topic settings
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible topic settings
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible topic settings
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible topic settings
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyTopicSettingsAccess = ManyTopicSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted topic settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any topic settings was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param topicId topic id to target
	  * @return Copy of this access point that only includes topic settings with the specified topic id
	  */
	def forTopic(topicId: Int) = filter(model.topicId.column <=> topicId)
	
	/**
	  * @param topicIds Targeted topic ids
	  * @return Copy of this access point that only includes topic settings where topic id is within the
	  *  specified value set
	  */
	def forTopics(topicIds: IterableOnce[Int]) = filter(model.topicId.column.in(IntSet.from(topicIds)))
	
	/**
	  * @param personaId persona id to target
	  * @return Copy of this access point that only includes topic settings with the specified persona id
	  */
	def usingPersona(personaId: Int) = filter(model.personaId.column <=> personaId)
	
	/**
	  * @param personaIds Targeted persona ids
	  * @return Copy of this access point that only includes topic settings where persona id is within the
	  *  specified value set
	  */
	def usingPersonas(personaIds: IterableOnce[Int]) = filter(model
		.personaId.column.in(IntSet.from(personaIds)))
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes topic settings with the specified name
	  */
	def withName(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param names Targeted names
	  * @return Copy of this access point that only includes topic settings where name is within the specified
	  *  value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
}

