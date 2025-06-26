
package com.newgen.repair;

@SuppressWarnings("serial")
public class RetryException extends Exception 
{
	public RetryException(String msg) 
	{
		super(msg);
	}
}
