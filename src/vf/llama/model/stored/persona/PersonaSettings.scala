package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.settings.DbSinglePersonaSettings
import vf.llama.model.factory.persona.PersonaSettingsFactoryWrapper
import vf.llama.model.partial.persona.PersonaSettingsData

object PersonaSettings extends StoredFromModelFactory[PersonaSettingsData, PersonaSettings]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaSettingsData
	
	override protected def complete(model: AnyModel, data: PersonaSettingsData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona settings that has already been stored in the database
  * @param id id of this persona settings in the database
  * @param data Wrapped persona settings data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaSettings(id: Int, data: PersonaSettingsData) 
	extends StoredModelConvertible[PersonaSettingsData] with FromIdFactory[Int, PersonaSettings] 
		with PersonaSettingsFactoryWrapper[PersonaSettingsData, PersonaSettings]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona settings in the database
	  */
	def access = DbSinglePersonaSettings(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaSettingsData) = copy(data = data)
}

