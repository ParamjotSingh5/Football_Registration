package Domain;

import DTO.FormRegisterRequestDTO;
import Utilities.Extensions;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import launch.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration {

    public User user = new User();
    public UserRegister userRegister = new UserRegister();

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
        }   }

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
}
