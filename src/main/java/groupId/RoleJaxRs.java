package groupId;


import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import jdbc.RolesJdbc;
import model.Role;
import utils.request.*;
import utils.response.mainResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("roles")
public class RoleJaxRs {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public Response getRoles() {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();

        String selectSQL = "select * from roles";
        mainResponse.isSuccess = true;
        mainResponse.body = RolesJdbc.getRoles(selectSQL);

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("add")
    @Produces("application/json")
    public Response addRole(String body) throws MessagingException {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();
        addRoleRequest addRoleRequest = gson.fromJson(body, addRoleRequest.class);

        Role role = new Role();
        role.role = addRoleRequest.role;

        if (RolesJdbc.addRole(role)) {
            mainResponse.body="completed";
            mainResponse.isSuccess=true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @DELETE
    @Path("delete")
    @Produces("application/json")
    public Response deleteRole(String body) throws MessagingException {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();
        deleteRoleRequest deleteRoleRequest = gson.fromJson(body, deleteRoleRequest.class);
        if (RolesJdbc.deleteRole(deleteRoleRequest.id)) {
            mainResponse.body="completed";
            mainResponse.isSuccess=true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("update")
    @Produces("application/json")
    public Response updateRole(String body) throws MessagingException {
        mainResponse mainResponse = new mainResponse();

        Gson gson = new Gson();
        updateRoleRequest updateRoleRequest = gson.fromJson(body, updateRoleRequest.class);

        Role role = new Role();
        role.id = updateRoleRequest.id;
        role.role = updateRoleRequest.role;

        if (RolesJdbc.updateRole(role)) {
            mainResponse.body="completed";
            mainResponse.isSuccess=true;
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }
}
