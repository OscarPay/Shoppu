package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Doctor implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("rfc")
    @Expose
    private String rfc;
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName("hospital")
    @Expose
    private String hospital;
    @SerializedName("office")
    @Expose
    private String office;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("office_phone_number")
    @Expose
    private Object officePhoneNumber;
    @SerializedName("user_attributes")
    @Expose
    private UserAttributes userAttributes;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     * The rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     * The specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     *
     * @param specialty
     * The specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     *
     * @return
     * The hospital
     */
    public String getHospital() {
        return hospital;
    }

    /**
     *
     * @param hospital
     * The hospital
     */
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    /**
     *
     * @return
     * The office
     */
    public String getOffice() {
        return office;
    }

    /**
     *
     * @param office
     * The office
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The officePhoneNumber
     */
    public Object getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    /**
     *
     * @param officePhoneNumber
     * The office_phone_number
     */
    public void setOfficePhoneNumber(Object officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    /**
     *
     * @return
     * The user
     */
    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    /**
     *
     * @param userAttributes
     * The user
     */
    public void setUserAttributes(UserAttributes userAttributes) {
        this.userAttributes = userAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (rfc != null ? !rfc.equals(doctor.rfc) : doctor.rfc != null) return false;
        if (specialty != null ? !specialty.equals(doctor.specialty) : doctor.specialty != null)
            return false;
        if (hospital != null ? !hospital.equals(doctor.hospital) : doctor.hospital != null)
            return false;
        if (office != null ? !office.equals(doctor.office) : doctor.office != null) return false;
        if (address != null ? !address.equals(doctor.address) : doctor.address != null)
            return false;
        if (officePhoneNumber != null ? !officePhoneNumber.equals(doctor.officePhoneNumber) : doctor.officePhoneNumber != null)
            return false;
        if (userAttributes != null ? !userAttributes.equals(doctor.userAttributes) : doctor.userAttributes != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rfc != null ? rfc.hashCode() : 0;
        result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
        result = 31 * result + (hospital != null ? hospital.hashCode() : 0);
        result = 31 * result + (office != null ? office.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (officePhoneNumber != null ? officePhoneNumber.hashCode() : 0);
        result = 31 * result + (userAttributes != null ? userAttributes.hashCode() : 0);
        return result;
    }
}
