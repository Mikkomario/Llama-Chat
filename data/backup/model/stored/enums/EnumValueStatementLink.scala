package vf.llama.model.stored.enums

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.enums.value.version.link.statement.DbSingleEnumValueStatementLink
import vf.llama.model.factory.enums.EnumValueStatementLinkFactoryWrapper
import vf.llama.model.partial.enums.EnumValueStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object EnumValueStatementLink 
	extends StoredFromModelFactory[EnumValueStatementLinkData, EnumValueStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = EnumValueStatementLinkData
	
	override protected def complete(model: AnyModel, data: EnumValueStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a enum value statement link that has already been stored in the database
  * @param id id of this enum value statement link in the database
  * @param data Wrapped enum value statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueStatementLink(id: Int, data: EnumValueStatementLinkData) 
	extends EnumValueStatementLinkFactoryWrapper[EnumValueStatementLinkData, EnumValueStatementLink] 
		with StatementLinkData 
		with StoredStatementLinkLike[EnumValueStatementLinkData, EnumValueStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this enum value statement link in the database
	  */
	def access = DbSingleEnumValueStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: EnumValueStatementLinkData) = copy(data = data)
}

