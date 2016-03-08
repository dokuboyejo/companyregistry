package companyregistry.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fault {

    private String errorCode = null;
    private String errorMessage = null;

    /**
     * @return the errorCode
     */
    @JsonProperty("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Fault {\n");

        sb.append("  errorCode: ").append(errorCode).append("\n");
        sb.append("  errorMessage: ").append(errorMessage).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

}