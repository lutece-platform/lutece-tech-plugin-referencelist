/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.referencelist.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for ReferenceItem objects
 */
public final class ReferenceItemHome
{
    // Static variable pointed at the DAO instance
    private static IReferenceItemDAO _dao = SpringContextService.getBean( "referencelist.referenceItemDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "referencelist" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ReferenceItemHome( )
    {
    }

    /**
     * Create an instance of the referenceItem class
     * 
     * @param referenceItem
     *            The instance of the ReferenceItem which contains the informations to store
     * @return The instance of referenceItem which has been created with its primary key.
     */
    public static ReferenceItem create( ReferenceItem referenceItem )
    {
        _dao.insert( referenceItem, _plugin );

        return referenceItem;
    }

    /**
     * Update of the referenceItem which is specified in parameter
     * 
     * @param referenceItem
     *            The instance of the ReferenceItem which contains the data to store
     * @return The instance of the referenceItem which has been updated
     */
    public static ReferenceItem update( ReferenceItem referenceItem )
    {
        _dao.store( referenceItem, _plugin );

        return referenceItem;
    }

    /**
     * Remove the referenceItem whose identifier is specified in parameter
     * 
     * @param nKey
     *            The referenceItem Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a referenceItem whose identifier is specified in parameter
     * 
     * @param nKey
     *            The referenceItem primary key
     * @return an instance of ReferenceItem
     */
    public static ReferenceItem findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the referenceItem objects and returns them as a list
     * 
     * @return the list which contains the data of all the referenceItem objects
     */
    public static List<ReferenceItem> getReferenceItemsList( int nIdReference )
    {
        return _dao.selectReferenceItemsList( nIdReference, _plugin );
    }

    /**
     * Load the id of all the referenceItem objects and returns them as a list
     * 
     * @return the list which contains the id of all the referenceItem objects
     */
    public static List<Integer> getIdReferenceItemsList( )
    {
        return _dao.selectIdReferenceItemsList( _plugin );
    }

    /**
     * Load the data of all the referenceItem objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the referenceItem objects
     */
    public static ReferenceList getReferenceItemsReferenceList( )
    {
        return _dao.selectReferenceItemsReferenceList( _plugin );
    }

    /**
     * Check if ReferenceItemName exist in Reference.
     * 
     * @param nIdReference
     *            The reference id
     * @param Itemname
     *            The Itemname of candidateItem
     * @return an instance of ReferenceItem
     */
    public static ReferenceItem findByReferenceName( int nIdReference, String Itemname )
    {
        return _dao.loadReferenceItemByName( nIdReference, Itemname, _plugin );

    }

    public static CompareResult compareReferenceItems( List<ReferenceItem> candidateItems, int refId )
    {

        // lists to compare
        List<ReferenceItem> currentListCandidateItems = candidateItems;
        List<ReferenceItem> currentListReferenceItems = ReferenceItemHome.getReferenceItemsList( refId );

        // lists to return;
        List<ReferenceItem> updateListCandidateReferenceItems = new ArrayList<>( );
        List<ReferenceItem> duplicateListCandidateReferenceItems = new ArrayList<>( );
        List<ReferenceItem> insertListCandidateReferenceItems = new ArrayList<>( );

        // Compare
        for ( ReferenceItem candidateItem : currentListCandidateItems )
        {
            boolean founded = false;
            if ( !currentListReferenceItems.isEmpty( ) )
            {
                for ( ReferenceItem referenceItem : currentListReferenceItems )
                {
                    // compare names;
                    if ( candidateItem.getItemName( ).equals( referenceItem.getItemName( ) ) )
                    {
                        founded = true;
                        // compare values;
                        if ( candidateItem.getItemValue( ).equals( referenceItem.getItemValue( ) ) )
                        {
                            // duplicate candidateItem.
                            duplicateListCandidateReferenceItems.add( candidateItem );
                        }
                        else
                        {
                            // candidateItem to update.
                            candidateItem.setId( referenceItem.getId( ) );
                            updateListCandidateReferenceItems.add( candidateItem );
                        }
                    }
                }
            }
            // candidateItem to insert.
            if ( !founded )
                insertListCandidateReferenceItems.add( candidateItem );
        }
        return new CompareResult( insertListCandidateReferenceItems, updateListCandidateReferenceItems, duplicateListCandidateReferenceItems );
    }

}
