package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.conversation.MemoryDbModel
import vf.llama.model.partial.conversation.MemoryData
import vf.llama.model.stored.conversation.Memory

/**
  * Used for reading memory data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MemoryDbFactory extends FromValidatedRowModelFactory[Memory] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = MemoryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		Memory(valid(this.model.id.name).getInt, MemoryData(valid(this.model.messageId.name).getInt, 
			valid(this.model.archivedAfter.name).instant))
}

