package vf.llama.database.access.single.persona

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.Persona

/**
  * An access point to individual personas, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersona(id: Int) extends UniquePersonaAccess with SingleIntIdModelAccess[Persona]

