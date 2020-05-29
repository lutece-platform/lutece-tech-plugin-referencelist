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
    private static final String SQL_QUERY_SELECT = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE id_reference_item = ?";
    private static final String SQL_QUERY_SELECT_ID = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE idreference = ?";
    private static final String SQL_QUERY_SELECT_NAME = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item WHERE idreference = ? AND item_name = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO referencelist_item ( item_name, item_value, idreference ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM referencelist_item WHERE id_reference_item = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE referencelist_item SET item_name = ?, item_value = ? WHERE id_reference_item = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_reference_item, item_name, item_value, idreference FROM referencelist_item";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_reference_item FROM referencelist_item";
    
    
    private String _logMessage = "";
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( ReferenceItemValue referenceItem, Plugin plugin )
    {
    	_logMessage = "ReferenceItemValueDAO -> insert";
    	
    	AppLogService.info(_logMessage);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceItemValue load( int nKey, Plugin plugin )
    {
    	_logMessage = "ReferenceItemValueDAO -> load";
       
   		AppLogService.info(_logMessage);
       
       return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
    	_logMessage = "ReferenceItemValueDAO -> delete";
    	
    	AppLogService.info(_logMessage);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( ReferenceItemValue referenceItem, Plugin plugin )
    {
    	_logMessage = "ReferenceItemValueDAO -> store";
    	
    	AppLogService.info(_logMessage);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ReferenceItemValue> selectReferenceItemValues( int idReference, Plugin plugin )
    {
    	_logMessage = "ReferenceItemValueDAO -> selectReferenceItemValues : idReference = " + idReference;
    	
    	AppLogService.info(_logMessage);
    	
    	List<ReferenceItemValue> listReferenceItemValues = new ArrayList<ReferenceItemValue>( );
        
        ReferenceItemValue value = new ReferenceItemValue( );
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
        
        return listReferenceItemValues;
    }

    
    
}
