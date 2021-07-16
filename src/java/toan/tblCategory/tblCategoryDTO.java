/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblCategory;

import java.io.Serializable;

/**
 *
 * @author toann
 */
public class tblCategoryDTO implements Serializable{
    private String categoryID, categoryName;

    public tblCategoryDTO(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public tblCategoryDTO() {
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "tblCategoryDTO{" + "categoryID=" + categoryID + ", categoryName=" + categoryName + '}';
    }
    
}
