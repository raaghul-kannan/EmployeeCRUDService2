package com.mycompany.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mycompany.model.Employee;
import org.bson.Document;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmployeeDAO {

    private final MongoClient client;
    private final MongoCollection mongoCollection;

    public EmployeeDAO(String propFileName) throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/"+propFileName);
        Properties properties = new Properties();
        properties.load(fileReader);

        String hostName = properties.getProperty("hostname");
        int port = Integer.parseInt(properties.getProperty("port"));
        String db = properties.getProperty("db");

        client = new MongoClient(hostName, port);
        mongoCollection = client.getDatabase(db).getCollection("employees");
    }

    public boolean createEmployee(Employee employee){
        Document document = new Document();
        document.append("_id", employee.getId());
        document.append("employeeName", employee.getName());
        document.append("employeeDesignation", employee.getDesignation());

        client.getDatabase("details")
                .getCollection("employees").insertOne(document);
        return true;
    }

    public Employee getEmployee(int employeeId){
        FindIterable<Document> findIterable = mongoCollection.find(Filters.eq("_id", employeeId));
        for(Document doc : findIterable) {
            return new Employee(doc.getInteger("_id"),
                    doc.getString("employeeName"),
                    doc.getString("employeeDesignation"));
        }
        return null;
    }

    public boolean updateEmployee(Employee employee){
        mongoCollection.updateOne(new Document("_id", employee.getId()),
                new Document("$set", new Document("employeeDesignation", employee.getDesignation())
                        .append("employeeName", employee.getName())));
        return true;
    }

    public boolean deleteEmployee(int employeeId) {
        mongoCollection.deleteOne(new Document("_id", employeeId));
        return true;
    }
}