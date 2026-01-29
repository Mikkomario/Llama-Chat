package vf.llama.view.app

import utopia.annex.model.response.{RequestFailure, Response}
import utopia.annex.util.RequestResultExtensions._
import utopia.firmament.component.Window
import utopia.flow.async.AsyncExtensions._
import vf.llama.util.Common._
import vf.llama.view.vc.mvp.ChatVc

/**
 * Displays the MVP view
 * @author Mikko Hilpinen
 * @since 20.07.2025, v0.1
 */
object MvpApp extends App
{
	println("Starting the app")
	ChatVc.display().waitForResult() match {
		case failure: RequestFailure =>
			println("Failed")
			log(failure.cause, "Failed to start the app")
		case Response.Success(window: Window, _, _) =>
			println("App started")
			window.closeFuture.waitFor()
	}
}
