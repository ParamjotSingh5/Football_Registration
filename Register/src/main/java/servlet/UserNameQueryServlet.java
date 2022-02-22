package servlet;

import Domain.User;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import Utilities.Validations;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "UserNameQuery",
        urlPatterns = {"/username-taken"}
)

public class UserNameQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqUserName = "";

        reqUserName = req.getParameter("username");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletOutputStream out = resp.getOutputStream();

        Gson gson = new Gson();

        if(reqUserName.isEmpty() || !new Validations().isSanitizedValue(reqUserName.trim())){

            resp.setStatus(400);

            out.write(gson.toJson(new GenericResponse()
                            .setMessage(ValidationMessages.INVALID_REQUEST.toString())
                            .setStatus(false)
                            .setSuccess(false)
                    ).getBytes());
            out.flush();
            out.close();

            return;
        }

        User existingUser = new User();
        GenericResponse genericResponse = existingUser.isUserNameAlreadyExists(reqUserName);

        if(!genericResponse.status){

            resp.setStatus(400);

            System.out.println(genericResponse.message);

            genericResponse.message = "An error error occurred while processing the request";
            genericResponse.status = false;
            genericResponse.success = false;

            out.write(gson.toJson(genericResponse).getBytes());
            out.flush();
            out.close();

            return;
        }

        //username does not exists
        if(!genericResponse.success){

            resp.setStatus(200);

            System.out.println(genericResponse.message);

            genericResponse.message = "No user found with provided username";

            out.write(gson.toJson(genericResponse).getBytes());
            out.flush();
            out.close();

            return;
        }

        resp.setStatus(200);
        out.write(gson.toJson(genericResponse).getBytes());
        out.flush();
        out.close();

        return;
    }
}
