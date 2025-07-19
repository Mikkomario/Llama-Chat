package vf.llama.database.props.meta

import utopia.vault.model.immutable.{DbPropertyDeclaration, Table}
import utopia.vault.model.template.HasIdProperty

object MetaInfoAvailabilityDbProps
{
	// OTHER	--------------------
	
	/**
	  * @param table Table operated using this configuration
	  * @param fieldIdPropName Name of the database property matching field id (default = "fieldId")
	  * @param contextIdPropName Name of the database property matching context id (default = "contextId")
	  * @param createdPropName Name of the database property matching created (default = "created")
	  * @return A model which defines all meta info availability database properties
	  */
	def apply(table: Table, fieldIdPropName: String = "fieldId", contextIdPropName: String = "contextId", 
		createdPropName: String = "created"): MetaInfoAvailabilityDbProps = 
		_MetaInfoAvailabilityDbProps(table, fieldIdPropName, contextIdPropName, createdPropName)
	
	
	// NESTED	--------------------
	
	/**
	  * @param table Table operated using this configuration
	  * @param fieldIdPropName Name of the database property matching field id (default = "fieldId")
	  * @param contextIdPropName Name of the database property matching context id (default = "contextId")
	  * @param createdPropName Name of the database property matching created (default = "created")
	  */
	private case class _MetaInfoAvailabilityDbProps(table: Table, fieldIdPropName: String = "fieldId", 
		contextIdPropName: String = "contextId", createdPropName: String = "created") 
		extends MetaInfoAvailabilityDbProps
	{
		// ATTRIBUTES	--------------------
		
		override lazy val id = DbPropertyDeclaration("id", index)
		
		override lazy val fieldId = DbPropertyDeclaration.from(table, fieldIdPropName)
		
		override lazy val contextId = DbPropertyDeclaration.from(table, contextIdPropName)
		
		override lazy val created = DbPropertyDeclaration.from(table, createdPropName)
	}
}

/**
  * Common trait for classes which provide access to meta info availability database properties
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbProps extends HasIdProperty
{
	// ABSTRACT	--------------------
	
	/**
	  * Declaration which defines how field id shall be interacted with in the database
	  */
	def fieldId: DbPropertyDeclaration
	
	/**
	  * Declaration which defines how context id shall be interacted with in the database
	  */
	def contextId: DbPropertyDeclaration
	
	/**
	  * Declaration which defines how created shall be interacted with in the database
	  */
	def created: DbPropertyDeclaration
}

