package com.newgen.cbg.parser;

public class RequestXMLAttribute
{
	private String attributeName;
	private String attributeValue;
	
	public String getAttributeName()
	{
		return this.attributeName;
	}

	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	public String getAttributeValue()
	{
		return this.attributeValue;
	}

	public void setAttributeValue(String attributeValue)
	{
		this.attributeValue = attributeValue;
	}
	
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
