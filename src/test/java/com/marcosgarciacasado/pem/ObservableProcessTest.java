package com.marcosgarciacasado.pem;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.marcosgarciacasado.pem.FinalStatus;
import com.marcosgarciacasado.pem.InitialStatus;
import com.marcosgarciacasado.pem.IntermediateStatus;
import com.marcosgarciacasado.pem.ObservableProcess;
import com.marcosgarciacasado.pem.ProcessEvent;
import com.marcosgarciacasado.pem.ProcessStatus;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Basic unit test for ObservableProcess.
 */
public class ObservableProcessTest extends TestCase implements Observer{
	
	/**
	 * List of observations made.
	 * 
	 */	
	private ArrayList<String> observations;
	
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public ObservableProcessTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ObservableProcessTest.class);
	}

	/**
	 * This test executes a ping command to www.bayesforecast.com. It changes status 
	 * when starts each state of the process: 
	 * The ping test, the statistics report and the time report
	 */
	public void testObservableProcess() {
		String command = "ping www.github.com";

		this.observations = new ArrayList<String>();
		
		System.out.println("Creating status...");
		ProcessStatus ps1 = new IntermediateStatus();
		ProcessStatus ps2 = new IntermediateStatus();
		ProcessStatus ps3 = new IntermediateStatus();

		System.out.println("Creating events...");
		ProcessEvent pe1 = new ProcessEvent("Haciendo.*", ps1);
		ProcessEvent pe2 = new ProcessEvent("Estad.*", ps2);
		ProcessEvent pe3 = new ProcessEvent("Tiempos.*", ps3);
		pe1.addObserver(this);
		pe2.addObserver(this);
		pe3.addObserver(this);

		System.out.println("Creating process...");
		ObservableProcess op = new ObservableProcess(command);

		System.out.println("Suscribing events...");
		op.addEvent(pe1);
		op.addEvent(pe2);
		op.addEvent(pe3);

		System.out.println("Asserting initial status...");
		assertEquals(InitialStatus.class, op.getActualStatus().getClass());

		System.out.println("Asserting initial status...");

		System.out.println("Launching process...");
		op.runProcess();

		System.out.println("Asserting final status...");
		assertEquals(FinalStatus.class, op.getActualStatus().getClass());

		System.out.println("Asserting observed status..."+observations.size());
		assertEquals(observations.size(), 3);
	}

	/**
	 * When the test receives a notification from one of the Observable classes, this code
	 * is being executed. It adds a new observation to the observations' list, with the activated regexp.
	 * 
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		observations.add(((ProcessEvent)arg0).getRegexp());
	}
}
