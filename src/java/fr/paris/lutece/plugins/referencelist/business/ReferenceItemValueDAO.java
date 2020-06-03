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
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for ReferenceItemValue objects
 */
public final class ReferenceItemValueDAO implements IReferenceItemValueDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_itemvalue, item_name, lang, value FROM referencelist_itemvalue v, referencelist_item i" 
    		 										+ " where v.id_reference_item = i.id_reference_item ";
    
    private static final String SQL_QUERY_INSERT = "INSERT INTO referencelist_itemvalue ( id_reference_item, lang, value ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM referencelist_itemvalue WHERE id_itemvalue = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE referencelist_itemvalue SET id_reference_item = ?, lang = ?, value = ? WHERE id_itemvalue = ?";
    
    private static final String SQL_QUERY_SELECTALL = SQL_QUERY_SELECT + " and i.idreference = ?";  
    private static final String SQL_QUERY_SELECTONE = SQL_QUERY_SELECT + " and v.id_itemvalue = ?"; 
    
    private String _logMessage = "";
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( ReferenceItemValue itemValue, Plugin plugin )
    {	
    	AppLogService.info("ReferenceItemValueDAO -> insert");
    	
    	AppLogService.info("itemValue created : idItem = " + itemValue.getIdItem( ) );
    	AppLogService.info("itemValue created : lang = " + itemValue.getLang( ) );
    	AppLogService.info("itemValue created : value = " + itemValue.getValue( ) );
    	
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        
    	try
        {
            int nIndex = 1;
            
            daoUtil.setInt( nIndex++, itemValue.getIdItem( ) );
            daoUtil.setString( nIndex++, itemValue.getLang( ) );
            daoUtil.setString( nIndex++, itemValue.getValue( ) );

            daoUtil.executeUpdate( );
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceItemValue load( int nKey, Plugin plugin )
    {
    	ReferenceItemValue value = new ReferenceItemValue( );
    	
   		AppLogService.info("ReferenceItemValueDAO -> load");
       
   		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTONE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            value.setId( daoUtil.getInt( nIndex++ ) );
            value.setName( daoUtil.getString( nIndex++ ) );
            value.setLang( daoUtil.getString( nIndex++ ) );
            value.setValue( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
       
        return value;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
		AppLogService.info("ReferenceItemValueDAO -> delete id = " + nKey);
    	
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
	    daoUtil.setInt( 1, nKey );
	    daoUtil.executeUpdate( );
	    daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( ReferenceItemValue itemValue, Plugin plugin )
    {    	
    	AppLogService.info("ReferenceItemValueDAO -> store");
    	
    	AppLogService.info("itemValue created : id = " + itemValue.getId( ) );
    	AppLogService.info("itemValue created : idItem = " + itemValue.getIdItem( ) );
    	AppLogService.info("itemValue modified : lang = " + itemValue.getLang() );
    	AppLogService.info("itemValue modified : value = " + itemValue.getValue() );
    	
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

    	try
        {
            int nIndex = 1;
            
            daoUtil.setInt( nIndex++, itemValue.getIdItem( ) );
            daoUtil.setString( nIndex++, itemValue.getLang( ) );
            daoUtil.setString( nIndex++, itemValue.getValue( ) );
            daoUtil.setInt( nIndex++, itemValue.getId( ) );
            
            daoUtil.executeUpdate( );
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ReferenceItemValue> selectReferenceItemValues( int idReference, Plugin plugin )
    {
    	AppLogService.info("ReferenceItemValueDAO -> selectReferenceItemValues : idReference = " + idReference);
    	
    	List<ReferenceItemValue> listReferenceItemValues = new ArrayList<ReferenceItemValue>( );
        
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, idReference );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
        	ReferenceItemValue value = new ReferenceItemValue( );
            int nIndex = 1;

            value.setId( daoUtil.getInt( nIndex++ ) );
            value.setName( daoUtil.getString( nIndex++ ) );
            value.setLang( daoUtil.getString( nIndex++ ) );
            value.setValue( daoUtil.getString( nIndex++ ) );

            listReferenceItemValues.add( value );
        }

        daoUtil.free( );
        
        return listReferenceItemValues;
    }

    
    
}
