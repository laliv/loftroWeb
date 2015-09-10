package no.liven.loftro.web;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
 
import java.util.HashMap;
 
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
 
public class BlogHandlerResource {
 
    private static final String API_CONTEXT = "/api/v1";
 
    private final BlogHandlerService blogHandlerService;
 
    public BlogHandlerResource(BlogHandlerService blogHandlerService) {
        this.blogHandlerService = blogHandlerService;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
        post(API_CONTEXT + "/blog", "application/json", (request, response) -> {
        	blogHandlerService.createNewTodo(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());
 
        get(API_CONTEXT + "/blog/:id", "application/json", (request, response)
 
                -> blogHandlerService.find(request.params(":id")), new JsonTransformer());
 
        get(API_CONTEXT + "/blog", "application/json", (request, response)
 
                -> blogHandlerService.findAll(), new JsonTransformer());
 
        put(API_CONTEXT + "/blog/:id", "application/json", (request, response)
 
                -> blogHandlerService.update(request.params(":id"), request.body()), new JsonTransformer());
    }
 
 
}