package vf.llama.util

import utopia.flow.async.context.ThreadPool
import utopia.flow.time.TimeExtensions._
import utopia.flow.util.logging.{Logger, SysErrLogger}
import utopia.vault.database.ConnectionPool

import scala.concurrent.ExecutionContext
import scala.io.Codec

/**
 * Interface to commonly shared objects
 *
 * @author Mikko Hilpinen
 * @since 03.09.2024, v0.1
 */
object Common
{
	/**
	 * Implicit commonly used encoding
	 */
	implicit val codec: Codec = Codec.UTF8
	/**
	 * Implicit commonly used logging implementation
	 */
	implicit val log: Logger = SysErrLogger
	/**
	 * Implicit commonly used execution context
	 */
	implicit val exc: ExecutionContext = new ThreadPool("Llama-Chat", 4, 200)
	/**
	 * Implicit commonly used database connection pool
	 */
	implicit val cPool: ConnectionPool = new ConnectionPool(connectionKeepAlive = 30.seconds)
}
