package vf.llama.database.factory.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.sql.OrderBy
import vf.llama.database.factory.statement.StatementLinkDbFactoryLike
import vf.llama.database.storable.conversation.MessageStatementLinkDbModel
import vf.llama.model.partial.conversation.MessageStatementLinkData
import vf.llama.model.stored.conversation.MessageStatementLink

/**
  * Used for reading message statement link data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MessageStatementLinkDbFactory extends StatementLinkDbFactoryLike[MessageStatementLink]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	override def dbProps = MessageStatementLinkDbModel
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = dbProps.table
	
	/**
	  * @param model Model from which additional data may be read
	  * @param id Id to assign to the read/parsed statement link
	  * @param statementId statement id to assign to the new statement link
	  * @param parentId parent id to assign to the new statement link
	  * @param orderIndex order index to assign to the new statement link
	  */
	override protected def apply(model: AnyModel, id: Int, statementId: Int, parentId: Int, orderIndex: Int) =
		MessageStatementLink(id, MessageStatementLinkData(parentId, statementId, orderIndex))
}

