package vf.llama.model.factory.meta

import vf.llama.model.enumeration.AutomatedMetaInfo

import java.time.Instant

/**
  * Common trait for meta info field-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoFieldFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param archivedAfter New archived after to assign
	  * @return Copy of this item with the specified archived after
	  */
	def withArchivedAfter(archivedAfter: Instant): A
	
	/**
	  * @param automatedContent New automated content to assign
	  * @return Copy of this item with the specified automated content
	  */
	def withAutomatedContent(automatedContent: AutomatedMetaInfo): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param customInfoId New custom info id to assign
	  * @return Copy of this item with the specified custom info id
	  */
	def withCustomInfoId(customInfoId: Int): A
}

