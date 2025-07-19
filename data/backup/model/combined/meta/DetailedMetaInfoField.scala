package vf.llama.model.combined.meta

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.meta.MetaInfoFieldFactoryWrapper
import vf.llama.model.partial.meta.MetaInfoFieldData
import vf.llama.model.stored.meta.{CustomMetaInfo, MetaInfoField}

object DetailedMetaInfoField
{
	// OTHER	--------------------
	
	/**
	  * @param field field to wrap
	  * @param customInfo custom info to attach to this field
	  * @return Combination of the specified field and custom info
	  */
	def apply(field: MetaInfoField, customInfo: Option[CustomMetaInfo]): DetailedMetaInfoField = 
		_DetailedMetaInfoField(field, customInfo)
	
	
	// NESTED	--------------------
	
	/**
	  * @param field field to wrap
	  * @param customInfo custom info to attach to this field
	  */
	private case class _DetailedMetaInfoField(field: MetaInfoField, customInfo: Option[CustomMetaInfo]) 
		extends DetailedMetaInfoField
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: MetaInfoField) = copy(field = factory)
	}
}

/**
  * Combines a meta info field with user-defined information, if applicable
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait DetailedMetaInfoField 
	extends Extender[MetaInfoFieldData] with HasId[Int] 
		with MetaInfoFieldFactoryWrapper[MetaInfoField, DetailedMetaInfoField]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped field
	  */
	def field: MetaInfoField
	
	/**
	  * The custom info that is attached to this field
	  */
	def customInfo: Option[CustomMetaInfo]
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this field in the database
	  */
	override def id = field.id
	
	override def wrapped = field.data
	
	override protected def wrappedFactory = field
}

