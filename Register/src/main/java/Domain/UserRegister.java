package Domain;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserRegister {

    public int ID;
    public int UserId;
    public int TeamId;
    public String PositionId;
    public int AgeGroupId;
    public Date CreatedOn;
    public Date UpdatedOn;

    public int getID() {
        return ID;
    }

    public UserRegister setID(int ID) {
        this.ID = ID;
        return this;
    }

    public int getUserId() {
        return UserId;
    }

    public UserRegister setUserId(int userId) {
        UserId = userId;
        return this;
    }

    public int getTeamId() {
        return TeamId;
    }

    public UserRegister setTeamId(int teamId) {
        TeamId = teamId;
        return this;
    }

    public String getPositionId() {
        return PositionId;
    }

    public int[] getFormattedPositionId() {

        String[] items = this.PositionId.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            };
        }

        return results;
    }

    public UserRegister setPositionId(String positionId) {
        PositionId = positionId;
        return this;
    }

    public UserRegister setPositionId(int[] positionIds) {

        PositionId = IntStream.of(positionIds)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        return this;
    }

    public int getAgeGroupId() {
        return AgeGroupId;
    }

    public UserRegister setAgeGroupId(int ageGroupId) {
        AgeGroupId = ageGroupId;
        return this;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public UserRegister setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return UpdatedOn;
    }

    public UserRegister setUpdatedOn(Date updatedOn) {
        UpdatedOn = updatedOn;
        return this;
    }
}
