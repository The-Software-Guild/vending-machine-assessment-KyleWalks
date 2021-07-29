package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DvdLibraryDao;
import com.sg.dvdlibrary.dao.DvdLibraryDaoException;
import com.sg.dvdlibrary.dto.Dvd;
import com.sg.dvdlibrary.ui.DvdLibraryView;

import java.util.List;

public class DvdLibraryController {

    private DvdLibraryView view;
    private DvdLibraryDao dao;

    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listStudents();
                        break;
                    case 2:
                        createStudent();
                        break;
                    case 3:
                        viewStudent();
                        break;
                    case 4:
                        removeStudent();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (DvdLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void createStudent() throws DvdLibraryDaoException {
        view.displayCreateStudentBanner();
        Dvd newDvd = view.getNewStudentInfo();
        dao.addStudent(newDvd.getStudentId(), newDvd);
        view.displayCreateSuccessBanner();
    }

    private void removeStudent() throws DvdLibraryDaoException {
        view.displayRemoveStudentBanner();
        String studentId = view.getStudentIdChoice();
        Dvd removedDvd = dao.removeStudent(studentId);
        view.displayRemoveResult(removedDvd);
    }

    private void listStudents() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllStudents();
        view.displayStudentList(dvdList);
    }

    private void viewStudent() throws DvdLibraryDaoException {
        view.displayDisplayStudentBanner();
        String studentId = view.getStudentIdChoice();
        Dvd dvd = dao.getStudent(studentId);
        view.displayStudent(dvd);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

}