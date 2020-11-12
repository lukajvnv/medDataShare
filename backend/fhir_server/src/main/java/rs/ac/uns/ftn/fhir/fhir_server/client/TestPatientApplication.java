package rs.ac.uns.ftn.fhir.fhir_server.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.r4.model.*;
import rs.ac.uns.ftn.fhir.fhir_server.util.ImageUtil;
import rs.ac.uns.ftn.fhir.fhir_server.util.PdfExporter;

import java.io.IOException;

public class TestPatientApplication {

//   	private static String serveFHIRApi = "https://hapi.fhir.org/baseR4";
   private static String serveFHIRApi = "http://127.0.0.1:8183/FHIR";
   //	private static String serveFHIRApi = "http://127.0.0.1:8183/STU3";

   /**
    * This is the Java main method, which gets executed
    */
   public static void main(String[] args)  {
      // context - create this once, as it's an expensive operation
      // see http://hapifhir.io/doc_intro.html

      try {
//         init();
//         createPatient();
//         search();
//         runOperation();
         createBinary();
//         readBinary();
//         runTestOperation();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static void createBinary() throws IOException {
      FhirContext ctx = FhirContext.forR4();

      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      try {
         Binary binary = new Binary();
         binary.setId(new IdType("fafa"));  //ovo ne radi
//         binary.setContentType("application/jpg");
         binary.setContentType("jpg");
//         binary.setContentType("png");
         binary.setContent(ImageUtil.getBytes());
//         binary.setProperty("fileName", new StringType("ala"));

         MethodOutcome outcome = client
                 .create()
                 .resource(binary)
                 .execute();

         IdType id = (IdType) outcome.getId();
         System.out.println("Resource is available at: " + id.getValue());

         Binary receivedBinary = (Binary) outcome.getResource();
         IParser xmlParser = ctx.newXmlParser().setPrettyPrint(true);
      } catch (DataFormatException e) {
         System.out.println("An error occurred trying to upload:");
         e.printStackTrace();
      }
   }

   private static void readBinary() throws IOException {
      FhirContext ctx = FhirContext.forR4();

      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      try {
         Binary binary = client
                 .read()
                 .resource(Binary.class)
                 .withId("1124683")
                 .execute();

         byte[] bytes = binary.getContent();

         System.out.println("Resource is available at: " + binary.getId());

      } catch (DataFormatException e) {
         System.out.println("An error occurred trying to upload:");
         e.printStackTrace();
      }
   }

   private static void init() throws IOException{
      FhirContext ctx = FhirContext.forR4();

      Patient patient = new Patient();

      // you can use the Fluent API to chain calls
      HumanName humanName = new HumanName();
      patient.addName()
              .setUse(HumanName.NameUse.OFFICIAL)
              .setFamily("Jovanovic")
              .addPrefix("Mr")
              .addGiven("Sam");
      patient.addIdentifier()
              .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
              .setValue("8003608166690503");

      System.out.println("Press Enter to serialise Resource to the console as XML.");
      System.in.read();

      // create a new XML parser and serialize our Patient object with it
      String encoded = ctx.newXmlParser().setPrettyPrint(true)
              .encodeResourceToString(patient);

      System.out.println(encoded);

      System.out.println("Press Enter to end.");
      System.in.read();
   }

   private static void createPatient() throws IOException {
      FhirContext ctx = FhirContext.forR4();

      Patient ourPatient = new Patient();

      // you can use the Fluent API to chain calls
      // see http://hapifhir.io/doc_fhirobjects.html
      ourPatient
              .addName()
              .setUse(HumanName.NameUse.OFFICIAL)
              .addPrefix("Mr")
              .setFamily("Fhirman")
              .addGiven("Sam");
      ourPatient.addIdentifier()
              .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
              .setValue("8003608166690503");

      // increase timeouts since the server might be powered down
      // see http://hapifhir.io/doc_rest_client_http_config.html
      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      // create the RESTful client to work with our FHIR server
      // see http://hapifhir.io/doc_rest_client.html
      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      try {
         // send our resource up - result will be stored in 'outcome'
         // see http://hapifhir.io/doc_rest_client.html#Create_-_Type
         MethodOutcome outcome = client
                 .create()
//                 .update()
                 .resource(ourPatient)
                 .prettyPrint()   //!!!!!!!!!!!!!!!!!!!!!1
                 .encodedXml()    //!!!!!!!!!!!!!!!!!!!!!1
                 .execute();

         IdType id = (IdType) outcome.getId();
         System.out.println("Resource is available at: " + id.getValue());

         IParser xmlParser = ctx.newXmlParser().setPrettyPrint(true);
//         xmlParser.setParserErrorHandler(new StrictErrorHandler());
         Patient receivedPatient = (Patient) outcome.getResource();
         System.out.println("This is what we sent up: \n"
                 + xmlParser.encodeResourceToString(ourPatient)
                 + "\n\nThis is what we received: \n"
                 + xmlParser.encodeResourceToString(receivedPatient));
      } catch (DataFormatException e) {
         System.out.println("An error occurred trying to upload:");
         e.printStackTrace();
      }

      System.out.println("Press Enter to end.");
      System.in.read();
   }

   private static void search() throws IOException{
      // context - create this once, as it's an expensive operation
      // see http://hapifhir.io/doc_intro.html
      FhirContext ctx = FhirContext.forR4();

      // increase timeouts since the server might be powered down
      // see http://hapifhir.io/doc_rest_client_http_config.html
      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      // create the RESTful client to work with our FHIR server
      // see http://hapifhir.io/doc_rest_client.html
      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      System.out.println("Press Enter to send to server: " + serveFHIRApi);
      System.in.read();

      try {
         // perform a search for Patients with last name 'Fhirman'
         // see http://hapifhir.io/doc_rest_client.html#SearchQuery_-_Type
         Bundle response = client
                 .search()
                 .forResource(Patient.class)
                 .where(Patient.FAMILY.matches().values("Fhirman"))
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

      System.out.println("Press Enter to end.");
      System.in.read();
   }

   private static void runOperation() throws IOException{
      // context - create this once, as it's an expensive operation
      // see http://hapifhir.io/doc_intro.html
      FhirContext ctx = FhirContext.forR4();
//      String serverBaseUrl = "http://sqlonfhir-stu3.azurewebsites.net/fhir";

      // increase timeouts since the server might be powered down
      // see http://hapifhir.io/doc_rest_client_http_config.html
      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      // create the RESTful client to work with our FHIR server
      // see http://hapifhir.io/doc_rest_client.html
      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      client.registerInterceptor(new LoggingInterceptor(true));
      System.out.println("Press Enter to send to server: " + serveFHIRApi);

      System.in.read();

      try {
         String patientId = "1124683";
         // Invoke $everything on our Patient
         // See http://hapifhir.io/doc_rest_client.html#Extended_Operations
         Parameters inParams = new Parameters();
         inParams.addParameter().setName("start").setValue(new StringType("2001-01-01"));
//         Parameters outParams = client
//                 .operation()
//                 .onServer()
//                 .named("$versions")
//                 .withNoParameters(Parameters.class) // No input parameters
//                 .useHttpGet() // Use HTTP GET instead of POST
//                 .execute();

         Parameters outParams = client
                 .operation()
                 .onInstance(new IdType("Patient", patientId))
                 .named("$everything")
                 .withNoParameters(Parameters.class) // No input parameters
                 .execute();

         // FHIR normally returns a 'Parameters' resource to an operation, but
         // in case of a single resource response, it just returns the resource
         // itself. This is why it seems that we have to fish a Bundle out of the
         // resulting Params result - HAPI needs to update for the FHIR shortcut
         Bundle result = (Bundle) outParams.getParameterFirstRep().getResource();

         System.out.println("Received " + result.getTotal()
                 + " results. The resources are:");
         result.getEntry().forEach((entry) -> {
            Resource resource = entry.getResource();
            System.out.println(resource.getResourceType() + "/"
                    + resource.getIdElement().getIdPart());
         });
      } catch (Exception e) {
         System.out.println("An error occurred trying to run the operation:");
         e.printStackTrace();
      }

      System.out.println("Press Enter to end.");
      System.in.read();
   }

   private static void runTestOperation() throws IOException{
      // context - create this once, as it's an expensive operation
      // see http://hapifhir.io/doc_intro.html
      FhirContext ctx = FhirContext.forR4();
//      String serverBaseUrl = "http://sqlonfhir-stu3.azurewebsites.net/fhir";

      // increase timeouts since the server might be powered down
      // see http://hapifhir.io/doc_rest_client_http_config.html
      ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
      ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);

      // create the RESTful client to work with our FHIR server
      // see http://hapifhir.io/doc_rest_client.html
      IGenericClient client = ctx.newRestfulGenericClient(serveFHIRApi);

      try {
         String patientId = "1124683";

         Parameters inParams = new Parameters();
         inParams.addParameter().setName("start").setValue(new StringType("2001-01-01"));

         Parameters outParams = client
                 .operation()
//                 .onType(Patient.class)
                 .onInstance(new IdType("Patient", patientId))
                 .named("$test")
//                 .withNoParameters(Parameters.class)
                 .withParameters(inParams)
                 .useHttpGet()
                 .execute();

         Bundle result = (Bundle) outParams.getParameterFirstRep().getResource();

         System.out.println("Received " + result.getTotal()
                 + " results. The resources are:");
         result.getEntry().forEach((entry) -> {
            Resource resource = entry.getResource();
            System.out.println(resource.getResourceType() + "/"
                    + resource.getIdElement().getIdPart());
         });
      } catch (Exception e) {
         System.out.println("An error occurred trying to run the operation:");
         e.printStackTrace();
      }

   }



}
