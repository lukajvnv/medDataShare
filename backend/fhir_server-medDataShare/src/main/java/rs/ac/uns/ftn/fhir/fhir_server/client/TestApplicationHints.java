package rs.ac.uns.ftn.fhir.fhir_server.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.AdditionalRequestHeadersInterceptor;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import java.util.ArrayList;
import java.util.Date;

public class TestApplicationHints {

	/*
	 * This class contains hints for the tasks outlined in TestApplication
	 */

//	private static String serveFHIRApi = "https://hapi.fhir.org/baseR4";
	private static String serveFHIRApi = "http://127.0.0.1:8183/FHIR";
//	private static String serveFHIRApi = "http://127.0.0.1:8183/STU3";

	public static void main(String[] args) {
		String imagingStudyId = createImagingStudy();
		getImagingStudy(imagingStudyId);
//		String imagingStudyId = "5ffc5d09d2a2585e7bb39032";
//		updateImagingStudy(imagingStudyId);
//		searchImagingStudy();
		//		update();
//		get();
//		step1_read_a_resource();
//		step2_search_for_patients_named_test();
//		step3_create_patient();
	}

	private static void searchImagingStudy(){
		FhirContext ctx = FhirContext.forR4();
		ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
		ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		try {
			Bundle response = client
					.search()
					.forResource(ImagingStudy.class)
//					.where(Patient.FAMILY.matches().values("Fhirman"))
					.where(ImagingStudy.STARTED.after().day(new Date()))
					.and(ImagingStudy.STATUS.hasSystemWithAnyCode("registered"))
					.and(ImagingStudy.SUBJECT.hasId("patient1"))
//                 .and(Patient.BIRTHDATE.before().day("2014-01-01"))
//                 .count(100)
					.returnBundle(Bundle.class)
					.execute();
			System.out.println("Found " + response.getTotal()
					+ " Fhirman patients. Their logical IDs are:");
			response.getEntry().forEach((entry) -> {
				// within each entry is a resource - print its logical ID
				System.out.println(entry.getResource().getIdElement().getIdPart());
			});
		} catch (Exception e) {
			System.out.println("An error occurred trying to search:");
			e.printStackTrace();
		}
	}

	public static String createImagingStudy(){
		FhirContext ctx = FhirContext.forR4();
		ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
		ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		//		ImagingStudy imagingStudy = client.read().resource(ImagingStudy.class).withId("1598282").execute();
		ImagingStudy imagingStudy = new ImagingStudy();
		Reference referenceLocation = new Reference("fjdlkfdljfdlksjfdslj");
		referenceLocation.setDisplay("display name");
		imagingStudy.setLocation(referenceLocation);
		imagingStudy.setDescription("bla bla");
		imagingStudy.setStatus(ImagingStudy.ImagingStudyStatus.REGISTERED);   //REGISTERED(idle), AVAILABLE(unconditional), CANCELLED(forbidden), ENTEREDINERROR, UNKNOWN, NULL;
		Reference referenceSubject = new Reference("fjdlkfdljfdlksjfdslj");
		referenceSubject.setDisplay("display name");
		imagingStudy.setSubject(referenceSubject);
		imagingStudy.setStarted( new Date());
		imagingStudy.addIdentifier()
				.setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
				.setValue("id1");
		imagingStudy.addIdentifier()
				.setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
				.setUse(Identifier.IdentifierUse.OFFICIAL)
				.setValue("id2");
		Meta meta = new Meta().addProfile("profile").setSource("jfdljdkf");
		imagingStudy.setMeta(meta);
		Coding codingModality = new Coding();
		codingModality.setDisplay("CT trial");
		codingModality.setCode("CT");
		codingModality.setSystem("system");
		imagingStudy.setModality(new ArrayList<>(){{add(codingModality);}});
		ImagingStudy.ImagingStudySeriesComponent component = new ImagingStudy.ImagingStudySeriesComponent();
		component.setNumber(2);
//		component.setPerformer();
		Coding codingSeriesBody = new Coding();
		codingSeriesBody.setDisplay("picture type");
		codingSeriesBody.setCode("BINARY_ID");
		codingSeriesBody.setSystem("system");
		component.setBodySite(codingSeriesBody);
		imagingStudy.setSeries(new ArrayList<>(){{add(component);}});
		XhtmlNode xhtmlNode = new XhtmlNode(NodeType.Text, "desc");
		xhtmlNode.setContent("bjaljbalkj");
		Narrative narrative = new Narrative();
		narrative.setDiv(xhtmlNode);
		narrative.setStatus(Narrative.NarrativeStatus.ADDITIONAL);
		imagingStudy.setText(narrative);
//		imagingStudy.setBasedOn();
		Coding procedureCode = new Coding();
		procedureCode.setDisplay("picture type");
		procedureCode.setCode("BINARY_ID");
		procedureCode.setSystem("system");
		CodeableConcept c = new CodeableConcept(procedureCode);
		c.setText("babababa");
		imagingStudy.setProcedureCode(new ArrayList<>(){{add(c);}});

		BearerTokenAuthInterceptor bearerTokenAuthInterceptor = new BearerTokenAuthInterceptor();

		//		imagingStudy.setReasonCode();
//		imagingStudy.setEndpoint();

		try {
			MethodOutcome outcome = client
					.create()
					.resource(imagingStudy)
					.prettyPrint()   //!!!!!!!!!!!!!!!!!!!!!1
					.encodedXml()    //!!!!!!!!!!!!!!!!!!!!!1
					.execute();

			IdType id = (IdType) outcome.getId();
			System.out.println("Resource is available at: " + id.getValue());

			IParser xmlParser = ctx.newXmlParser().setPrettyPrint(true);
			ImagingStudy receivedImagingStudy = (ImagingStudy) outcome.getResource();
			System.out.println("This is what we send: \n"
					+ xmlParser.encodeResourceToString(imagingStudy) +
					"\n This is what we received: \n"
					+ xmlParser.encodeResourceToString(receivedImagingStudy));

//			String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
//			System.out.println(string);
			return id.getValue();
		} catch (DataFormatException e) {
			System.out.println("An error occurred trying to upload:");
			e.printStackTrace();
			return null;
		}



	}

	public static void updateImagingStudy(String idValue){
		FhirContext ctx = FhirContext.forR4();
		ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
		ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);
		AdditionalRequestHeadersInterceptor interceptor = new AdditionalRequestHeadersInterceptor();
		interceptor.addHeaderValue("User-data", "fjdlkfdljfdlksjfdslj");
		client.registerInterceptor(interceptor);

		ImagingStudy imagingStudy = new ImagingStudy();
		imagingStudy.setStatus(ImagingStudy.ImagingStudyStatus.CANCELLED);   //REGISTERED(idle), AVAILABLE(unconditional), CANCELLED(forbidden), ENTEREDINERROR, UNKNOWN, NULL;

		try {
			MethodOutcome outcome = client
					.update()
					.resource(imagingStudy)
					.withId(idValue)
					.prettyPrint()
					.encodedXml()
					.execute();

			IdType id = (IdType) outcome.getId();
			System.out.println("Resource is available at: " + id.getValue());

			IParser xmlParser = ctx.newXmlParser().setPrettyPrint(true);
			ImagingStudy receivedImagingStudy = (ImagingStudy) outcome.getResource();
			System.out.println("This is what we send: \n"
					+ xmlParser.encodeResourceToString(imagingStudy) +
					"\n This is what we received: \n"
					+ xmlParser.encodeResourceToString(receivedImagingStudy));
		} catch (DataFormatException e) {
			System.out.println("An error occurred trying to upload:");
			e.printStackTrace();
		} catch(Exception e1) {
			System.out.println(e1.getMessage());
		} finally {
			client.unregisterInterceptor(interceptor);
		}



	}

	public static void get(){
		// Create a context
		FhirContext ctx = FhirContext.forR4();
//		FhirContext ctx = FhirContext.forDstu3();

		// Create a client
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);
		// pri svakom pozivu servera se poziva interceptRequest pa interceptResponse
		// Read a patient with the given ID
		Patient patient = client.read().resource(Patient.class).withId("5fa9a1468cc0be50ab6601b8").execute();

		// Print the output
		String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println(string);
	}

	public static void getImagingStudy(String id){
		// Create a context
		FhirContext ctx = FhirContext.forR4();

		// Create a client
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);
		// pri svakom pozivu servera se poziva interceptRequest pa interceptResponse
		// Read a patient with the given ID
		ImagingStudy imagingStudy = client.read().resource(ImagingStudy.class).withId(id).execute();

		// Print the output
		String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(imagingStudy);
		System.out.println(string);
	}

	public static void update(){
		// Create a context
		FhirContext ctx = FhirContext.forR4();

		// Create a client
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		// Read a patient with the given ID
		Patient patient = client.read().resource(Patient.class).withId("1124683").execute();

		patient
				.addName()
				.setUse(HumanName.NameUse.OFFICIAL)
				.addPrefix("Mr")
				.setFamily("Fhirman")
				.addGiven("Sam");

		MethodOutcome outcome = client
                 .update()
				.resource(patient)
				.prettyPrint()   //!!!!!!!!!!!!!!!!!!!!!1
				.execute();

		// Print the output
		IParser jsonParser = ctx.newJsonParser().setPrettyPrint(true);
		Patient updatedPatient = (Patient) outcome.getResource();

		System.out.println("Before update: \n"
				+ jsonParser.encodeResourceToString(patient)
				+ "\n\nAfter update: \n"
				+ jsonParser.encodeResourceToString(updatedPatient));
	}

	public static void step1_read_a_resource() {

		FhirContext ctx = FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		Patient patient;
		try {
			// Try changing the ID from 952975 to 999999999999
			patient = client.read().resource(Patient.class).withId("1124683").execute();
		} catch (ResourceNotFoundException e) {
			System.out.println("Resource not found!");
			return;
		}

		String string = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println(string);

	}

	public static void step2_search_for_patients_named_test() {
		FhirContext ctx = FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		org.hl7.fhir.r4.model.Bundle results = client
			.search()
			.forResource(Patient.class)
			.where(Patient.NAME.matches().value("test"))
			.returnBundle(org.hl7.fhir.r4.model.Bundle.class)
			.execute();

		System.out.println("First page: ");
		System.out.println(ctx.newXmlParser().encodeResourceToString(results));

		// Load the next page
		org.hl7.fhir.r4.model.Bundle nextPage = client
			.loadPage()
			.next(results)
			.execute();

		System.out.println("Next page: ");
		System.out.println(ctx.newXmlParser().encodeResourceToString(nextPage));

	}
	
	public static void step3_create_patient() {
		// Create a patient
		Patient newPatient = new Patient();

		// Populate the patient with fake information
		newPatient
			.addName()
				.setFamily("DevDays2015")
				.addGiven("John")
				.addGiven("Luka");
		newPatient
			.addIdentifier()
				.setSystem("http://acme.org/mrn")
				.setValue("1234567");
		newPatient.setGender(Enumerations.AdministrativeGender.MALE);
		newPatient.setBirthDateElement(new DateType("2015-11-18"));

		// Create a client
		FhirContext ctx = FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

		// Create the resource on the server
		MethodOutcome outcome = client
			.create()
			.resource(newPatient)
			.execute();

		// Log the ID that the server assigned
		IIdType id = outcome.getId();
		System.out.println("Created patient, got ID: " + id);
	}
	
}
