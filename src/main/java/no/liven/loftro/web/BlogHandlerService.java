package no.liven.loftro.web;


import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
 
public class BlogHandlerService {
 
    private final DB db;
    private final DBCollection collection;
 
    public BlogHandlerService(DB db) {
        this.db = db;
        this.collection = db.getCollection("blog");
    }
 
    public List<BlogHandler> findAll() {
        List<BlogHandler> todos = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            todos.add(new BlogHandler((BasicDBObject) dbObject));
        }
        return todos;
    }
 
    public void createNewTodo(String body) {
    	BlogHandler todo = new Gson().fromJson(body, BlogHandler.class);
        collection.insert(new BasicDBObject("title", todo.getTitle()).append("done", todo.isDone()).append("createdOn", new Date()));
    }
 
    public BlogHandler find(String id) {
        return new BlogHandler((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }
 
    public BlogHandler update(String todoId, String body) {
    	BlogHandler todo = new Gson().fromJson(body, BlogHandler.class);
        collection.update(new BasicDBObject("_id", new ObjectId(todoId)), new BasicDBObject("$set", new BasicDBObject("done", todo.isDone())));
        return this.find(todoId);
    }
}