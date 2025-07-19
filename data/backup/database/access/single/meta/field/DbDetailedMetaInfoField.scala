package vf.llama.database.access.single.meta.field

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.DetailedMetaInfoFieldDbFactory
import vf.llama.database.storable.meta.{CustomMetaInfoDbModel, MetaInfoFieldDbModel}
import vf.llama.model.combined.meta.DetailedMetaInfoField

/**
  * Used for accessing individual detailed meta info fields
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbDetailedMetaInfoField 
	extends SingleRowModelAccess[DetailedMetaInfoField] with NonDeprecatedView[DetailedMetaInfoField] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked custom info
	  */
	protected def customInfoModel = CustomMetaInfoDbModel
	
	/**
	  * A database model (factory) used for interacting with linked fields
	  */
	private def model = MetaInfoFieldDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedMetaInfoFieldDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted detailed meta info field
	  * @return An access point to that detailed meta info field
	  */
	def apply(id: Int) = DbSingleDetailedMetaInfoField(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique detailed meta info fields.
	  * @return An access point to the detailed meta info field that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueDetailedMetaInfoFieldAccess(mergeCondition(additionalCondition))
}

