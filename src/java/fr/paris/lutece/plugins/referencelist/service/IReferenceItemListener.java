package fr.paris.lutece.plugins.referencelist.service;

import fr.paris.lutece.plugins.referencelist.business.ReferenceItem;

/**
 * Listener for ReferenceItem events 
 */
public interface IReferenceItemListener
{
    /**
     * Method called where a {@link ReferenceItem} is created
     * @param item
     */
    void addReferenceItem( ReferenceItem item );
    
    /**
     * Method called where a {@link ReferenceItem} is deleted
     * @param item
     */
    void removeReferenceItem( ReferenceItem item );
    
    /**
     * Method called where a {@link ReferenceItem} is removed
     * @param item
     */
    void updateReferenceItem( ReferenceItem item );

}
