package vf.llama.database.access.many.conversation.message

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple messages at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMessages extends ManyMessagesAccess with UnconditionalView with ViewManyByIntIds[ManyMessagesAccess]

