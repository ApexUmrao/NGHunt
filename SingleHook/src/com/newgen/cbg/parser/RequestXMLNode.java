package com.newgen.cbg.parser;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.jxpath.JXPathContext;

public class RequestXMLNode
{
	private String xmlNodeName;
	private String textWithinNode;
	private Set<RequestXMLAttribute> attributes = new LinkedHashSet();
	private Set<RequestXMLNode> childXMLItems = new LinkedHashSet();

	public String getXmlNodeName()
	{
		return this.xmlNodeName;
	}

	public void setXMLNodeName(String xmlNodeName)
	{
		this.xmlNodeName = xmlNodeName;
	}

	public String getTextWithinNode()
	{
		return this.textWithinNode;
	}

	public void setTextWithinNode(String textWithinNode)
	{
		this.textWithinNode = textWithinNode;
	}

	public Set<RequestXMLAttribute> getAttributes()
	{
		return this.attributes;
	}

	public void setAttributes(Set<RequestXMLAttribute> attributes)
	{
		this.attributes = attributes;
	}

	public Set<RequestXMLNode> getChildXMLItems()
	{
		return this.childXMLItems;
	}

	public void setChildConfigurationItems(Set<RequestXMLNode> childXMLItems)
	{
		this.childXMLItems = childXMLItems;
	}

	public void addToAttributes(RequestXMLAttribute resourceConfigurationAttributes)
	{
		if (this.attributes == null) {
			this.attributes = new LinkedHashSet();
		}
		this.attributes.add(resourceConfigurationAttributes);
	}

	public void addToChildConfigurationItems(RequestXMLNode resourceConfiguration)
	{
		if (this.childXMLItems == null) {
			this.childXMLItems = new LinkedHashSet();
		}
		this.childXMLItems.add(resourceConfiguration);
	}

	public Object clone() throws CloneNotSupportedException
	{
		Iterator iterator;
		RequestXMLNode clone = (RequestXMLNode)super.clone();
		if (this.attributes != null) {
			clone.setAttributes(new LinkedHashSet(this.attributes.size()));
			for (iterator = this.attributes.iterator(); iterator.hasNext(); ) {
				RequestXMLAttribute attribute = (RequestXMLAttribute)iterator.next();
				clone.addToAttributes((RequestXMLAttribute)attribute.clone());
			}
		}

		if (this.childXMLItems != null) {
			clone.setChildConfigurationItems(new LinkedHashSet(this.childXMLItems.size()));
			for (iterator = this.childXMLItems.iterator(); iterator.hasNext(); ) {
				RequestXMLNode node = (RequestXMLNode)iterator.next();
				clone.addToChildConfigurationItems((RequestXMLNode)node.clone());
			}
		}
		return clone;
	}

	public List<?> searchObjectGraph(String xpathExpression) throws Exception
	{
		return JXPathContext.newContext(this).selectNodes(xpathExpression);
	}
}