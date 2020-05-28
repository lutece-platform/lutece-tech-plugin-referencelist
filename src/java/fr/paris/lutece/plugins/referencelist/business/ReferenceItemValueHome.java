/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for ReferenceItemValueValue objects
 */
public final class ReferenceItemValueHome
{
    // Static variable pointed at the DAO instance
    //private static IReferenceItemValueDAO _dao = SpringContextService.getBean( "referencelist.referenceItemValueValueDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "referencelist" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ReferenceItemValueHome( )
    {
    }

    /**
     * Create an instance of the referenceItemValue class
     * 
     * @param referenceItemValue
     *            The instance of the ReferenceItemValue which contains the informations to store
     * @return The instance of referenceItemValue which has been created with its primary key.
     */
    public static ReferenceItemValue create( ReferenceItemValue referenceItemValue )
    {
        //_dao.insert( referenceItemValue, _plugin );

        return referenceItemValue;
    }

    /**
     * Update of the referenceItemValue which is specified in parameter
     * 
     * @param referenceItemValue
     *            The instance of the ReferenceItemValue which contains the data to store
     * @return The instance of the referenceItemValue which has been updated
     */
    public static ReferenceItemValue update( ReferenceItemValue referenceItemValue )
    {
       // _dao.store( referenceItemValue, _plugin );

        return referenceItemValue;
    }

    /**
     * Remove the referenceItemValue whose identifier is specified in parameter
     * 
     * @param nKey
     *            The referenceItemValue Id
     */
    public static void remove( int nKey )
    {
        //_dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a referenceItemValue whose identifier is specified in parameter
     * 
     * @param nKey
     *            The referenceItemValue primary key
     * @return an instance of ReferenceItemValue
     */
    public static ReferenceItemValue findByPrimaryKey( int nKey )
    {
        return null; //_dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the referenceItemValue objects and returns them as a list
     * 
     * @return the list which contains the data of all the referenceItemValue objects
     */
    public static List<ReferenceItemValue> getReferenceItemValueList( int nIdReference )
    {
    	List<ReferenceItemValue> listReferenceItemValues = new ArrayList<ReferenceItemValue>( );
        
        ReferenceItemValue value = new ReferenceItemValue();
        value.setName("civilite.monsieur");
        value.setLang("fr");
        value.setValue("M.");
        listReferenceItemValues.add( value );
        
        value = new ReferenceItemValue();
        value.setName("civilite.monsieur");
        value.setLang("es");
        value.setValue("Sr");
        listReferenceItemValues.add( value );
        
        value = new ReferenceItemValue();
        value.setName("civilite.madame");
        value.setLang("fr");
        value.setValue("Mme");
        listReferenceItemValues.add( value );
        
        value = new ReferenceItemValue();
        value.setName("civilite.madame");
        value.setLang("es");
        value.setValue("Sra");
        listReferenceItemValues.add( value );
        
        return listReferenceItemValues ;//_dao.selectReferenceItemValuesList( nIdReference, _plugin );
    }

    
}
