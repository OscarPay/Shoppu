
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Patient {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("allergies")
    @Expose
    private String allergies;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_attributes")
    @Expose
    private UserAttributes userAttributes;

    public Patient(String name, String lastname, String email, String password, String passwordConfirm, String number){
        this.userAttributes = new UserAttributes(name,lastname,email,password,passwordConfirm,number);
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The bloodType
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * 
     * @param bloodType
     *     The blood_type
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * 
     * @return
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 
     * @return
     *     The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     *     The allergies
     */
    public String getAllergies() {
        return allergies;
    }

    /**
     * 
     * @param allergies
     *     The allergies
     */
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The user
     */
    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    /**
     * 
     * @param userAttributes
     *     The user
     */
    public void setUserAttributes(UserAttributes userAttributes) {
        this.userAttributes = userAttributes;
    }

}
