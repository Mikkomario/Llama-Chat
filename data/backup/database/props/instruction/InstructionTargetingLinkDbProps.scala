package vf.llama.database.props.instruction

import utopia.vault.model.immutable.{DbPropertyDeclaration, Table}
import utopia.vault.model.template.HasIdProperty

object InstructionTargetingLinkDbProps
{
	// OTHER	--------------------
	
	/**
	  * @param table Table operated using this configuration
	  * @param targetIdPropName Name of the database property matching target id (default = "targetId")
	  * @param instructionIdPropName Name of the database property matching instruction id (default = 
		"instructionId")
	  * @param createdPropName Name of the database property matching created (default = "created")
	  * @param removedAfterPropName Name of the database property matching removed after (default = 
		"removedAfter")
	  * @return A model which defines all instruction targeting link database properties
	  */
	def apply(table: Table, targetIdPropName: String = "targetId", 
		instructionIdPropName: String = "instructionId", createdPropName: String = "created", 
		removedAfterPropName: String = "removedAfter"): InstructionTargetingLinkDbProps = 
		_InstructionTargetingLinkDbProps(table, targetIdPropName, instructionIdPropName, createdPropName, 
			removedAfterPropName)
	
	
	// NESTED	--------------------
	
	/**
	  * @param table Table operated using this configuration
	  * @param targetIdPropName Name of the database property matching target id (default = "targetId")
	  * @param instructionIdPropName Name of the database property matching instruction id (default = 
		"instructionId")
	  * @param createdPropName Name of the database property matching created (default = "created")
	  * @param removedAfterPropName Name of the database property matching removed after (default = 
		"removedAfter")
	  */
	private case class _InstructionTargetingLinkDbProps(table: Table, targetIdPropName: String = "targetId", 
		instructionIdPropName: String = "instructionId", createdPropName: String = "created", 
		removedAfterPropName: String = "removedAfter") 
		extends InstructionTargetingLinkDbProps
	{
		// ATTRIBUTES	--------------------
		
		override lazy val id = DbPropertyDeclaration("id", index)
		
		override lazy val targetId = DbPropertyDeclaration.from(table, targetIdPropName)
		
		override lazy val instructionId = DbPropertyDeclaration.from(table, instructionIdPropName)
		
		override lazy val created = DbPropertyDeclaration.from(table, createdPropName)
		
		override lazy val removedAfter = DbPropertyDeclaration.from(table, removedAfterPropName)
	}
}

/**
  * Common trait for classes which provide access to instruction targeting link database properties
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbProps extends HasIdProperty
{
	// ABSTRACT	--------------------
	
	/**
	  * Declaration which defines how target id shall be interacted with in the database
	  */
	def targetId: DbPropertyDeclaration
	
	/**
	  * Declaration which defines how instruction id shall be interacted with in the database
	  */
	def instructionId: DbPropertyDeclaration
	
	/**
	  * Declaration which defines how created shall be interacted with in the database
	  */
	def created: DbPropertyDeclaration
	
	/**
	  * Declaration which defines how removed after shall be interacted with in the database
	  */
	def removedAfter: DbPropertyDeclaration
}

