package vf.llama.database.access.many.color

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple colors at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbColors extends ManyColorsAccess with UnconditionalView with ViewManyByIntIds[ManyColorsAccess]

