package vf.llama.database.access.single.meta.field

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.MetaInfoFieldDbFactory
import vf.llama.database.storable.meta.MetaInfoFieldDbModel
import vf.llama.model.stored.meta.MetaInfoField

/**
  * Used for accessing individual meta info fields
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMetaInfoField 
	extends SingleRowModelAccess[MetaInfoField] with NonDeprecatedView[MetaInfoField] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = MetaInfoFieldDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MetaInfoFieldDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted meta info field
	  * @return An access point to that meta info field
	  */
	def apply(id: Int) = DbSingleMetaInfoField(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique meta info fields.
	  * @return An access point to the meta info field that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueMetaInfoFieldAccess(mergeCondition(additionalCondition))
}

