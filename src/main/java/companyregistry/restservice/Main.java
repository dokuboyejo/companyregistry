package companyregistry.restservice;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Response;

import companyregistry.application.beneficiary.BeneficiaryApplicationService;
import companyregistry.application.beneficiary.BeneficiaryApplicationServiceImpl;
import companyregistry.application.company.CompanyApplicationService;
import companyregistry.application.company.CompanyApplicationServiceImpl;
import companyregistry.domain.CompanyBeneficiary;
import companyregistry.domain.Fault;
import companyregistry.domain.IdCatalog;
import companyregistry.domain.beneficiary.Beneficiary;
import companyregistry.domain.company.Company;
import companyregistry.restservice.beneficiary.representation.BeneficiaryRepresentation;
import companyregistry.restservice.company.representation.CompanyRepresentation;
import companyregistry.restservice.exception.BadRequestResourceException;
import companyregistry.restservice.exception.ConflictResourceException;
import companyregistry.restservice.exception.NotFoundResourceException;
import companyregistry.restservice.exception.ServerErrorResourceException;
import companyregistry.util.LoadData;
import spark.servlet.SparkApplication;

/**
 * @author dokuboyejo
 *
 */
public class Main implements SparkApplication {

	CompanyApplicationService companyApplicationService;
	BeneficiaryApplicationService beneficiaryApplicationService;
	LoadData loadData;
	
	@Override
	public void init() {
		/*
		 * Application services and Utility initialization
		 */
		companyApplicationService = new CompanyApplicationServiceImpl();
		beneficiaryApplicationService = new BeneficiaryApplicationServiceImpl();
		loadData = new LoadData();
		
		/*
		 * CRUD for companies
		 */
		
		// list all previously registered companies
		get("/companies", (request, response) -> {
			List<Company> companies = companyApplicationService.getCompanies();
			List<CompanyRepresentation> companyRepresentations = new ArrayList<CompanyRepresentation>();
			companies
				 .parallelStream()
			     .forEach(c -> {
			    	 CompanyRepresentation cr = new CompanyRepresentation(c);
				     companyRepresentations.add(cr);
			     });
			return companyRepresentations;
		}, loadData);
		
		// retrieve details of a particular company
		get("/companies/:id", (request, response) -> {
			if (!StringUtils.isNumeric(request.params("id"))) throw new BadRequestResourceException("company's id is invalid");
			Company company = companyApplicationService.getCompany(Long.valueOf(request.params("id")));
			CompanyRepresentation companyRepresentation = new CompanyRepresentation(company); 
			return companyRepresentation;
		}, loadData);
		
		// list all previously registered beneficiaries for a particular company
		get("/companies/:id/beneficiaries", (request, response) -> {
			if (!StringUtils.isNumeric(request.params("id"))) throw new BadRequestResourceException("company's id is invalid");
			List<Beneficiary> beneficiaries = companyApplicationService.getCompanyBeneficiaries(Long.valueOf(request.params("id")));
			List<BeneficiaryRepresentation> beneficiaryRepresentations = new ArrayList<BeneficiaryRepresentation>();
			beneficiaries
				 .parallelStream()
			     .forEach(b -> {
			    	 BeneficiaryRepresentation br = new BeneficiaryRepresentation(b);
			    	 beneficiaryRepresentations.add(br);
			     });
			return beneficiaryRepresentations;
		}, loadData);
		
		// create a new company
		post("/companies", "application/json", (request, response) -> {
			Company company = LoadData.objectize(request.body(), Company.class);
			if (null == company) throw new BadRequestResourceException("company details must be provided");
			Long companyId = companyApplicationService.addCompany(company);
			return LoadData.objectize(String.format("{\"id\":%d}", companyId), Object.class);
		}, loadData);
		
		// add new beneficiaries to a particular company
		post("/companies/:id/beneficiaries", "application/json", (request, response) -> {
			String id = request.params("id");
			if (!StringUtils.isNumeric(id)) throw new BadRequestResourceException("company's id is invalid");
			List<Beneficiary> beneficiaries = LoadData.objectizeList(request.body(), Beneficiary.class);
			if (null == beneficiaries || beneficiaries.isEmpty()) throw new BadRequestResourceException("a minimum of one valid beneficiary details (id, firstName, lastName) must be provided");
			Long beneficiaryCount = companyApplicationService.addNewBeneficiaryToCompany(Long.valueOf(id), beneficiaries);
			return LoadData.objectize(String.format("{\"numberOfBeneficiariesAdded\":%d}", beneficiaryCount), Object.class);
		}, loadData);
		
		// update an existing company
		put("/companies", "application/json", (request, response) -> {
			Company company = LoadData.objectize(request.body(), Company.class);
			if (null == company) throw new BadRequestResourceException("company details must be provided");
			boolean status = companyApplicationService.updateCompany(company);
			return LoadData.objectize(String.format("{\"status\":%s}", status), Object.class);
		}, loadData);
		
		// Add existing beneficiaries to a particular company
		put("/companies/:id/beneficiaries", "application/json", (request, response) -> {
			String companyId = request.params("id");
			if (!StringUtils.isNumeric(companyId)) throw new BadRequestResourceException("company's id is invalid");
			List<IdCatalog> idCatalogs = LoadData.objectizeList(request.body(), IdCatalog.class);
			if (null == idCatalogs || idCatalogs.isEmpty()) throw new BadRequestResourceException("a minimum of one valid beneficiary id details must be provided");
			Long beneficiaryCount = companyApplicationService.addExistingBeneficiaryToCompany(Long.valueOf(companyId), idCatalogs);
			return LoadData.objectize(String.format("{\"numberOfBeneficiariesAdded\":%d}", beneficiaryCount), Object.class);
		}, loadData); 
		
		// delete a particular company
		delete("/companies/:id", (request, response) -> {
			if (!StringUtils.isNumeric(request.params("id"))) throw new BadRequestResourceException("company's id is invalid");
			boolean status = companyApplicationService.deleteCompany(Long.valueOf(request.params("id")));
			return LoadData.objectize(String.format("{\"status\":%s}", status), Object.class);
		}, loadData);
		
		// remove a beneficiary from a particular company
		delete("/companies/:c_id/beneficiaries/:b_id", (request, response) -> {
			String companyId = request.params("c_id");
			String beneficiaryId = request.params("b_id");
			if (!StringUtils.isNumeric(companyId)) throw new BadRequestResourceException("company's id is invalid");
			if (!StringUtils.isNumeric(beneficiaryId)) throw new BadRequestResourceException("beneficiary's id is invalid");
			CompanyBeneficiary companyBeneficiary = new CompanyBeneficiary();
			companyBeneficiary.setBeneficiaryId(Long.valueOf(beneficiaryId));
			companyBeneficiary.setCompanyId(Long.valueOf(companyId));
			boolean status = companyApplicationService.deleteBeneficiaryFromCompany(companyBeneficiary);
			return LoadData.objectize(String.format("{\"status\":%s}", status), Object.class);
		}, loadData);
		
		/*
		 * CRUD for beneficiaries
		 */
		
		// list all previously registered beneficiaries of all companies
		get("/beneficiaries", (request, response) -> {
			List<Beneficiary> beneficiaries = beneficiaryApplicationService.getBeneficiaries();
			List<BeneficiaryRepresentation> beneficiaryRepresentations = new ArrayList<BeneficiaryRepresentation>();
			beneficiaries
				.parallelStream()
			    .forEach(p -> {
				    BeneficiaryRepresentation pr = new BeneficiaryRepresentation(p);
				    beneficiaryRepresentations.add(pr);
			    });
			return beneficiaryRepresentations;
		}, loadData);
		
		// retrieve details of a particular beneficiary
		get("/beneficiaries/:id", (request, response) -> {
			if (!StringUtils.isNumeric(request.params("id"))) throw new BadRequestResourceException("beneficiary's id is invalid");
			Beneficiary beneficiary = beneficiaryApplicationService.getBeneficiary(Long.valueOf(request.params("id")));
			BeneficiaryRepresentation beneficiaryRepresentation = new BeneficiaryRepresentation(beneficiary);
			return beneficiaryRepresentation;
		}, loadData);
		
		// create a new beneficiary
		post("/beneficiaries", "application/json", (request, response) -> {
			Beneficiary beneficiary = LoadData.objectize(request.body(), Beneficiary.class);
			if (null == beneficiary) throw new BadRequestResourceException("beneficiary details must be provided");
			Long beneficiaryId = beneficiaryApplicationService.addBeneficiary(beneficiary);
			return LoadData.objectize(String.format("{\"id\":%d}", beneficiaryId), Object.class);
		}, loadData);
		
		// update an existing beneficiary
		put("/beneficiaries", "application/json", (request, response) -> {
			Beneficiary beneficiary = LoadData.objectize(request.body(), Beneficiary.class);
			if (null == beneficiary) throw new BadRequestResourceException("beneficiary details must be provided");
			boolean status = beneficiaryApplicationService.updateBeneficiary(beneficiary);
			return LoadData.objectize(String.format("{\"status\":%s}", status), Object.class);
		}, loadData);
		
		// delete a particular beneficiary
		delete("/beneficiaries/:id", (request, response) -> {
			if (!StringUtils.isNumeric(request.params("id"))) throw new BadRequestResourceException("beneficiary's id is invalid");
			boolean status = beneficiaryApplicationService.deleteBeneficiary(Long.valueOf(request.params("id")));
			return LoadData.objectize(String.format("{\"status\":%s}", status), Object.class);
		}, loadData);

		/*
		 * START -- CORS configuration
		 */
		options("/*", (request,response)->{
		    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
		    if (accessControlRequestHeaders != null) response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

		    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
		    if(accessControlRequestMethod != null) response.header("Access-Control-Allow-Methods", accessControlRequestMethod);

		    return "OK";
		});
		
		before((request,response)->{
		    response.header("Access-Control-Allow-Origin", "*");
		});
		
		/*
		 * END -- CORS configuration
		 */
		
		// map all request type to media type JSON
		after((req, res) -> {
			res.type("application/json");
		});
		
		exception(NotFoundResourceException.class, (e, request, response) -> {
			response.status(Response.SC_NOT_FOUND);
			response.body(errorMessage(Response.SC_NOT_FOUND, e.getMessage()));
		});

		exception(BadRequestResourceException.class, (e, request, response) -> {
			response.status(Response.SC_BAD_REQUEST);
			response.body(errorMessage(Response.SC_BAD_REQUEST, e.getMessage()));
		});

		exception(ConflictResourceException.class, (e, request, response) -> {
			response.status(Response.SC_CONFLICT);
			response.body(errorMessage(Response.SC_CONFLICT, e.getMessage()));
		});
		
		exception(ServerErrorResourceException.class, (e, request, response) -> {
			response.status(Response.SC_INTERNAL_SERVER_ERROR);
			response.body(errorMessage(Response.SC_INTERNAL_SERVER_ERROR, e.getMessage()));
		});
	}
	
	public String errorMessage(int errorCode, String errorMessage) {
		Fault fault = new Fault();
		fault.setErrorCode(String.valueOf(errorCode));
		fault.setErrorMessage(errorMessage);
		return LoadData.jsonize(fault);
	}

}
