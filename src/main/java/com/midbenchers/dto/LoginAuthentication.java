package com.midbenchers.dto;




public class LoginAuthentication {


    private String email;
    private String password;

    public LoginAuthentication(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginAuthentication() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
