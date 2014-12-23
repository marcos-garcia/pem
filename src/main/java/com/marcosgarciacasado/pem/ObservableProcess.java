package com.marcosgarciacasado.pem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Manages the execution of a process through command line and reads its standard output stream to launch events
 * when it matches with an specific regular expression.
 * 
 * @author Marcos García <marcosgarciacasado@gmail.com>
 * @version 0.0.1
 * @see java.util.Observable
 * @see java.util.Observer
 * @see ProcessEvent
 * @see ProcessStatus
 */
public class ObservableProcess extends Observable implements Observer {

	/**
	 * Contains the command associated with the process. 
	 * When the process is run, this command is launched to the System.
	 */
	private String cmd;
	
	/**
	 * Represents the status of the process at the moment of the observation.
	 */
	private ProcessStatus actualStatus;
	
	/**
	 * Contains a list of events that can be launched during the execution of the process whether
	 * a line returned by the standard output matches a specific regular expression which activates it. 
	 * 
	 * @see ProcessEvent
	 */
	private ArrayList<ProcessEvent> eventList;

	/**
	 * Basic constructor. It is set to private method to force user to insert arguments using the rest
	 * of the constructors.
	 *
	 */
	@SuppressWarnings("unused")
	private ObservableProcess() {
		this.setCmd(null); // Sets the command as null
		this.eventList = new ArrayList<ProcessEvent>(); // Initialization of the event list
		this.setActualStatus(new InitialStatus()); // Marks the actual status as InitialStatus
	}

	/**
	 * Constructor of the process defined by the command passed by argument.
	 * 
	 * @param cmd Command associated to the process.
	 */
	public ObservableProcess(String cmd) {
		this.setCmd(cmd); // Sets the command with the one given as an argument
		this.eventList = new ArrayList<ProcessEvent>(); // Initialization of the event list
		this.setActualStatus(new InitialStatus()); // Marks the actual status as InitialStatus
	}

	/**
	 * Obtains the command associated to the process.
	 * 
	 * @return The command associated to the process.
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * Changes the command associated to the process with the one given through the argument.
	 * 
	 * @param cmd New command associated to the process.
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * Adds a new event to the process that could be activated during the process execution. 
	 * 
	 * @param e The event to be added.
	 * @see ProcessEvent
	 */
	public void addEvent(ProcessEvent e) {
		e.addObserver(this);
		this.eventList.add(e);
	}

	/**
	 * If it has been inserted before, removes the event passed by argument from the list of events that
	 * the process manages.
	 * 
	 * @param e The event to be removed
	 * @see ProcessEvent
	 */
	public void removeEvent(ProcessEvent e) {
		e.deleteObserver(this);
		this.eventList.remove(e);
	}

	/**
	 * Obtains the current status of the process.
	 * 
	 * @return the actualStatus
	 */
	public ProcessStatus getActualStatus() {
		return actualStatus;
	}

	/**
	 * Sets the current status of the process to the one given by argument.
	 * 
	 * @param actualStatus The new status of the process.
	 */
	public void setActualStatus(ProcessStatus actualStatus) {
		this.actualStatus = actualStatus;
	}

	/**
	 *  Establishes the status of the process to an Initial Status. 
	 */
	public void resetStatus() {
		this.setActualStatus(new InitialStatus());
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		// Se actualiza al nuevo estado activado
		this.setActualStatus(((ProcessEvent) arg0).getStatus());
		// Notificación de cambio de estado a observadores
		this.setChanged();
		synchronized (this) {
			this.notifyObservers();
		}
	}

	/**
	 * Launches the process and analyzes line by line its output to check the events.
	 * 
	 */
	public void runProcess() {
		// Initialization of variables
		Runtime rt = Runtime.getRuntime();
		Process cr = null;
		BufferedReader br = null;
		String line = null;
		try {
			// Starts the execution
			cr = rt.exec(this.getCmd());
			// Prepares the Buffered Reader of the command output.
			br = new BufferedReader(new InputStreamReader(cr.getInputStream()));
			// Reads line by line until the process is ended
			while ((line = br.readLine()) != null) {
				// Checks the line read
				this.eventMatchesLine(line);
			};
			// Waits for process termination
			cr.waitFor();
			// Marks process status as ended
			this.setActualStatus(new FinalStatus());
		} catch (InterruptedException e) {
			// InterruptedException, generally launched by getInputStream method
			e.printStackTrace();
		} catch (IOException e) {
			// IOException, generally launched by readLine method
			e.printStackTrace();
		} catch (Exception e) {
			// Rest of exceptions
			e.printStackTrace();
		}
	}

	/**
	 * Passes the line given by argument through every event registered in the process so that it
	 * activates when the line matches it's regular expression.
	 * 
	 * @param line Line to be checked.
	 */
	private void eventMatchesLine(String line) {
		// Initialization of the iterator of the list
		Iterator<ProcessEvent> i = this.eventList.iterator();
		ProcessEvent event;
		// Foreach event in the list, passes the line to be checked
		while (i.hasNext()) {
			event = i.next();
			event.activateEvent(line);
		}
		;
	};

}
