package vf.llama.database.access.many.instruction.link.topic

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.instruction.TopicInstructionLink

/**
  * The root access point when targeting multiple topic instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicInstructionLinks 
	extends ManyTopicInstructionLinksAccess with NonDeprecatedView[TopicInstructionLink] 
		with ViewManyByIntIds[ManyTopicInstructionLinksAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) topic instruction links
	  */
	def includingHistory = DbTopicInstructionLinksIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbTopicInstructionLinksIncludingHistory extends ManyTopicInstructionLinksAccess 
		with UnconditionalView
}

