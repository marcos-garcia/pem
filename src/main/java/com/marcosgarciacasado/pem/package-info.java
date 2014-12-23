/**
 * Provides the classes necessary to launch a process and activate observer events when the stdout of
 * the process launched matches a specific regular expression.
 * <p>
 * The package involves three kind of entities: 
 * <ul>
 * <li>An observable process in charge of the management of process execution.</li>
 * <li>A process status marks the status of a process.</li>
 * <li>An observable process event which is activated by the process associated when its stdout</li>
 * </ul>
 * matches it's regular expression. Then, the event notifys it's observers about being activated. It observes the process, 
 * and it is observed by external objects.
 *
 * @version 0.0.1-SNAPSHOT
 */
package com.marcosgarciacasado.pem;