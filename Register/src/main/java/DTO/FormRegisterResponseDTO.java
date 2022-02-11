package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic container for form submitting report
 */
public class FormRegisterResponseDTO {
    /**
     * <code>false</code>, iff an error occurred while processing form submitting request, otherwise <code>true</code>.
     */
    public boolean status;
    /**
     * <code>true</code>, iff all the protocols are successfully validated against request data, otherwise <code>false</code>, if any of data is fails to follow the protocols.
     */
    public boolean success;
    public String message;
    public List<ValidationReport> data = new ArrayList<ValidationReport>();
}
