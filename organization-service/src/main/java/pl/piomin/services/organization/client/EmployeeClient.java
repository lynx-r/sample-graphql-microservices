package pl.piomin.services.organization.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piomin.services.organization.clientgql.EmployeeGQL;
import pl.piomin.services.organization.model.Employee;

import java.util.List;
import java.util.Random;

@Component
public class EmployeeClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeClient.class);
	private static final int TIMEOUT = 5000;
	private static final String SERVICE_NAME = "EMPLOYEE-SERVICE"; 
	private static final String SERVER_URL = "http://localhost:%d/graphql";
	
	Random r = new Random();
	
	@Autowired
	private EurekaClient discoveryClient;

	public List<Employee> findByDepartment(Long departmentId) {
		Application app = discoveryClient.getApplication(SERVICE_NAME);
		InstanceInfo ii = app.getInstances().get(r.nextInt(app.size()));
		String serverUrl = String.format(SERVER_URL, ii.getPort());
		EmployeeGQL clientGQL = new EmployeeGQL();
		return clientGQL.getEmployeesByDepartmentQuery(serverUrl, departmentId.intValue());
	}

	public List<Employee> findByOrganization(Long organizationId) {
		Application app = discoveryClient.getApplication(SERVICE_NAME);
		InstanceInfo ii = app.getInstances().get(r.nextInt(app.size()));
		String serverUrl = String.format(SERVER_URL, ii.getPort());
		EmployeeGQL clientGQL = new EmployeeGQL();
		return clientGQL.getEmployeesByOrganizationQuery(serverUrl, organizationId.intValue());
	}
	
}
