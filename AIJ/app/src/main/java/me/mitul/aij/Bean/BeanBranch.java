package me.mitul.aij.Bean;

public class BeanBranch {
    private int BranchId;
    private int NumCollege;
    private String BranchName;

    public BeanBranch() {
    }

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int p) {
        BranchId = p;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String p) {
        BranchName = p;
    }

    public int getNumCollege() {
        return NumCollege;
    }

    public void setNumCollege(int p) {
        NumCollege = p;
    }
}
