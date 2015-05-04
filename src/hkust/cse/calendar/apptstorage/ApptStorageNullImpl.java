package hkust.cse.calendar.apptstorage;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;
	public ApptStorageNullImpl( User user )
	{
		defaultUser = user;
	}
	
	@Override
	public void SaveAppt(Appt appt) {
		//TODO Auto-generated method stub
	if(mAppts.containsKey(appt.getID()))
	{UpdateAppt(appt);
	System.out.println("Update Appt successfully");
	}
	else
	{
    mAppts.put(appt.getID(),appt);
    System.out.println("SaveAppt successfully");
	}
	testing();
	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAppt(Appt appt) {
		// TODO Auto-generated method stub
		mAppts.put(appt.getID(),appt);
	}

	@Override
	public void RemoveAppt(Appt appt) {
		// TODO Auto-generated method stub
       mAppts.remove(appt.gettimespan());
	}

	@Override
	public User getDefaultUser() {
		// TODO Auto-generated method stub
		return defaultUser;
	}

	@Override
	public void LoadApptFromXml() {
		// TODO Auto-generated method stub

	}
	
    // create other function that you need
	@Override
	public int getusableid(){
	while(mAppts.containsKey(usableid))
	{usableid++;}
	return usableid;
	}
	
	@Override
	public void testing()
	{
	       for (Object key : mAppts.keySet()) {
	            System.out.println(key + " : " + mAppts.get(key).getTitle());}
	}
	
	@Override
	public void testing2()
	{
	       for (Object key : Llist.keySet()) {
	            System.out.println(key + " : " + Llist.get(key));}
	}
	
	@Override
	public void setlocationlist(String location)
	{
		Llist.put(location,location);
	    System.out.println("Save Location successfully");
	    testing2();
	    testing();
	}
	
	@Override
	public void saveApptToXml()
	{
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Hashmap");
			doc.appendChild(rootElement);
	 
			// Appt elements
			Element Appt = doc.createElement("Appt");
			rootElement.appendChild(Appt);
	 
			// set attribute to Appt element
			Appt.setAttribute("id", "1");
	 
			// firstname elements
			Element TimeSpan = doc.createElement("TimeSpan");
			TimeSpan.appendChild(doc.createTextNode("yonga"));
			Appt.appendChild(TimeSpan);
	 
			// lastname elements
			Element mTitle = doc.createElement("mTitle");
			mTitle.appendChild(doc.createTextNode("mook kim"));
			Appt.appendChild(mTitle);
	 
			// mInfoelements
			Element mInfo= doc.createElement("mInfo");
			mInfo.appendChild(doc.createTextNode("mkyong"));
			Appt.appendChild(mInfo);
	 
			// mApptID elements
			Element mApptID = doc.createElement("mApptID");
			mApptID.appendChild(doc.createTextNode("100000"));
			Appt.appendChild(mApptID);
	 
			// joinApptID elements
	                Element joinApptID = doc.createElement("joinApptID");
	                joinApptID.appendChild(doc.createTextNode("100000"));
			Appt.appendChild(joinApptID);

			// isjoint elements
			Element isjoint = doc.createElement("isjoint");
			isjoint.appendChild(doc.createTextNode("100000"));
			Appt.appendChild(isjoint);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\file.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		}
	
		
	}

