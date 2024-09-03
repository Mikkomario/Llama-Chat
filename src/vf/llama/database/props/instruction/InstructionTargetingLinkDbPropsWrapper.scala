package vf.llama.database.props.instruction

/**
  * 
	Common trait for interfaces that provide access to instruction targeting link database properties by wrapping
  *  a InstructionTargetingLinkDbProps
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbPropsWrapper extends InstructionTargetingLinkDbProps
{
	// ABSTRACT	--------------------
	
	/**
	  * The wrapped instruction targeting link database properties
	  */
	protected def instructionTargetingLinkDbProps: InstructionTargetingLinkDbProps
	
	
	// IMPLEMENTED	--------------------
	
	override def created = instructionTargetingLinkDbProps.created
	
	override def id = instructionTargetingLinkDbProps.id
	
	override def instructionId = instructionTargetingLinkDbProps.instructionId
	
	override def removedAfter = instructionTargetingLinkDbProps.removedAfter
	
	override def targetId = instructionTargetingLinkDbProps.targetId
}

