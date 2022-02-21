package servlet;

import DTO.FormRegisterRequestDTO;
import DTO.FormRegisterResponseDTO;
import DTO.ValidationReport;
import Domain.Registration;
import Domain.User;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.httpclient.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(
        name = "Register",
        urlPatterns = {"/register"}
)

public class RegisterServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
        if (!method.equals("PATCH")) {
            super.service(req, resp);
            return;
        }

        this.doPatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }

        System.out.println(sb.toString());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletOutputStream out = resp.getOutputStream();

        Gson gson = new Gson();

        FormRegisterRequestDTO formRequestData = new FormRegisterRequestDTO();

        try{
            formRequestData = gson.fromJson(sb.toString(), FormRegisterRequestDTO.class);
        }
        catch (JsonSyntaxException ex){
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            System.out.println("Unable to parse submitted form data into the Known Data type.");
            ex.printStackTrace();

            FormRegisterResponseDTO wrongJSON = new FormRegisterResponseDTO();
            wrongJSON.message = ValidationMessages.INVALID_REQUEST.toString();
            wrongJSON.status = false;
            wrongJSON.success = false;

            String wrongJSONStr = gson.toJson(wrongJSON);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();

            return;
        }

        FormRegisterResponseDTO postRes = new FormRegisterResponseDTO();

        postRes = formRequestData.ValidateFormData();

        if(!postRes.status){
            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        if(!postRes.success){
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        GenericResponse res = new User().isUserNameAlreadyExists(formRequestData.username);

        if(!res.status){

            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);

            postRes.status = res.status;
            postRes.success = res.success;
            postRes.message = ValidationMessages.INTERNAL_SERVER_ERROR.toString();

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        //username already exists
        if(res.success){
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            postRes.status = res.status;
            postRes.success = res.success;
            postRes.message =  ValidationMessages.USERNAME_EXISTS.toString();

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        Registration registration = new Registration();

        GenericResponse genericRes = registration.setRegistrationData(formRequestData);

        if(!genericRes.status || !genericRes.success){

            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);

            postRes.status = genericRes.status;
            postRes.success = genericRes.success;
            postRes.message = genericRes.message;

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        genericRes = registration.RegisterNewUser(registration.user, registration.userRegister);

        if(!genericRes.status || !genericRes.success){

            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);

            postRes.status = genericRes.status;
            postRes.success = genericRes.success;
            postRes.message = genericRes.message;

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        postRes.status = true; postRes.success = true;
        postRes.message = genericRes.message;

        String response = gson.toJson(postRes);
        out.write(response.getBytes());
        out.flush();
        out.close();
        return;
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }


        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletOutputStream out = resp.getOutputStream();

        Gson gson = new Gson();

        FormRegisterRequestDTO updateOBJ = new FormRegisterRequestDTO();

        updateOBJ = gson.fromJson(sb.toString(), FormRegisterRequestDTO.class);
        System.out.println(updateOBJ.toString());

        FormRegisterResponseDTO postRes = new FormRegisterResponseDTO();

        postRes = updateOBJ.ValidatePatchFormData();

        if(!postRes.status || !postRes.success){

            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        GenericResponse genericRes = new GenericResponse();

        genericRes = new User().isUserNameAlreadyExists(updateOBJ.username);

        if(!genericRes.status){

            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);

            postRes.status = genericRes.status;
            postRes.success = genericRes.success;
            postRes.message = ValidationMessages.INTERNAL_SERVER_ERROR.toString();

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        //username does not exists
        if(!genericRes.success){
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            postRes.status = genericRes.status;
            postRes.success = genericRes.success;
            postRes.message =  ValidationMessages.INVALID_USERNAME.toString();

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }


        Registration registration = new Registration();

        genericRes = registration.UpdateExistingUser(updateOBJ);

        if(!genericRes.status || !genericRes.success){

            resp.setStatus(HttpStatus.SC_BAD_REQUEST);

            postRes.status = genericRes.status;
            postRes.success = genericRes.success;
            postRes.message = genericRes.message;

            String wrongJSONStr = gson.toJson(postRes);

            out.write(wrongJSONStr.getBytes());
            out.flush();
            out.close();
            return;
        }

        postRes.status = true; postRes.success = true;
        postRes.message = ValidationMessages.USER_DATA_UPDATED.toString();

        String response = gson.toJson(postRes);
        out.write(response.getBytes());
        out.flush();
        out.close();
        return;

    }

}
