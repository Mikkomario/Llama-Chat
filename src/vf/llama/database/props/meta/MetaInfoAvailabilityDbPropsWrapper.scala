package vf.llama.database.props.meta

/**
  * Common trait for interfaces that provide access to meta info availability database properties by wrapping
  *  a MetaInfoAvailabilityDbProps
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbPropsWrapper extends MetaInfoAvailabilityDbProps
{
	// ABSTRACT	--------------------
	
	/**
	  * The wrapped meta info availability database properties
	  */
	protected def metaInfoAvailabilityDbProps: MetaInfoAvailabilityDbProps
	
	
	// IMPLEMENTED	--------------------
	
	override def contextId = metaInfoAvailabilityDbProps.contextId
	
	override def created = metaInfoAvailabilityDbProps.created
	
	override def fieldId = metaInfoAvailabilityDbProps.fieldId
	
	override def id = metaInfoAvailabilityDbProps.id
}

