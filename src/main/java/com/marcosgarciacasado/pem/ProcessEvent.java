package com.marcosgarciacasado.pem;

import java.util.Observable;

/**
 * Manages an event associated with a process. Contains the information of the regular expression which 
 * activates the event and the new status of the process at the event activation.
 * 
 * @author Marcos García <marcosgarciacasado@gmail.com>
 * @version 0.0.1
 * @see java.util.Observable
 * @see java.util.Observer
 * @see ObservableProcess
 * @see ProcessStatus
 */
public class ProcessEvent extends Observable {

	/**
	 * Regular expression which activates the event.
	 * 
	 */
	private String regexp;

	/**
	 * New status of the event.
	 * 
	 * @see ProcessStatus
	 */
	private ProcessStatus status;

	/**
	 * Basic constructor of the process event. 
	 */
	public ProcessEvent() {
	};

	/**
	 * Constructor of the process event, passing the regular expression by argument.
	 * 
	 * @param regexp Regular expression of the event.
	 */
	public ProcessEvent(String regexp) {
		this.setRegexp(regexp);
	};

	/**
	 * Constructor of the process event, passing the status by argument.
	 * 
	 * @param status New status of the process after the event.
	 */
	public ProcessEvent(ProcessStatus status) {
		this.setStatus(status);
	};

	/**
	 * Constructor of the process event, passing the regular expression and the status by argument.
	 * 
	 * @param regexp Regular expression of the event.
	 * @param status New status of the process after the event.
	 */
	public ProcessEvent(String regexp, ProcessStatus status) {
		this.setRegexp(regexp);
		this.setStatus(status);
	};

	/**
	 * Obtains the regular expression associated with this event.
	 * 
	 * @return The regular expression associated with this event.
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * Sets the regular expression of this event with the one given by argument.
	 * 
	 * @param regexp The regular expression to be associated with this event.
	 */
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}

	/**
	 * Obtains the new status of the process after the event.
	 * 
	 * @return The new status of the process after the event.
	 */
	public ProcessStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status of this event with the one given by argument.
	 * 
	 * @param status The new status of this event.
	 */
	public void setStatus(ProcessStatus status) {
		this.status = status;
	}

	/**
	 * Compares the String given by argument with the regular expression associated with this event.
	 * 
	 * @param s The string to be compared.
	 * @return true if the string matches the regular expression of the event.
	 */
	public boolean matches(String s) {
		return s.matches(this.getRegexp());
	}

	/**
	 * Informs the observers about the activation of the event.
	 * 
	 * @see java.util.Observable
	 */
	public void activateEvent() {
		// Notifies the observers the activation of the Event 
		this.setChanged();
		// The event must be synchronized during the process of notification
		synchronized (this) {
			this.notifyObservers();
		}
	}

	/**
	 * Conditionally activates the event, whether the string passed by argument matches it's regular
	 * expression.
	 * 
	 * @param line String to be checked.
	 */
	public void activateEvent(String line) {
		// Comparison of the string
		if (this.matches(line)) {
			// Activation of the event when the comparison is successful
			this.activateEvent();
		}
	};
}
