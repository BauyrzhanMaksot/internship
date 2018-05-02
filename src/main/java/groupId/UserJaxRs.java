package groupId;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import jdbc.UserJdbc;
import model.User;
import utils.request.addUserRequest;
import utils.request.deleteUserRequest;
import utils.request.updateUserRequest;
import utils.response.mainResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("users")
public class UserJaxRs {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }


    @GET
    @Path("all")
    @Produces("application/json")
    public Response getUsers() {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();

        String selectSQL = "select * from users";
        mainResponse.isSuccess = true;
        mainResponse.body = UserJdbc.getUsers(selectSQL);

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("add")
    @Produces("application/json")
    public Response addUser(String body) {

        mainResponse mainResponse = new mainResponse();
        Gson gson = new Gson();
        addUserRequest addUserRequest = gson.fromJson(body, addUserRequest.class);

        User user = new User();
        user.firstName = addUserRequest.firstName;
        user.contactNumber = addUserRequest.contactNumber;


        if (UserJdbc.addUser(user)) {
            mainResponse.body = "completed";
            mainResponse.isSuccess = true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }


    @POST
    @Path("delete")
    @Produces("application/json")
    public Response deleteUser(String body) throws MessagingException {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();
//        String body = "";
        System.out.println(body);
        deleteUserRequest deleteUserRequest = gson.fromJson(body, deleteUserRequest.class);
        if (UserJdbc.deleteUser(deleteUserRequest.id)) {
            mainResponse.body = "completed";
            mainResponse.isSuccess = true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("update")
    @Produces("application/json")
    public Response updateUser(String body) throws MessagingException {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();
        updateUserRequest updateUserRequest = gson.fromJson(body, updateUserRequest.class);

        User user = new User();
        user.id = updateUserRequest.id;
        user.firstName = updateUserRequest.firstName;
        user.contactNumber = updateUserRequest.contactNumber;

        if (UserJdbc.updateUser(user)) {
            mainResponse.body = "completed";
            mainResponse.isSuccess = true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }
}