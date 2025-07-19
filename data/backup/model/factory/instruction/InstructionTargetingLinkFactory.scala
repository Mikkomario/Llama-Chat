package vf.llama.model.factory.instruction

import java.time.Instant

/**
  * Common trait for instruction targeting link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param instructionId New instruction id to assign
	  * @return Copy of this item with the specified instruction id
	  */
	def withInstructionId(instructionId: Int): A
	
	/**
	  * @param removedAfter New removed after to assign
	  * @return Copy of this item with the specified removed after
	  */
	def withRemovedAfter(removedAfter: Instant): A
	
	/**
	  * @param targetId New target id to assign
	  * @return Copy of this item with the specified target id
	  */
	def withTargetId(targetId: Int): A
}

