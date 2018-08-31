package pl.piomin.services.department.clientgql

import com.mashape.unirest.http.Unirest
import gql.DSL
import groovy.json.JsonOutput
import pl.piomin.services.department.model.Employee

class EmployeeGQL {

    List<Employee> getEmployeesByDepartmentQuery(String serverUrl, int departmentId) {
        String queryString = DSL.buildQuery {
            query('employeesByDepartment', [departmentId: departmentId]) {
                returns {
                    id
                    name
                    position
                    salary
                }
            }
        }
        (Unirest.post(serverUrl)
                .body(JsonOutput.toJson([query: queryString]))
                .asJson()
                .body.jsonObject['data']['employeesByDepartment'] as List)
                .collect { JsonUtils.jsonToData(it.toString(), Employee.class) }
    }
}