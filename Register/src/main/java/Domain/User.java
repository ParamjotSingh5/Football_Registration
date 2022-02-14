package Domain;

import DTO.ValidationReport;
import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import Utilities.Validations;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import launch.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User {

    public int ID;
    public String UserName;
    public String FirstName;
    public String LastName;
    public String Email;
    public String Phone;
    public String DailCode;
    public String PinCode;
    public String Address;
    public Date CreatedOn;
    public Date UpdatedOn;
    public int CountryId;
    public String CountryCode;
    public int StateId;
    public String StateCode;
    public int CityId;
    public String CityName;

    public GenericResponse getUserRegistrationDataByUsername(String username){
        if(username.trim().isEmpty()){
            return new GenericResponse(false, false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        Database db = new Database();
        Connection con = null;

        try{
            con = db.connect();
        }
        catch (Exception ex){
            System.out.println(ValidationMessages.DB_CONNECTION_ERROR.toString() + " error: " + ex.getMessage());
            return new GenericResponse(false, false, ValidationMessages.DB_CONNECTION_ERROR.toString());
        }

        String query = "SELECT u.ID, u.FIRST_NAME, u.LAST_NAME, u.[ADDRESS], u.DAIL_CODE, u.EMAIL, u.PHONE, u.PINCODE,\n" +
                "\t ur.AGEGROUPID, ur.TEAMID, ur.POSITIONID, cou.CODE as CountryCode , \n" +
                "\t sta.CODE as StateCode, ci.NAME as CityName FROM [USER] as u\n" +
                "INNER JOIN USER_REGISTRATION as ur on u.ID = ur.USERID\n" +
                "INNER JOIN COUNTRY as cou on u.COUNTRYID = cou.ID\n" +
                "INNER JOIN STATE as sta on u.STATEID = sta.ID\n" +
                "INNER JOIN CITY as ci on u.CITYID = ci.ID \n" +
                "WHERE u.USERNAME = ? \n";
        ResultSet userRes = null;

        GenericResponse parentRes = new GenericResponse();
        Registration registrationData = new Registration();

        try{
            PreparedStatement checkUserNameStmt  = con.prepareStatement(query);
            checkUserNameStmt.setString(1, username);
            userRes = checkUserNameStmt.executeQuery();

            while (userRes.next()) {
                registrationData.user.ID = userRes.getInt("ID");
                registrationData.user.FirstName = userRes.getString("FIRST_NAME");
                registrationData.user.LastName = userRes.getString("LAST_NAME");
                registrationData.user.Address = userRes.getString("ADDRESS");
                registrationData.user.DailCode = userRes.getString("DAIL_CODE");
                registrationData.user.Email = userRes.getString("EMAIL");
                registrationData.user.Phone = userRes.getString("PHONE");
                registrationData.user.PinCode = userRes.getString("PINCODE");
                registrationData.user.CountryCode = userRes.getString("CountryCode");
                registrationData.user.StateCode = userRes.getString("StateCode");
                registrationData.user.CityName = userRes.getString("CityName");

                registrationData.userRegister.AgeGroupId = userRes.getInt("AGEGROUPID");
                registrationData.userRegister.TeamId = userRes.getInt("TEAMID");
                registrationData.userRegister.PositionId = userRes.getString("POSITIONID");
            }

            parentRes.status =true;
            //User exists
            if(registrationData.user.ID > 0){
                parentRes.success = true;
                parentRes.message = ValidationMessages.USERNAME_EXISTS.toString();
                parentRes.data = registrationData.getRegistrationData();
            }
            else {
                parentRes.success = false;
            }
        }
        catch (SQLException ex){

            System.out.println(ValidationMessages.INTERNAL_SERVER_ERROR.toString() + " error: " + ex.getMessage());

            parentRes.status = false;
            parentRes.success = false;
            parentRes.message = ValidationMessages.INTERNAL_SERVER_ERROR.toString();
        }
        catch (Exception ex){
            System.out.println(ValidationMessages.INTERNAL_SERVER_ERROR.toString() + " error: " + ex.getMessage());

            parentRes.status = false;
            parentRes.success = false;
            parentRes.message = ValidationMessages.INTERNAL_SERVER_ERROR.toString();
        }
        finally {
            try { userRes.close(); } catch (Exception e) { /* Ignored */ }
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }

        return parentRes;
    }

    public GenericResponse isUserNameAlreadyExists(String username)
    {
        if(username.trim().isEmpty()){
            return new GenericResponse(false, false, ValidationMessages.VALUE_REQUIRED.toString());
        }

        Database db = new Database();
        Connection con = null;

        try{
            con = db.connect();
        }
        catch (Exception ex){
            System.out.println(ValidationMessages.DB_CONNECTION_ERROR.toString() + " error: " + ex.getMessage());
            return new GenericResponse(false, false, ValidationMessages.DB_CONNECTION_ERROR.toString());
        }

        String query = "SELECT U.ID FROM [USER] as U where U.USERNAME= ?";
        ResultSet userRes = null;

        User fetchedUser = new User();
        GenericResponse parentRes = new GenericResponse();

        try{
            PreparedStatement checkUserNameStmt  = con.prepareStatement(query);
            checkUserNameStmt.setString(1, username);
            userRes = checkUserNameStmt.executeQuery();

            while (userRes.next()) {
                fetchedUser.ID = userRes.getInt("ID");
            }

            parentRes.status =true;
            //User exists
            if(fetchedUser.ID > 0){
                parentRes.success = true;
                parentRes.message = ValidationMessages.USERNAME_EXISTS.toString();
            }
            else {
                parentRes.success = false;
            }
        }
        catch (SQLException ex){

            System.out.println(ValidationMessages.INTERNAL_SERVER_ERROR.toString() + " error: " + ex.getMessage());

            parentRes.status = false;
            parentRes.success = false;
            parentRes.message = ValidationMessages.INTERNAL_SERVER_ERROR.toString();
        }
        finally {
            try { userRes.close(); } catch (Exception e) { /* Ignored */ }
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }

        return parentRes;
    }

    public int getID() {
        return ID;
    }

    public User setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getUserName() {
        return UserName;
    }

    public User setUserName(String userName) {
        UserName = userName;
        return this;
    }

    public String getFirstName() {
        return FirstName;
    }

    public User setFirstName(String firstName) {
        FirstName = firstName;
        return this;
    }

    public String getLastName() {
        return LastName;
    }

    public User setLastName(String lastName) {
        LastName = lastName;
        return this;
    }

    public String getEmail() {
        return Email;
    }

    public User setEmail(String email) {
        Email = email;
        return this;
    }

    public String getPhone() {
        return Phone;
    }

    public User setPhone(String phone) {
        Phone = phone;
        return this;
    }

    public String getDailCode() {
        return DailCode;
    }

    public User setDailCode(String dailCode) {
        DailCode = dailCode;
        return this;
    }

    public String getPinCode() {
        return PinCode;
    }

    public User setPinCode(String pinCode) {
        PinCode = pinCode;
        return this;
    }

    public String getAddress() {
        return Address;
    }

    public User setAddress(String address) {
        Address = address;
        return this;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public User setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return UpdatedOn;
    }

    public User setUpdatedOn(Date updatedOn) {
        UpdatedOn = updatedOn;
        return this;
    }

    public int getCountryId() {
        return CountryId;
    }

    public User setCountryId(int countryId) {
        CountryId = countryId;
        return this;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public User setCountryCode(String countryCode) {
        CountryCode = countryCode;
        return this;
    }

    public int getStateId() {
        return StateId;
    }

    public User setStateId(int stateId) {
        StateId = stateId;
        return this;
    }

    public String getStateCode() {
        return StateCode;
    }

    public User setStateCode(String stateCode) {
        StateCode = stateCode;
        return this;
    }

    public int getCityId() {
        return CityId;
    }

    public User setCityId(int cityId) {
        CityId = cityId;
        return this;
    }

    public String getCityName() {
        return CityName;
    }

    public User setCityName(String cityName) {
        CityName = cityName;
        return this;
    }
}

















