package Domain;

import DTO.FormRegisterRequestDTO;
import Utilities.Extensions;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import com.google.gson.Gson;
import launch.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringJoiner;

public class Registration {

    public User user = new User();
    public UserRegister userRegister = new UserRegister();

    public FormRegisterRequestDTO getRegistrationData(){

        FormRegisterRequestDTO RequestDTO = new FormRegisterRequestDTO();

        RequestDTO.username = this.user.UserName; RequestDTO.firstname = this.user.FirstName;
        RequestDTO.lastname = this.user.LastName; RequestDTO.email = this.user.Email;
        RequestDTO.address = this.user.Address; RequestDTO.pincode = this.user.PinCode;
        RequestDTO.phonenumber = this.user.Phone; RequestDTO.countrydailcodeselect = this.user.DailCode;
        RequestDTO.desiredteamradios = this.userRegister.TeamId; RequestDTO.desiredpositionchecks = this.userRegister.getFormattedPositionId();
        RequestDTO.agegroup = this.userRegister.AgeGroupId; RequestDTO.countryselect = this.user.CountryCode;
        RequestDTO.stateselect = this.user.StateCode; RequestDTO.cityselect = this.user.CityName;

        return RequestDTO;
    }

    public GenericResponse setRegistrationData(FormRegisterRequestDTO requestDTO){

        //here we are considering that requestDTO is a valid object.

        try{
            this.user.setUserName(requestDTO.username).setFirstName(requestDTO.firstname).setLastName(requestDTO.lastname).setEmail(requestDTO.email).setPhone(requestDTO.phonenumber).
                    setDailCode(requestDTO.countrydailcodeselect).setPinCode(requestDTO.pincode).setAddress(requestDTO.address).setCountryCode(requestDTO.countryselect).
                    setStateCode(requestDTO.stateselect).setCityName(requestDTO.cityselect);

            this.userRegister.setAgeGroupId(requestDTO.agegroup).setTeamId(requestDTO.desiredteamradios).setPositionId(requestDTO.desiredpositionchecks);

            return new GenericResponse(true, true, "parsed");
        }
        catch (Exception ex){
            System.out.println(ex.getStackTrace());

            return new GenericResponse(false, false, ex.getMessage());
        }
    }

    public GenericResponse RegisterNewUser(User user, UserRegister register){

        Database db = new Database();
        Connection con = null;

        try{
            con = db.connect();
        }
        catch (Exception ex){
            return new GenericResponse(false, false, ex.getMessage());
        }

        String query = "EXEC RegisterNewUser @UserName = ?, \n" +
                "\t@FirstName = ?, \n" +
                "\t@LastName = ?, \n" +
                "\t@EMAIL = ?, \n" +
                "\t@PHONE = ?, \n" +
                "\t@DAIL_CODE = ?, \n" +
                "\t@PINCODE = ? , \n" +
                "\t@ADDRESS = ?,\n" +
                "\t@COUNTRYCODE = ?,\n" +
                "\t@STATECODE = ?, \n" +
                "\t@CITYNAME = ?, \n" +
                "\t@AgeGroupId  = ?,\n" +
                "\t@TeamId  = ?,\n" +
                "\t@PositionIds = ?";

        ResultSet userRes = null;

        User fetchedUser = new User();
        GenericResponse parentRes = new GenericResponse();

        try{
            PreparedStatement RegisterUserStmt  = con.prepareStatement(query);

            RegisterUserStmt.setString(1, user.UserName);
            RegisterUserStmt.setString(2, user.FirstName);
            RegisterUserStmt.setString(3, user.LastName);
            RegisterUserStmt.setString(4, user.Email);
            RegisterUserStmt.setString(5, user.Phone);
            RegisterUserStmt.setString(6, user.DailCode);
            RegisterUserStmt.setString(7, user.PinCode);
            RegisterUserStmt.setString(8, user.Address);
            RegisterUserStmt.setString(9, user.CountryCode);
            RegisterUserStmt.setString(10, user.StateCode);
            RegisterUserStmt.setString(11, user.CityName);

            RegisterUserStmt.setInt(12, register.AgeGroupId);
            RegisterUserStmt.setInt(13, register.TeamId);
            RegisterUserStmt.setString(14, register.PositionId);

            userRes = RegisterUserStmt.executeQuery();

            String status = null;

            if(Extensions.hasColumn(userRes, "error")){
                while (userRes.next()) {
                    status = userRes.getString("error");
                }

                parentRes.status = false;
                parentRes.success= false;
                parentRes.message = ValidationMessages.CANNOT_CREATE_NEW_USER.toString();

                System.out.println(ValidationMessages.CANNOT_CREATE_NEW_USER.toString() + "error: " + status);
            }
            else{

                while (userRes.next()) {
                    status = userRes.getString("success");
                }

                if(status == "200"){
                    parentRes.status = true;
                    parentRes.success = true;
                    parentRes.message = "User registered successfully.";
                    System.out.println(parentRes.message);
                }
            }


        }
        catch (SQLException ex){
            parentRes.status = false;
            parentRes.success = false;
            parentRes.message = ValidationMessages.CANNOT_CREATE_NEW_USER.toString();

            System.out.println(ValidationMessages.CANNOT_CREATE_NEW_USER.toString() + "error: " + ex.getMessage());
        }
        finally {
            try { userRes.close(); } catch (Exception e) { /* Ignored */ }
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }

        return parentRes;
    }

    public GenericResponse UpdateExistingUser(FormRegisterRequestDTO reqData){

        boolean updateUserTable = false;

        StringBuilder parentQuery = new StringBuilder();

        StringBuilder userQuery = new StringBuilder();

        userQuery.append("UPDATE [USER] ");
        userQuery.append("SET ");

        StringJoiner st = new StringJoiner(",");

        if(reqData.firstname != null){
            updateUserTable = true;
            st.add("[FIRST_NAME] = '"+reqData.firstname+"'");
        }
        if(reqData.lastname != null){
            updateUserTable = true;
            st.add("[LAST_NAME] = '"+reqData.lastname+"'");
        }
        if(reqData.countrydailcodeselect != null){
            updateUserTable = true;
            st.add("[DAIL_CODE] = '"+reqData.countrydailcodeselect+"' ");
        }
        if(reqData.phonenumber != null){
            updateUserTable = true;
            st.add("[PHONE] = '"+reqData.phonenumber+"' ");
        }
        if(reqData.email != null){
            updateUserTable = true;
            st.add("[EMAIL] = '"+reqData.email+"' ");
        }
        if(reqData.address != null){
            updateUserTable = true;
            st.add("[ADDRESS] = '"+reqData.address+"' ");
        }
        if(reqData.pincode != null){
            updateUserTable = true;
            st.add("[PINCODE] = '"+reqData.pincode+"' ");
        }
        if(reqData.countryselect != null){
            updateUserTable = true;
            st.add("[COUNTRYID] = (SELECT TOP 1 c.ID FROM COUNTRY as c WHERE c.CODE = "+reqData.countryselect+")");
        }
        if(reqData.stateselect != null){
            updateUserTable = true;
            st.add("[STATEID] = (SELECT TOP 1 s.ID FROM [STATE] s WHERE s.CODE = "+reqData.stateselect+")");
        }
        if(reqData.stateselect != null){
            updateUserTable = true;
            st.add("[CITYID] = (SELECT TOP 1 ci.ID FROM [CITY] ci WHERE ci.NAME = "+reqData.cityselect+")");
        }

        userQuery.append(st);
        userQuery.append(" WHERE [USER].USERNAME = '" + reqData.username +"';");

        // there is no argument to be updated inside user table. empty the string here and start a new transaction
        if(updateUserTable){
            parentQuery.append(userQuery);
        }

        boolean updateUserRegisterTable = false;

        StringBuilder userRegQuery = new StringBuilder();

        userRegQuery.append(" UPDATE [USER_REGISTRATION] ");
        userRegQuery.append("SET ");

        StringJoiner userRegSJ = new StringJoiner(",");

        if(reqData.agegroup > 0){
            updateUserRegisterTable = true;
            userRegSJ.add("[AGEGROUPID] = " + reqData.agegroup);
        }

        if(reqData.desiredteamradios > 0){
            updateUserRegisterTable = true;
            userRegSJ.add("[TEAMID] = " + reqData.desiredteamradios);
        }

        if(reqData.desiredpositionchecks != null){
            updateUserRegisterTable = true;
            userRegSJ.add("[POSITIONID] = '"+reqData.desiredPositionChecksToString()+"' ");
        }

        userRegQuery.append(userRegSJ);
        userRegQuery.append(" WHERE [USER_REGISTRATION].[USERID] = (SELECT TOP 1 u.ID FROM [USER] as u WHERE u.USERNAME = '"+reqData.username+"'); ");

        if(updateUserRegisterTable){
            parentQuery.append(userRegQuery);
        }

        parentQuery.append(" SELECT 1 as [updated];");

        System.out.println("GENERATED UPDATE QUERY: " + parentQuery.toString());

        Database db = new Database();
        Connection con = null;

        try{
            con = db.connect();
        }
        catch (Exception ex){
            return new GenericResponse(false, false, ex.getMessage());
        }

        ResultSet userRes = null;

        GenericResponse parentRes = new GenericResponse();
        try{
            PreparedStatement RegisterUserStmt  = con.prepareStatement(parentQuery.toString());

            userRes = RegisterUserStmt.executeQuery();

            String status = null;

            if(Extensions.hasColumn(userRes, "updated")){
                while (userRes.next()) {
                    status = userRes.getString("updated");
                }

                if(status == "1"){
                    parentRes.status = true;
                    parentRes.success = true;
                    parentRes.message = "User updated successfully.";
                    System.out.println(parentRes.message);
                }
            }


        }
        catch (SQLException ex){
            parentRes.status = false;
            parentRes.success = false;
            parentRes.message = ValidationMessages.CANNOT_CREATE_NEW_USER.toString();

            System.out.println(ValidationMessages.CANNOT_CREATE_NEW_USER.toString() + "error: " + ex.getMessage());
        }
        finally {
            try { userRes.close(); } catch (Exception e) { /* Ignored */ }
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }

        return parentRes;

    }
}
