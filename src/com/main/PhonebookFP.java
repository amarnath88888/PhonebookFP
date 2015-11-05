package com.main;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.constants.ApplicationConstants;
import com.contacts.ContactsInitializer;
import com.search.ContactsSearch;
import com.writer.IContactsWriter;
import com.writer.CsvWriter;
import com.writer.ExcelWriter;
import com.writer.PDFWriter;
import com.writer.TextWriter;

public class PhonebookFP {
	public static final Logger logger = Logger.getLogger(PhonebookFP.class);
	
	public static void main(String args[])	{
		DOMConfigurator.configure("log4j.xml");
		ContactsInitializer contactsinitializer = new ContactsInitializer();
		PhonebookFP phonebook = new PhonebookFP();
		
		contactsinitializer.initProperty(null);
		contactsinitializer.initContacts(null);
		contactsinitializer.initXmlHeader();
		phonebook.processProperty(contactsinitializer);
		System.out.println(ApplicationConstants.BYE);
		logger.debug("Application Terminated");		
	}

	private void processProperty(ContactsInitializer contactsinitializer) {
		Properties properties;
		String task;
		String tasks[];
		
		IContactsWriter writer = null;
		ContactsSearch contactsearch;
		
		properties=contactsinitializer.getProperties();
		task = properties.getProperty(ApplicationConstants.TASK);
		System.out.println(task);
		tasks = task.split(ApplicationConstants.COMMA);
		
		for(String choice : tasks)	{
			if(choice.equals(ApplicationConstants.TEXT_OUTPUT))
				writer = new TextWriter();
			else if(choice.equals(ApplicationConstants.CSV_OUTPUT))
				writer = new CsvWriter();
			else if(choice.equals(ApplicationConstants.EXCEL_OUTPUT))
				writer = new ExcelWriter();
			else if(choice.equals(ApplicationConstants.PDF_OUTPUT))
				writer = new PDFWriter();
			else if(choice.equals(ApplicationConstants.SEARCH))	{
				contactsearch = new ContactsSearch();
				contactsearch.searchName(contactsinitializer);
				continue;
			}
			writer.writeContacts(contactsinitializer);
		}
	}
}