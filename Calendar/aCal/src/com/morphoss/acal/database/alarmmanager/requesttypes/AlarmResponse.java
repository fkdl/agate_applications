package com.morphoss.acal.database.alarmmanager.requesttypes;

public abstract class AlarmResponse<E> {
	/**
	 * Returns the response object generated by an AlarmRequest.
	 * @return
	 */
	public abstract E result();
	
	protected AlarmResponse(Exception e) { this.error = e; }
	protected AlarmResponse() {}
	
	public boolean wasSuccessful() {
		return error==null;
	}
	
	public Exception getError() {
		return this.error;
	}
	protected Exception error = null;
}
