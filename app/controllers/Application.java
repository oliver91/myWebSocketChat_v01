package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ChatRoom;
import models.User;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import views.html.index;
import views.html.login;

public class Application extends Controller
{

    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render(User.find.byId(request().username())));
    }

    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);

            return redirect(
                    routes.Application.index()
            );
        }
    }

    /** Handle the chat websocket */
    public static WebSocket<JsonNode> chat() {
        final Http.Session sess = session();
        final String email = sess.get("email");

        return new WebSocket<JsonNode>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){

                // Join the chat room.
                try
                {
                    ChatRoom.join(email, in, out);
                }
                catch (Exception ex)
                {
                    System.out.println("FOOOOOOOOOOOOOOOOO!!!");
                    ex.printStackTrace();
                }
            }

        };
    }

    /** Create reverse routing for js */
    public static Result javascriptRoutes()
    {
        response().setContentType("text/javascript");
        return ok( Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Application.chat()) );
    }




    // ################################################################################################
    public static class Login
    {
        public String email;
        public String password;


        public String validate()
        {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

}
