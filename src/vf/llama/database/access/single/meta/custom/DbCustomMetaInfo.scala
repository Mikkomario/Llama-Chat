package vf.llama.database.access.single.meta.custom

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.CustomMetaInfoDbFactory
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.stored.meta.CustomMetaInfo

/**
  * Used for accessing individual custom meta infos
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbCustomMetaInfo 
	extends SingleRowModelAccess[CustomMetaInfo] with NonDeprecatedView[CustomMetaInfo] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = CustomMetaInfoDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted custom meta info
	  * @return An access point to that custom meta info
	  */
	def apply(id: Int) = DbSingleCustomMetaInfo(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique custom meta infos.
	  * @return An access point to the custom meta info that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueCustomMetaInfoAccess(mergeCondition(additionalCondition))
}

