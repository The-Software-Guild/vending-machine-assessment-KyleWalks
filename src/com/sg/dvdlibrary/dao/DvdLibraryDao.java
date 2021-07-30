package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Dvd;

import java.util.List;

public interface DvdLibraryDao {
    /**
     * Adds the given DVD to the library and associates it with the given
     * DVD title. If there is already a DVD associated with the given
     * DVD title it will return that DVD object, otherwise it will
     * return null.
     *
     * @param dvdTitle title with which DVD is to be associated
     * @param dvd DVD to be added to the library
     * @return the DVD object previously associated with the given
     * DVD title if it exists, null otherwise
     * @throws DvdLibraryDaoException
     */
    Dvd addDvd(String dvdTitle, Dvd dvd)
            throws DvdLibraryDaoException;

    /**
     * Returns a List of all DVDs on the library.
     *
     * @return DVD List containing all DVDs on the library.
     * @throws DvdLibraryDaoException
     */
    List<Dvd> getAllDvds()
            throws DvdLibraryDaoException;

    /**
     * Returns the DVD object associated with the given DVD title.
     * Returns null if no such DVD exists
     *
     * @param dvdTitle Title of the DVD to retrieve
     * @return the DVD object associated with the given DVD title,
     * null if no such DVD exists
     * @throws DvdLibraryDaoException
     */
    Dvd getDvd(String dvdTitle)
            throws DvdLibraryDaoException;

    /**
     * Removes from the library the DVD associated with the given title.
     * Returns the DVD object that is being removed or null if
     * there is no DVD associated with the given title
     *
     * @param dvdTitle title of DVD to be removed
     * @return DVD object that was removed or null if no DVD
     * was associated with the given DVD title
     * @throws DvdLibraryDaoException
     */
    Dvd removeDvd(String dvdTitle)
            throws DvdLibraryDaoException;
}