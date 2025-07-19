package vf.llama.database.access.single.enums.value.version.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.EnumValueStatementLinkDbFactory
import vf.llama.database.storable.enums.EnumValueStatementLinkDbModel
import vf.llama.model.stored.enums.EnumValueStatementLink

/**
  * Used for accessing individual enum value statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValueStatementLink 
	extends SingleRowModelAccess[EnumValueStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = EnumValueStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted enum value statement link
	  * @return An access point to that enum value statement link
	  */
	def apply(id: Int) = DbSingleEnumValueStatementLink(id)
	
	/**
	  * 
		@param condition Filter condition to apply in addition to this root view's condition. Should yield unique enum
	  *  value statement links.
	  * @return An access point to the enum value statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueEnumValueStatementLinkAccess(condition)
}

