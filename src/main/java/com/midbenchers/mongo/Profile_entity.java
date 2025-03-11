package com.midbenchers.mongo;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profile")
public class Profile_entity {


    @Id
    private String id;

    private  Long userId;
    private String bio;
    private String profilepicture;
    private String address;
    private Long phonenumber;

    public Profile_entity() {
    }

    public Profile_entity(Long userId, String bio, String address, String profilepicture, Long phonenumber) {
        this.userId = userId;
        this.bio = bio;
        this.address = address;
        this.profilepicture = profilepicture;
        this.phonenumber = phonenumber;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserid() {
        return userId;
    }

    public void setUserid(Long userid) {
        this.userId = userid;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }
}
