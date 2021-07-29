package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.Dvd;

import java.util.List;

public class DvdLibraryView {

    private UserIO io;

    public DvdLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List Students");
        io.print("2. Create New Student");
        io.print("3. View a Student");
        io.print("4. Remove a Student");
        io.print("5. Exit");

        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public Dvd getNewStudentInfo() {
        String studentId = io.readString("Please enter Student ID");
        String firstName = io.readString("Please enter First Name");
        String lastName = io.readString("Please enter Last Name");
        String cohort = io.readString("Please enter Cohort");
        Dvd currentDvd = new Dvd(studentId);
        currentDvd.setFirstName(firstName);
        currentDvd.setLastName(lastName);
        currentDvd.setCohort(cohort);
        return currentDvd;
    }

    public void displayCreateStudentBanner() {
        io.print("=== Create Student ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Student successfully created.  Please hit enter to continue");
    }

    public void displayStudentList(List<Dvd> dvdList) {
        for (Dvd currentDvd : dvdList) {
            String studentInfo = String.format("#%s : %s %s",
                    currentDvd.getStudentId(),
                    currentDvd.getFirstName(),
                    currentDvd.getLastName());
            io.print(studentInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveStudentBanner () {
        io.print("=== Remove Student ===");
    }

    public void displayRemoveResult(Dvd dvdRecord) {
        if(dvdRecord != null){
            io.print("Student successfully removed.");
        }else{
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayStudentBanner () {
        io.print("=== Display Student ===");
    }

    public String getStudentIdChoice() {
        return io.readString("Please enter the Student ID.");
    }

    public void displayStudent(Dvd dvd) {
        if (dvd != null) {
            io.print(dvd.getStudentId());
            io.print(dvd.getFirstName() + " " + dvd.getLastName());
            io.print(dvd.getCohort());
            io.print("");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All Students ===");
    }
}
