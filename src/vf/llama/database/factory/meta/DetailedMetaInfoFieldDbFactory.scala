package vf.llama.database.factory.meta

import utopia.vault.nosql.factory.row.linked.PossiblyCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.meta.DetailedMetaInfoField
import vf.llama.model.stored.meta.{CustomMetaInfo, MetaInfoField}

/**
  * Used for reading detailed meta info fields from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DetailedMetaInfoFieldDbFactory 
	extends PossiblyCombiningFactory[DetailedMetaInfoField, MetaInfoField, CustomMetaInfo] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = CustomMetaInfoDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = MetaInfoFieldDbFactory
	
	/**
	  * @param field field to wrap
	  * @param customInfo custom info to attach to this field
	  */
	override def apply(field: MetaInfoField, customInfo: Option[CustomMetaInfo]) = 
		DetailedMetaInfoField(field, customInfo)
}

