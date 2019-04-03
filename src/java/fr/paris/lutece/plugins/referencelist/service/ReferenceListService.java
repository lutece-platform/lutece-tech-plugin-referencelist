package fr.paris.lutece.plugins.referencelist.service;

import java.util.List;

import fr.paris.lutece.plugins.referencelist.business.Reference;
import fr.paris.lutece.plugins.referencelist.business.ReferenceHome;

/**
 *
 * ReferenceListService
 *
 */
/**
 * This class provides instances management methods for ReferenceList
 */
public class ReferenceListService {

    /* This class implements the Singleton design pattern. */
    private static ReferenceListService _singleton;

    /**
     * Returns the instance of ReferenceListService
     * 
     * @return the ReferenceListService instance
     */
    public static ReferenceListService getInstance() {
        if (_singleton == null) {
            _singleton = new ReferenceListService();
        }

        return _singleton;
    }

    /**
     * Returns the list of all References
     * 
     * @return the list of all References
     */
    public List<Reference> getReferenceList() {
        List<Reference> listReference = ReferenceHome.getReferencesList();
        return listReference;
    }

}