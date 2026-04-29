package org.dummy.insecure.framework;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

@Slf4j
public class VulnerableTaskHolder implements Serializable {

	private static final long serialVersionUID = 2;

	private String taskName;
	private String taskAction;
	private LocalDateTime requestedExecutionTime;
	
	public VulnerableTaskHolder(String taskName, String taskAction) {
		super();
		this.taskName = taskName;
		this.taskAction = taskAction;
		this.requestedExecutionTime = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "VulnerableTaskHolder [taskName=" + taskName + ", taskAction=" + taskAction + ", requestedExecutionTime="
				+ requestedExecutionTime + "]";
	}

	/**
	 * Execute a task when de-serializing a saved or received object.
	 * @author stupid develop
	 */
	private void readObject( ObjectInputStream stream ) throws Exception {
        //unserialize data so taskName and taskAction are available
		stream.defaultReadObject();
		
		//do something with the data
		log.info("restoring task: {}", taskName);
		log.info("restoring time: {}", requestedExecutionTime);
		
		if (requestedExecutionTime!=null && 
				(requestedExecutionTime.isBefore(LocalDateTime.now().minusMinutes(10))
				|| requestedExecutionTime.isAfter(LocalDateTime.now()))) {
			//do nothing is the time is not within 10 minutes after the object has been created
			log.debug(this.toString());
			throw new IllegalArgumentException("outdated");
		}
		
		// Do not execute untrusted data
		taskAction = taskAction.replaceAll("[^a-zA-Z0-9]", ""); // Whitelist only safe chars
		if (taskAction.equals("sleep") || taskAction.equals("ping")) {
		    // safe usage
		    Process p = Runtime.getRuntime().exec(taskAction);
		} else {
		    throw new SecurityException("Disallowed command");
		}
       
    }
	
}