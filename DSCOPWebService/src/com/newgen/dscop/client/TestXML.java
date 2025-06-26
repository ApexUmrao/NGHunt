package com.newgen.dscop.client;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.newgen.dscop.client.TestXML;

public class TestXML 
{
	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException  
	{

		// Step 1: create a DocumentBuilderFactory
		DocumentBuilderFactory dbf =
				DocumentBuilderFactory.newInstance();

		// Step 2: create a DocumentBuilder
		DocumentBuilder db = dbf.newDocumentBuilder();

		// Step 3: parse the input file to get a Document object
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	} 

	public String getTagValues(String sXML, String sTagName) 
	{  
		String sTagValues = "";
		String sStartTag = "<" + sTagName + ">";
		String sEndTag = "</" + sTagName + ">";
		String tempXML = sXML;
		tempXML=tempXML.replaceAll("&","#amp#");
		try
		{

			for(int i=0;i<sXML.split(sEndTag).length-1;i++) 
			{
				if(tempXML.indexOf(sStartTag) != -1) 
				{
					sTagValues += tempXML.substring(tempXML.indexOf(sStartTag) + sStartTag.length(), tempXML.indexOf(sEndTag));
					//System.out.println("sTagValues"+sTagValues);
					tempXML=tempXML.substring(tempXML.indexOf(sEndTag) + sEndTag.length(), tempXML.length());
				}
				if(tempXML.indexOf(sStartTag) != -1) 
				{    
					sTagValues +=",";
					//System.out.println("sTagValues"+sTagValues);
				}
			}
			if(sTagValues.indexOf("#amp#")!= -1)
			{
				System.out.println("Index found");
				sTagValues =sTagValues.replaceAll("#amp#", "&");
			}
			//System.out.println(" Final sTagValues"+sTagValues);
		}

		catch(Exception e) 
		{   
		}
		return sTagValues;
	}


	public String getTagValue(String xml, String tag) throws ParserConfigurationException, SAXException, IOException 
	{
		xml=xml.replaceAll("&","#amp#");
		xml=xml.replaceAll(";","#col#");
		xml=xml.replaceAll(",","#Comma#");
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);
		String value = "";

		int length = nodeList.getLength();
		System.out.println("length---"+length);

		if (length > 0) 
		{
			String sTempValue ="";
			for(int j=0;j<length;j++)
			{
				Node node =  nodeList.item(j);

				if (node.getNodeType() == Node.ELEMENT_NODE) 
				{
					NodeList childNodes = node.getChildNodes();                                                                   
					int count = childNodes.getLength();        

					for (int i = 0; i < count; i++) 
					{              
						Node item = childNodes.item(i);
						if (item.getNodeType() == Node.ELEMENT_NODE) 
						{              
							sTempValue=item.getTextContent();

							if(sTempValue.indexOf("#amp#")!= -1)
							{
								System.out.println("Index found");
								sTempValue =sTempValue.replaceAll("#amp#", "&");
							}

							value += sTempValue+",";           
						}
						else if (item.getNodeType() == Node.TEXT_NODE)
						{
							value= item.getNodeValue();
							if(value.indexOf("#amp#")!= -1)
							{
								System.out.println("Index found");
								value =value.replaceAll("#amp#", "&");
							}
							return value;
						}
					}
					if(!value.equalsIgnoreCase(""))
					{
						value = value.substring(0, value.length()-1);
						value = value+";";
					}

				}
				else if (node.getNodeType() == Node.TEXT_NODE)
				{
					value= node.getNodeValue();
					if(value.indexOf("#amp#")!= -1)
					{
						System.out.println("Index found");
						value =value.replaceAll("#amp#", "&");
					}
					return value;
				}
			}

			if(!value.equalsIgnoreCase(""))
			{
				value = value.substring(0, value.length()-1);
			}
			return value;
		}
		return "";
	}


	public static void main(String a[])
	{
		try
		{
			String s="<Output><Option>ADCB_GETCUSTINFO</Option><returnCode>2</returnCode><errorDescription>InqCustomerInformation-666666-RemedySystem Timeout,IBSystem Timeout,WMS System Timeout</errorDescription><GetCustomerInformationRes><listOfCustomer><customer><Prefix>Mrs</Prefix><FullName>uksk kyohegksukw jdhmlp kekckosf</FullName><DOB>06/05/1978</DOB><PassportNO>A2712294</PassportNO><PssportIssueDate></PssportIssueDate><PassportExpDate></PassportExpDate><VisaNo></VisaNo><VisaIssueDate></VisaIssueDate><VisaExpDate></VisaExpDate><Nationality></Nationality><MotherName>RUKAIAH</MotherName><ResCountry></ResCountry><Profession></Profession><Gender></Gender><EIDA_NO></EIDA_NO><memoDesc></memoDesc><EmpName></EmpName><City>gkm ke nskfuks</City><POBox>bd ydv 9335</POBox><State>gkm ke  nskfuks</State><Country>hwfilo kgky lufgkilm</Country><Phone>3</Phone><Mobile>485639817776</Mobile><Email></Email></customer><Accounts><Account><AcctType>SOW</AcctType><AccountNo>10060444200001</AccountNo><AccountTitle>uksk kyohegksukw jdhmlp kekckosf</AccountTitle><AcctStatus>ACCOUNT OPEN REGULAR</AcctStatus><BranchCode>841</BranchCode><BranchName>IBD-RAS AL KHAIMAH</BranchName><ProductCode>200</ProductCode><ProductName>Emirati Millionaire Savings Account</ProductName><CurrencyName>AED</CurrencyName></Account></Accounts><Addresses><Address><Type>Correspondence Address</Type><AddressLine_1></AddressLine_1><AddressLine_2>wk</AddressLine_2><BuildingName></BuildingName><City>gkm ke nskfuks</City><CompanyName></CompanyName><Country>hwfilo kgky lufgkilm</Country><EMail></EMail><MobileNo>485639817776</MobileNo><POBox>bd ydv 9335</POBox><ZipCode></ZipCode></Address><Address><Type>Office Address</Type><AddressLine_1></AddressLine_1><AddressLine_2>wk</AddressLine_2><BuildingName></BuildingName><City>gkm ke nskfuks</City><CompanyName>WK</CompanyName><Country>hwfilo kgky lufgkilm</Country><EMail></EMail><MobileNo></MobileNo><POBox>bd ydv 9335</POBox><ZipCode></ZipCode></Address><Address><Type>Permanent Address</Type><AddressLine_1>wk</AddressLine_1><AddressLine_2></AddressLine_2><BuildingName></BuildingName><City>wk</City><CompanyName></CompanyName><Country>hwfilo kgky lufgkilm</Country><EMail></EMail><MobileNo></MobileNo><POBox>wk</POBox><ZipCode></ZipCode></Address><Address><Type>Residence Address</Type><AddressLine_1></AddressLine_1><AddressLine_2></AddressLine_2><BuildingName></BuildingName><City></City><CompanyName></CompanyName><Country></Country><EMail></EMail><MobileNo></MobileNo><POBox></POBox><ZipCode></ZipCode></Address></Addresses></listOfCustomer></GetCustomerInformationRes><TotalRetrieved>1</TotalRetrieved></Output>";
			TestXML t= new TestXML();
			String ar = t.getTagValues(s, "City");
			System.out.println("ar---"+ar);
			String ab = t.getTagValue(s, "PssportIssueDate");

			System.out.println("ab---"+ab);
		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
	}
}
