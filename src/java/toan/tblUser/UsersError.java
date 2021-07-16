/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblUser;

/**
 *
 * @author toann
 */
public class UsersError {

    private String nameError, phoneError, passwordError, addressError, emailError, emailErrorFormat, phoneErrorFormat, emailDuplicate;

    public UsersError() {
    }

    @Override
    public String toString() {
        return "UsersError{" + "nameError=" + nameError + ", phoneError=" + phoneError + ", passwordError=" + passwordError + ", addressError=" + addressError + ", emailError=" + emailError + ", emailErrorFormat=" + emailErrorFormat + ", phoneErrorFormat=" + phoneErrorFormat + ", emailDuplicate=" + emailDuplicate + '}';
    }

    public String getEmailDuplicate() {
        return emailDuplicate;
    }

    public void setEmailDuplicate(String emailDuplicate) {
        this.emailDuplicate = emailDuplicate;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getEmailErrorFormat() {
        return emailErrorFormat;
    }

    public void setEmailErrorFormat(String emailErrorFormat) {
        this.emailErrorFormat = emailErrorFormat;
    }

    public String getPhoneErrorFormat() {
        return phoneErrorFormat;
    }

    public void setPhoneErrorFormat(String phoneErrorFormat) {
        this.phoneErrorFormat = phoneErrorFormat;
    }

    public UsersError(String nameError, String phoneError, String passwordError, String addressError, String emailError, String emailErrorFormat, String phoneErrorFormat, String emailDuplicate) {
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.passwordError = passwordError;
        this.addressError = addressError;
        this.emailError = emailError;
        this.emailErrorFormat = emailErrorFormat;
        this.phoneErrorFormat = phoneErrorFormat;
        this.emailDuplicate = emailDuplicate;
    }

}
